package com.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "can't be empty")
    @Size(min = 2, max = 25)
    private String name;

    @NotEmpty(message = "can't be empty")
    @Email(message = "invalid email")
    @Size(max = 50, message = "too long email")
    private String email;

    @NotEmpty(message = "can't be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_]{5,32}$", message = "invalid telegram name")
    @Column(name = "telegram", nullable = false)
    private String tgName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;
}

/*
CREATE TABLE IF NOT EXISTS users (
id SERIAL PRIMARY KEY,
name VARCHAR(25) NOT NULL CHECK (length(name) >= 2),
email VARCHAR(50) NOT NULL CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
telegram VARCHAR(32) NOT NULL CHECK (telegram ~ '^[a-zA-Z0-9_]{5,32}$')
);
 */