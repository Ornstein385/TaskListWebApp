package com.example.controllers;

import com.example.dao.TaskDAO;
import com.example.dao.UserDAO;
import com.example.models.Task;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TasksController {
    private final TaskDAO taskDAO;
    private final UserDAO userDAO;

    public TasksController(TaskDAO taskDAO, UserDAO userDAO) {
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
    }

    @GetMapping
    public String listAllTasks(Model model) {
        List<Task> tasks = taskDAO.index();
        model.addAttribute("tasks", tasks);
        return "tasksList"; // имя представления, в котором будут отображаться задачи
    }

    @GetMapping("/user/{userId}")
    public String listTasksByUser(@PathVariable("userId") long userId, Model model) {
        // Получаем список задач для пользователя. Вам нужно добавить соответствующий метод в TaskDAO.
        List<Task> tasks = taskDAO.findByUserId(userId);
        model.addAttribute("tasks", tasks);
        return "tasksList"; // имя представления, в котором будут отображаться задачи пользователя
    }

    @GetMapping("/add")
    public String newTask(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("users", userDAO.index());
        return "addTask"; // Представление для добавления новой задачи
    }

    @PostMapping
    public String createTask(@ModelAttribute("task") @Valid Task task, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addTask";
        }
        taskDAO.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String editTask(@PathVariable("id") long id, Model model) {
        Task task = taskDAO.show(id);
        model.addAttribute("task", task);
        model.addAttribute("users", userDAO.index());
        return "editTask"; // Представление для редактирования задачи
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("task", taskDAO.show(id));
        return "taskDetails";
    }

    @PutMapping("/{id}")
    public String updateTask(@PathVariable("id") long id, @ModelAttribute("task") @Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editTask";
        }
        taskDAO.updateTask(id, task);
        return "redirect:/tasks/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable("id") long id) {
        taskDAO.deleteTask(id);
        return "redirect:/tasks";
    }
}
