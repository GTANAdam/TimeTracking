package com.timetracking.user.dao;

import com.timetracking.user.model.User;

import java.util.List;

public interface IUserDAO 
{
    List<User> getUsers();
    List<User> getUsers(int start, int total);
    int add(User user);
    boolean delete(int id);
    User getById(int id);
    boolean update(User user);
    int getNumberOfRows();
}
