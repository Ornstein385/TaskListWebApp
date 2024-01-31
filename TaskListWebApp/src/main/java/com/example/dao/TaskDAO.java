package com.example.dao;

import com.example.models.Task;
import com.example.models.TaskItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class TaskDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Task> index() {
        return entityManager.createQuery("FROM Task", Task.class).getResultList();
    }

    public Task show(long id) {
        return entityManager.find(Task.class, id);
    }

    public void save(Task task) {
        entityManager.persist(task);
    }

    public void updateTask(long id, Task updatedTask) {
        Task task = entityManager.find(Task.class, id);
        task.setName(updatedTask.getName());
        task.setDescription(updatedTask.getDescription());
        if (updatedTask.getDeadline() != null) {
            task.setDeadline(updatedTask.getDeadline());
        }
        task.getTaskItems().clear();
        task.getTaskItems().addAll(updatedTask.getTaskItems() == null ? Collections.emptyList() : updatedTask.getTaskItems());
        task.getTaskItems().forEach(taskItem -> taskItem.setTask(task));
        entityManager.merge(task);
    }

    public void deleteTask(long id) {
        Task task = entityManager.find(Task.class, id);
        entityManager.remove(task);
    }

    public void updateTaskItem(long id, TaskItem updatedTaskItem) {
        TaskItem taskItem = entityManager.find(TaskItem.class, id);
        taskItem.setName(updatedTaskItem.getName());
        taskItem.setDescription(updatedTaskItem.getDescription());
        taskItem.setReady(updatedTaskItem.getReady());
        entityManager.merge(taskItem);
    }

    public List<Task> findByUserId(long userId) {
        return entityManager.createQuery("FROM Task WHERE user.id = :userId", Task.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
