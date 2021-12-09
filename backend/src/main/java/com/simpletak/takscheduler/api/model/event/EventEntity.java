package com.simpletak.takscheduler.api.model.event;

import com.simpletak.takscheduler.api.model.eventGroup.EventGroupEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "event", indexes = @Index(name = "dateIndex", columnList = "nextEventDate ASC, startEventDate ASC, endEventDate ASC"))
public class EventEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "eventName", length = 128, nullable = false)
    private String eventName;

    @Column(name = "eventDescription", length = 512)
    private String eventDescription;

    @Column(name = "eventPriority")
    private EventPriority eventPriority;

    @Column(name = "isReoccurring")
    private boolean isReoccurring;

    @Column(name = "eventFreq")
    private EventFreq eventFreq;

    @Column(name = "execution_cron_date")
    private String eventCron;

    @Column(name = "nextEventDate")
    private Date nextEventDate;

    @Column(name = "eventTime")
    private Date eventTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="eventGroup_id", nullable = false)
    private EventGroupEntity eventGroup;

    @Column(name = "startEventDate")
    private Date startEventDate;

    @Column(name = "endEventDate")
    private Date endEventDate;

//    private EventDateTime eventDateTime;

//    @Data
//    public class EventDateTime{
//        public EventDateTime(Date eventDate, Date eventTime) {
//            this.eventDate = eventDate;
//            this.eventTime = eventTime;
//        }
//
//        @Temporal(TemporalType.DATE)
//        private Date eventDate;
//
//        @Temporal(TemporalType.TIME)
//        private Date eventTime;
//    }
}
