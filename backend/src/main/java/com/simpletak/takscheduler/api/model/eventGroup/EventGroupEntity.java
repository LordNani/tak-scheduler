package com.simpletak.takscheduler.api.model.eventGroup;


import com.simpletak.takscheduler.api.model.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "event_group")
public class EventGroupEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "eventGroupName", length = 256, nullable = false)
    private String eventName;

    @Column(name = "eventGroupDescription", length = 512)
    private String eventGroupDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id", nullable = false)
    private UserEntity owner;
}
