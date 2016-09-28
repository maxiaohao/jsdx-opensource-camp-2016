package com.easybuy.control.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.easybuy.control.dao.UserDao;
import com.easybuy.model.User;
import com.easybuy.util.db.GenericDao;

/**
 * MySQL CRUD implementation for {@link User}.
 *
 * @author maxiaohao <maxiaohao@gmail.com>
 * @date Sep 28, 2016
 *
 */
public class UserDaoImplMySql extends GenericDao implements UserDao {

    @Override
    public User addUser(User user) throws SQLException {
        if (null == user) {
            return null;
        }
        int newUserId = insert(
                User.class,
                "insert into easybuy_user (eu_user_name, eu_password, eu_sex, eu_birthday, "
                        + "eu_identity_code, eu_email, eu_mobile, eu_address, eu_status) values (?,?,?,?,?,?,?,?,?)",
                user.getEu_user_name(), user.getEu_password(), user.getEu_sex(), user.getEu_birthday(),
                user.getEu_identity_code(), user.getEu_email(), user.getEu_mobile(), user.getEu_address(),
                user.getEu_status());
        user.setEu_user_id(newUserId);
        return user;
    }


    @Override
    public boolean removeUser(long userId) throws SQLException {
        return exec("delete from easybuy_user where eu_user_id=?", userId) > 0;
    }


    @Override
    public boolean updateUser(long userId, User user) throws SQLException {
        return exec(
                "update easybuy_user set eu_user_name=?, eu_password=?, eu_sex=?, eu_birthday=?, "
                        + "eu_identity_code=?, eu_email=?, eu_mobile=?, eu_address=?, eu_status=? where eu_user_id=?",
                user.getEu_user_name(), user.getEu_password(), user.getEu_sex(), user.getEu_birthday(),
                user.getEu_identity_code(), user.getEu_email(), user.getEu_mobile(), user.getEu_address(),
                user.getEu_status(), userId) > 0;
    }


    @Override
    public User getUserById(long userId) throws Exception {
        return queryObj(User.class, "select * from easybuy_user where eu_user_id=?", userId);
    }


    @Override
    public User getUserByUserName(String userName) throws Exception {
        return queryObj(User.class, "select * from easybuy_user where eu_user_name=?", userName);
    }


    @Override
    public List<User> getAllUsers() throws Exception {
        return queryObjs(User.class, "select * from easybuy_user");
    }

}
