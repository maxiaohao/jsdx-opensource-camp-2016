package com.easybuy.control.dao;

import java.util.List;

import com.easybuy.model.User;

public interface UserDao {

    User addUser(User user);


    boolean removeUser(long id);


    boolean updateUser(long id, User user);


    User getUserById(long userId);


    User getUserByUserName(String userName);


    List<User> getAllUsers();

}
