package com.easybuy.control.dao;

import java.sql.SQLException;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easybuy.control.dao.impl.UserDaoImplMySql;
import com.easybuy.model.User;

/**
 *
 * @author maxiaohao <maxiaohao@gmail.com>
 * @date Sep 28, 2016
 *
 */
public class UserDaoTest {

    protected final Logger log = LoggerFactory.getLogger(UserDaoTest.class);


    @Test
    public void testAddUser() throws SQLException {
        User user = new User();
        user.setEu_user_name("张三");
        user.setEu_sex("男");
        user.setEu_birthday(new Date());
        user.setEu_status(User.STATUS_NORMAL);
        User newUser = new UserDaoImplMySql().addUser(user);
        log.info("newUser Id -> {}", newUser.getEu_user_id());
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
