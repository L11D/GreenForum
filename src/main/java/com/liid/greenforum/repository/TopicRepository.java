package com.liid.greenforum.repository;

import com.liid.greenforum.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TopicRepository extends JpaRepository<TopicEntity, UUID> {

    @Query(value =
            "SELECT EXISTS " +
                    "(SELECT 1 FROM MessageEntity m " +
                    "JOIN TopicEntity t ON m.parentTopic.id = t.id AND (m.id = :messageId AND t.id = :topicId) " +
                    ")"
    )
    boolean containMessage(@Param("topicId") UUID topicId,
                           @Param("messageId") UUID messageId);
}
