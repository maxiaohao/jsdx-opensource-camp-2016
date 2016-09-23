package com.easybuy.control.dao.impl;

import java.util.List;

import com.easybuy.control.dao.UserDao;
import com.easybuy.model.User;
import com.easybuy.util.db.GenericDao;

public class UserDaoImplMySql extends GenericDao implements UserDao {

    @Override
    public boolean addUser(User user) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean removeUser(long id) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public boolean updateUser(long id, User user) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public User getUserById(long userId) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public User getUserByUserName(String userNames) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<User> getAllUsers() {
        // TODO Auto-generated method stub
        return null;
    }

}
