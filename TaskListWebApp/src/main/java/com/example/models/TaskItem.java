package com.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "task_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id") // Имя колонки внешнего ключа, связывающее с Task
    private Task task;

    @NotEmpty(message = "can't be empty")
    @Size(max = 30)
    private String name;

    @NotEmpty(message = "can't be empty")
    @Size(max = 200, message = "too long description")
    private String description;

    private Boolean ready = false;
}

/*
CREATE TABLE IF NOT EXISTS task_items (
    id SERIAL PRIMARY KEY,
    task_id BIGINT NOT NULL,
    name VARCHAR(30) NOT NULL CHECK (length(name) > 0),
    description VARCHAR(200) NOT NULL CHECK (length(description) > 0),
    ready BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES tasks(id)
);
 */