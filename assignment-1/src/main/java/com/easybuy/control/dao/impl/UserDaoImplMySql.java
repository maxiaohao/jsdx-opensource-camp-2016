package com.easybuy.control.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.easybuy.control.dao.UserDao;
import com.easybuy.model.User;
import com.easybuy.util.db.GenericDao;

public class UserDaoImplMySql extends GenericDao implements UserDao {

    @Override
    public User addUser(User user) {
        if (null == user) {
            return null;
        }
        try {
            int newUserId = insert(
                    User.class,
                    "insert into easybuy_user (eu_user_name, eu_password, eu_sex, eu_birthday, "
                            + "eu_identity_code, eu_email, eu_mobile, eu_address, eu_status) values (?,?,?,?,?,?,?,?,?)",
                    user.getEu_user_name(), user.getEu_password(), user.getEu_sex(), user.getEu_birthday(),
                    user.getEu_identity_code(), user.getEu_email(), user.getEu_mobile(), user.getEu_address(),
                    user.getEu_status());
            user.setEu_user_id(newUserId);
            return user;
        } catch (SQLException e) {
            log.error("failed to create new user {}", user);
        }
        return null;
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
