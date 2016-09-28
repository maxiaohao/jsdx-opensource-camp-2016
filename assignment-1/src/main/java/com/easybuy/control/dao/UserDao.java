package com.easybuy.control.dao;

import java.util.List;

import com.easybuy.model.User;

/**
 * DB CRUD interface for {@link User}.
 *
 * @author maxiaohao <maxiaohao@gmail.com>
 * @date Sep 28, 2016
 *
 */
public interface UserDao {

    User addUser(User user) throws Throwable;


    boolean removeUser(long userId) throws Throwable;


    boolean updateUser(long userId, User user) throws Throwable;


    User getUserById(long userId) throws Throwable;


    User getUserByUserName(String userName) throws Throwable;


    List<User> getAllUsers() throws Throwable;

}
