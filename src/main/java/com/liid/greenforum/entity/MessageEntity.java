package com.liid.greenforum.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_creator_id")
    @NotNull
    private UUID userCreatorId;

    @Column(name = "user_creator_nickname")
    @NotBlank
    private String userCreatorNickname;

    @Column(name = "creation_date")
    @NotNull
    private LocalDateTime creationDate;

    @Column(name = "text")
    @NotBlank
    private String text;

    @ManyToOne
    @JoinColumn(name="topic_id")
    private TopicEntity parentTopic;
}
