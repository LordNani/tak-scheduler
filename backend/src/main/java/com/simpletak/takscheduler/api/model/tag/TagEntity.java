package com.simpletak.takscheduler.api.model.tag;

import com.simpletak.takscheduler.api.model.tagEventGroup.TagEventGroupEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "tag")
public class TagEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "tagName", length = 64)
    private String tagName;

    @Column(name = "tagColor", length = 7)
    private String tagColor;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "tag")
    private List<TagEventGroupEntity> tagEventGroupEntities;
}
