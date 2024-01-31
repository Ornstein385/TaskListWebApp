package com.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotEmpty(message = "can't be empty")
    @Size(max = 30)
    private String name;

    @NotEmpty(message = "can't be empty")
    @Size(max = 500, message = "too long description")
    private String description;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskItem> taskItems;

    @Column(name = "creation_time", nullable = false, updatable = false)
    private LocalDateTime creationTime = LocalDateTime.now();

    private LocalDateTime deadline;
}

/*
CREATE TABLE IF NOT EXISTS tasks (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(30) NOT NULL CHECK (length(name) > 0),
    description VARCHAR(500) NOT NULL CHECK (length(description) > 0),
    creation_time TIMESTAMP NOT NULL DEFAULT NOW(),
    deadline TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);
 */