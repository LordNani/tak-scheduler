package com.simpletak.takscheduler.model.event;

import lombok.*;
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
@Table(name = "event", indexes = @Index(name = "dateIndex", columnList = "eventDate ASC, eventTime ASC"))
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

    @Column(name = "eventDate")
    @Temporal(TemporalType.DATE)
    private Date eventDate;

    @Column(name = "eventTime")
    @Temporal(TemporalType.TIME)
    private Date eventTime;

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
