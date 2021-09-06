package com.codewalla.books.dao;

import com.codewalla.books.entity.User;

import java.util.List;

public interface UserRepository {
    Boolean saveUser( User user);
    Integer updateUser(User user);
    User getById(int id);
    User getByEmail(String email);
    Integer deleteById(int id);
    List<User> getAllUsers();
}
