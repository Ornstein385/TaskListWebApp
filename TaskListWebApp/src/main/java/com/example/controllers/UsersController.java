package com.example.controllers;

import com.example.dao.UserDAO;
import com.example.models.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserDAO userDAO;


    public UsersController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userDAO.index());
        return "usersList";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userDAO.show(id));
        return "user";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("user") User user) {
        return "add";
    }

    @PostMapping
    public String save(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add";
        }
        userDAO.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userDAO.show(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userDAO.update(id, user);
        return "redirect:/users/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userDAO.delete(id);
        return "redirect:/users";
    }
}