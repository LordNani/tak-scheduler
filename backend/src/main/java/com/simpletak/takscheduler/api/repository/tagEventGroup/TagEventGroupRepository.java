package com.simpletak.takscheduler.api.repository.tagEventGroup;

import com.simpletak.takscheduler.api.model.tagEventGroup.TagEventGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagEventGroupRepository extends JpaRepository<TagEventGroupEntity, UUID> {
    @Query(value = "SELECT teg1.eventgroup_id\n" +
            "  FROM tag_event_group AS teg1, \n" +
            "  (SELECT * FROM tag WHERE id IN :tags) AS t1\n" +
            "   WHERE teg1.tag_id = t1.id\n" +
            "   GROUP BY teg1.eventgroup_id\n" +
            "   HAVING COUNT(teg1.tag_id) = " +
            "(SELECT COUNT(id) FROM (SELECT * FROM tag WHERE id IN :tags) AS t2);", nativeQuery = true)
    public List<UUID> getEventGroupIdsByAllTagIds(List<String> tags);
}
