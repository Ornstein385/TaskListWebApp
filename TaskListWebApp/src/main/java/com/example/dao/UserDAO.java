package com.example.dao;

import com.example.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<User> index() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    public User show(long id) {
        return entityManager.find(User.class, id);
    }

    public void save(User user) {
        entityManager.persist(user);
    }

    public void update(long id, User updatedUser) {
        User user = entityManager.find(User.class, id);
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setTgName(updatedUser.getTgName());
        entityManager.merge(user);
    }

    public void delete(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
}