package com.liid.greenforum.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "topic")
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class TopicEntity {

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

    @Column(name = "name")
    @NotBlank
    private String name;
}
