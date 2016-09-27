package com.easybuy.control.dao;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easybuy.control.dao.impl.UserDaoImplMySql;
import com.easybuy.model.User;

public class UserDaoTest {

    protected final Logger log = LoggerFactory.getLogger(UserDaoTest.class);


    @Test
    public void testAddUser() {
        User user = new User();
        user.setEu_user_name("张三");
        user.setEu_sex("男");
        user.setEu_birthday(new Date());
        user.setEu_status(User.STATUS_NORMAL);
        User newUser = new UserDaoImplMySql().addUser(user);
        log.info("newUser-> ", newUser);
    }

    //
    // boolean removeUser(long id);
    //
    //
    // boolean updateUser(long id, User user);
    //
    //
    // User getUserById(long userId);
    //
    //
    // User getUserByUserName(String userName);
    //
    //
    // List<User> getAllUsers();

}
