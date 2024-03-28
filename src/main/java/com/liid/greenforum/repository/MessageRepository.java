package com.liid.greenforum.repository;

import com.liid.greenforum.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    @Query(value =
            "SELECT m FROM MessageEntity m WHERE m.parentTopic.id = :topicId")
    Page<MessageEntity> findByTopicId(@Param("topicId") UUID topicId, Pageable pageable);

    @Query(value =
            "SELECT m FROM MessageEntity m WHERE m.parentTopic.id = :topicId")
    List<MessageEntity> findByTopicId(@Param("topicId") UUID topicId);
}
