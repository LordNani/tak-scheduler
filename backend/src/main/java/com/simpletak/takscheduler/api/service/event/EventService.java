package com.simpletak.takscheduler.api.service.event;

import com.simpletak.takscheduler.api.dto.event.EventDTO;
import com.simpletak.takscheduler.api.dto.event.EventMapper;
import com.simpletak.takscheduler.api.dto.event.NewEventDTO;
import com.simpletak.takscheduler.api.exception.InvalidCronExpressionException;
import com.simpletak.takscheduler.api.exception.event.EventNotFoundException;
import com.simpletak.takscheduler.api.exception.eventgroup.EventGroupNotFoundException;
import com.simpletak.takscheduler.api.exception.user.UserNotFoundException;
import com.simpletak.takscheduler.api.model.event.EventEntity;
import com.simpletak.takscheduler.api.model.event.EventFreq;
import com.simpletak.takscheduler.api.model.event.scheduling.EventSchedulingByCronDTO;
import com.simpletak.takscheduler.api.model.event.scheduling.EventSchedulingByDateDTO;
import com.simpletak.takscheduler.api.repository.event.EventRepository;
import com.simpletak.takscheduler.api.service.event.scheduling.EventRunnableTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.quartz.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    public EventDTO findEventById(UUID id) {
        return mapper.fromEntity(eventRepository.findById(id).orElseThrow(EventNotFoundException::new));
    }

    public EventDTO createEvent(NewEventDTO eventDTO) {
        EventEntity eventEntity = mapper.toEntity(new EventDTO(eventDTO, null));
        return mapper.fromEntity(eventRepository.saveAndFlush(eventEntity));
    }

    public EventDTO updateEvent(EventDTO eventDTO) {
        EventEntity eventEntity = mapper.toEntity(eventDTO);
        if (!eventRepository.existsById(eventDTO.getId())) throw new EventNotFoundException();
        return mapper.fromEntity(eventRepository.saveAndFlush(eventEntity));
    }

    public void deleteEvent(UUID id) {
        try {
            eventRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EventGroupNotFoundException();
        }
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
                    eventEntity.getEventDate(),
                    period
            );
        } else if (eventEntity.getEventFreq().equals(EventFreq.DAILY)) {
            taskScheduler.scheduleWithFixedDelay(
                    task,
                    eventEntity.getEventDate(),
                    period
            );
        } else {
            taskScheduler.schedule(
                    task,
                    eventEntity.getEventDate()
            );
        }

        log.info("The event '" + eventEntity.getEventName() + "' has been scheduled by date.");

    }

    public void scheduleEventByDate(EventSchedulingByDateDTO eventSchedulingByDateDTO) {
        EventEntity eventEntity = eventRepository
                .findById(eventSchedulingByDateDTO.getEventID())
                .orElseThrow(UserNotFoundException::new);

        eventEntity.setEventDate(eventSchedulingByDateDTO.getExecutionDate());
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
}
