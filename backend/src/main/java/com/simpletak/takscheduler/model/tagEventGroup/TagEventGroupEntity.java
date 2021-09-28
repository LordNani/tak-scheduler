package com.simpletak.takscheduler.model.tagEventGroup;

import com.simpletak.takscheduler.model.eventGroup.EventGroupEntity;
import com.simpletak.takscheduler.model.tag.TagEntity;
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


@Entity
@Table(name = "tag_event_group")
public class TagEventGroupEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "eventgroup_id", nullable = false)
    private EventGroupEntity eventGroupEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tag_id", nullable = false)
    private TagEntity chat;
}
