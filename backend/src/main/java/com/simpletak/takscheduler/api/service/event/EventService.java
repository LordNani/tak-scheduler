package com.simpletak.takscheduler.api.service.event;

import com.simpletak.takscheduler.api.dto.event.EventDTO;
import com.simpletak.takscheduler.api.dto.event.EventMapper;
import com.simpletak.takscheduler.api.dto.event.NewEventDTO;
import com.simpletak.takscheduler.api.exception.InvalidCronExpressionException;
import com.simpletak.takscheduler.api.exception.event.EventNotFoundException;
import com.simpletak.takscheduler.api.exception.event.IncorrectYearOrTimeRangeException;
import com.simpletak.takscheduler.api.exception.user.UserIsNotAuthorizedException;
import com.simpletak.takscheduler.api.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.api.model.event.EventEntity;
import com.simpletak.takscheduler.api.model.event.EventFreq;
import com.simpletak.takscheduler.api.model.event.scheduling.EventSchedulingByCronDTO;
import com.simpletak.takscheduler.api.model.event.scheduling.EventSchedulingByDateDTO;
import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.api.model.subscription.SubscriptionEntity;
import com.simpletak.takscheduler.api.repository.event.EventRepository;
import com.simpletak.takscheduler.api.repository.subscription.SubscriptionRepository;
import com.simpletak.takscheduler.api.service.event.scheduling.EventRunnableTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.quartz.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log
public class EventService {
    public static final long DAY_IN_MILISECONDS = 86400000;
    public static final long DAY_IN_WEEK = 7;
    private final ThreadPoolTaskScheduler taskScheduler;
    private final EventRepository eventRepository;
    private final EventMapper mapper;
    private final ApplicationContext applicationContext;
    private final SubscriptionRepository subscriptionRepository;

    public EventDTO findEventById(UUID id) {
        return mapper.fromEntity(eventRepository.findById(id).orElseThrow(EventNotFoundException::new));
    }

    public EventDTO createEvent(NewEventDTO eventDTO) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();

        EventEntity eventEntity = mapper.toEntity(new EventDTO(eventDTO, null));

        EventGroupEntity eventGroupEntity = eventEntity.getEventGroup();
        boolean isOwned = eventGroupEntity.getOwner().getId().equals(userId);

        if (!isOwned) {
            throw new UserIsNotAuthorizedException("You are not authorized to edit this event group.");
        }

        return mapper.fromEntity(eventRepository.saveAndFlush(eventEntity));
    }

    public EventDTO updateEvent(EventDTO eventDTO) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();

        EventEntity eventEntity = mapper.toEntity(eventDTO);

        EventGroupEntity eventGroupEntity = eventEntity.getEventGroup();
        if (!userId.equals(eventGroupEntity.getOwner().getId())) {
            throw new UserIsNotAuthorizedException("You are not authorized to edit this event.");
        }

        if (!eventRepository.existsById(eventDTO.getId())) throw new EventNotFoundException();
        return mapper.fromEntity(eventRepository.saveAndFlush(eventEntity));
    }

    public void deleteEvent(UUID id) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();

        EventEntity eventEntity = eventRepository
                .findById(id)
                .orElseThrow(EventNotFoundException::new);

        EventGroupEntity eventGroupEntity = eventEntity.getEventGroup();
        if (!userId.equals(eventGroupEntity.getOwner().getId())) {
            throw new UserIsNotAuthorizedException("You are not authorized to delete this event.");
        }

        eventRepository.deleteById(id);
    }

    public void scheduleEvents() {
        List<EventEntity> eventsWithCron = eventRepository.findAllByEventCronIsNotNull();
        for (EventEntity eventEntity : eventsWithCron) {
            scheduleEventByCron(eventEntity);
        }
    }

    private void scheduleEventByCron(EventEntity eventEntity) {
        EventRunnableTask task = applicationContext.getBean(EventRunnableTask.class);
        task.setEventEntity(eventEntity);
        taskScheduler.schedule(
                task,
                new CronTrigger(eventEntity.getEventCron())
        );
        log.info("The event '" + eventEntity.getEventName() + "' has been scheduled by CRON.");

    }

    private void scheduleEventByDate(EventEntity eventEntity) {
        EventRunnableTask task = applicationContext.getBean(EventRunnableTask.class);
        long period = DAY_IN_MILISECONDS;
        task.setEventEntity(eventEntity);

        if (eventEntity.getEventFreq().equals(EventFreq.WEEKLY)) {
            period = DAY_IN_MILISECONDS * DAY_IN_WEEK;
            taskScheduler.scheduleWithFixedDelay(
                    task,
                    eventEntity.getNextEventDate(),
                    period
            );
        } else if (eventEntity.getEventFreq().equals(EventFreq.DAILY)) {
            taskScheduler.scheduleWithFixedDelay(
                    task,
                    eventEntity.getNextEventDate(),
                    period
            );
        } else {
            taskScheduler.schedule(
                    task,
                    eventEntity.getNextEventDate()
            );
        }

        log.info("The event '" + eventEntity.getEventName() + "' has been scheduled by date.");

    }

    public void scheduleEventByDate(EventSchedulingByDateDTO eventSchedulingByDateDTO) {
        EventEntity eventEntity = eventRepository
                .findById(eventSchedulingByDateDTO.getEventID())
                .orElseThrow(UserNotFoundException::new);

        eventEntity.setNextEventDate(eventSchedulingByDateDTO.getExecutionDate());
        eventRepository.save(eventEntity);
        scheduleEventByDate(eventEntity);
    }

    public void notifyUser(EventEntity eventEntity) {
        System.out.println("NOTIFY : " + eventEntity.getEventName());
    }

    public void scheduleEventByCron(EventSchedulingByCronDTO eventDTO) {
        boolean isValidCron = CronExpression.isValidExpression(eventDTO.getExecutionCronDate());
        if (!isValidCron) {
            throw new InvalidCronExpressionException();
        }
        EventEntity eventEntity = eventRepository
                .findById(eventDTO.getEventID())
                .orElseThrow(UserNotFoundException::new);

        eventEntity.setEventCron(eventDTO.getExecutionCronDate());
        eventRepository.save(eventEntity);
        scheduleEventByCron(eventEntity);
    }

    private static String parseToCron(String date) {
        String month = date.substring(date.indexOf("-") + 1, date.indexOf("-") + 3);
        String day = date.substring(date.indexOf("-") + 4, date.indexOf("-") + 6);
        String hour = date.substring(date.indexOf("T") + 1, date.indexOf(":"));
        String minutes = date.substring(date.indexOf(":") + 1, date.indexOf(":") + 3);
        return String.format("00 %s %s %s %s ?", minutes, hour, day, month);
    }

    private static String parseToDate(String cron) {
        String minutes = cron.substring(3, 5);
        String hour = cron.substring(6, 8);
        String day = cron.substring(9, 11);
        String month = cron.substring(12, 14);
        return String.format("%s-%s-%sT%s:%s:00", LocalDate.now().getYear(), month, day, hour, minutes);
    }

    public List<EventDTO> eventsInMonth(Integer year, Integer month) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
        validateYearAndMonth(year, month);
        Calendar startRangeC = new GregorianCalendar(year, month-1, 1, 0, 0);
        Date startRange = startRangeC.getTime();
        startRangeC.add(Calendar.MONTH, 1);
        Date endRange = startRangeC.getTime();
        return getSubscribedEventsInTimeRange(startRange, endRange, userId);
    }

    public List<EventDTO> eventsWithinDay(Integer year, Integer month, Integer day) {
        UUID userId = (UUID) SecurityContextHolder.getContext().getAuthentication().getDetails();
        validateYearAndMonthAndDay(year, month, day);
        Calendar startRangeC = new GregorianCalendar(year, month-1, day, 0, 0);
        Date startRange = startRangeC.getTime();
        startRangeC.add(Calendar.DAY_OF_MONTH, 1);
        Date endRange = startRangeC.getTime();
        return getSubscribedEventsInTimeRange(startRange, endRange, userId);
    }

    private List<EventDTO> getSubscribedEventsInTimeRange(Date startRange, Date endRange, UUID userId) {
        List<SubscriptionEntity> subscriptionEntities = subscriptionRepository.findAllByUserEntity_Id(userId);
        List<EventGroupEntity> eventGroups = subscriptionEntities
                .stream()
                .map(SubscriptionEntity::getEventGroupEntity)
                .collect(Collectors.toList());
        List<EventEntity> events = eventRepository.findAllByStartEventDateLessThanEqualAndEndEventDateGreaterThanAndEventGroupIn(endRange, startRange, eventGroups);
        List<EventEntity> reoccurringEvents = events
                .stream()
                .filter(EventEntity::isReoccurring)
                .collect(Collectors.toList());
        events.addAll(getRepeatedEventsInTimeRange(startRange, endRange, reoccurringEvents));
        return events
                .stream()
                .map(mapper::fromEntity)
                .collect(Collectors.toList());
    }

    private List<EventEntity> getRepeatedEventsInTimeRange(Date startRange, Date endRange, List<EventEntity> reoccurringEvents) {
        LinkedList<EventEntity> events = new LinkedList<>();
        while(!events.isEmpty()){
            EventEntity reoccurringEvent = events.pop();
            Calendar actualStartDateInInterval = new GregorianCalendar();
            actualStartDateInInterval.setTime(reoccurringEvent.getStartEventDate());
            Calendar actualEndDateInInterval = new GregorianCalendar();
            actualEndDateInInterval.setTime(reoccurringEvent.getEndEventDate().compareTo(endRange) > 0 ?
                    endRange : reoccurringEvent.getEndEventDate());
            if(actualStartDateInInterval.compareTo(actualEndDateInInterval) >= 0) continue;
            if(reoccurringEvent.getStartEventDate().compareTo(startRange) < 0){
                actualStartDateInInterval = getActualStartDateInRange(reoccurringEvent.getStartEventDate(), reoccurringEvent.getEventFreq(), startRange);
            }
            int incrementField = reoccurringEvent.getEventFreq().getType().equals(EventFreq.DAILY) ?
                    Calendar.DAY_OF_MONTH : Calendar.WEEK_OF_MONTH;
            for(Calendar currentDate = (Calendar) actualEndDateInInterval.clone();
                currentDate.compareTo(actualEndDateInInterval) < 0;
                currentDate.add(incrementField, 1)){
                EventEntity clone = reoccurringEvent.clone();
                clone.setNextEventDate(currentDate.getTime());
                events.add(clone);
            }
        }
        return events;
    }

    private Calendar getActualStartDateInRange(Date earlierDate, EventFreq eventFreq, Date date) {
        Calendar earlierCalendar = new GregorianCalendar();
        earlierCalendar.setTime(earlierDate);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        if(eventFreq.equals(EventFreq.DAILY)){
            if(timeOfDayCompare(earlierCalendar, calendar) < 0){
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            earlierCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
            earlierCalendar.set(Calendar.DAY_OF_MONTH, 1); //Precaution
            earlierCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            earlierCalendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
            return earlierCalendar;
        }
        else{
            long daysBetween = ChronoUnit.DAYS.between(
                    LocalDateTime.ofInstant(earlierCalendar.toInstant(), earlierCalendar.getTimeZone().toZoneId()),
                    LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()));
            int amountOfWeeks = (int) (daysBetween/7);
            earlierCalendar.add(Calendar.WEEK_OF_YEAR, amountOfWeeks);
            if(earlierCalendar.compareTo(calendar) < 0) earlierCalendar.add(Calendar.WEEK_OF_YEAR, 1);
            return earlierCalendar;
        }
    }

    private int timeOfDayCompare(Calendar earlierCalendar, Calendar calendar) {
        Calendar clone = (Calendar) calendar.clone();
        clone.set(Calendar.YEAR, earlierCalendar.get(Calendar.YEAR));
        clone.set(Calendar.DAY_OF_MONTH, 1); //Precaution
        clone.set(Calendar.MONTH, earlierCalendar.get(Calendar.MONTH));
        clone.set(Calendar.DAY_OF_MONTH, earlierCalendar.get(Calendar.DAY_OF_MONTH));
        return earlierCalendar.compareTo(clone);
    }

    private void validateYearAndMonthAndDay(Integer year, Integer month, Integer day) {
        validateYearAndMonth(year, month);
        Calendar calendar = new GregorianCalendar(year, month, 1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if(day < 1 || day > maxDay) throw new IncorrectYearOrTimeRangeException();
    }

    private void validateYearAndMonth(Integer year, Integer month) {
        if(!(year != null && year >= 2021 && year < 2121 && month != null && month >= 1 && month <= 12)){
            throw new IncorrectYearOrTimeRangeException();
        }
    }
}
