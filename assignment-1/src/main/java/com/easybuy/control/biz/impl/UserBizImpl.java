package com.easybuy.control.biz.impl;

import javax.servlet.http.HttpServletRequest;

import com.easybuy.control.Constants;
import com.easybuy.control.biz.UserBiz;
import com.easybuy.control.dao.UserDao;
import com.easybuy.control.dao.impl.UserDaoImplMySql;
import com.easybuy.model.User;
import com.easybuy.util.CrudResult;

public class UserBizImpl implements UserBiz {

    private UserDao dao = new UserDaoImplMySql();


    @Override
    public CrudResult addUser(User user) {
        if (null == user) {
            return new CrudResult(false, "invalid user null!");
        }
        if (null == user.getEu_user_name() || user.getEu_user_name().trim().length() == 0) {
            return new CrudResult(false, "user name shoudl not be empty!");
        }
        try {
            if (null != dao.getUserByUserName(user.getEu_user_name())) {
                return new CrudResult(false, "user name '" + user.getEu_user_name() + "' is already taken!");
            }

            User newUser = dao.addUser(user);
            return new CrudResult(true, newUser);

        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while accessing database. Error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult removeUser(long userId) {
        // TODO: some extra checks before removing the user (e.g, user's related orders, comments, etc)
        try {
            boolean ret = dao.removeUser(userId);
            if (ret) {
                return new CrudResult(true);
            } else {
                return new CrudResult(false, "no user with id=" + userId + " were removed from database");
            }
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while deleting user from database. user_id= " + userId
                    + ", error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult updateUser(long userId, User user) {
        if (null == user) {
            return new CrudResult(false, "invalid user null!");
        }
        // TODO: some extra checks before update the user
        try {
            boolean ret = dao.updateUser(userId, user);
            if (ret) {
                return new CrudResult(true);
            } else {
                return new CrudResult(false, "no user with id=" + userId + " were removed from database");
            }
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while updating user from database. user_id= " + userId
                    + ", error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult getUserById(long userId) {
        try {
            User user = dao.getUserById(userId);
            if (null == user) {
                return new CrudResult(false, "No user found with user_id=" + userId + "!");
            }
            return new CrudResult(true, user);
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while reading user from database. user_id= " + userId
                    + ", error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult getUserByUserName(String userName) {
        try {
            User user = dao.getUserByUserName(userName);
            if (null == user) {
                return new CrudResult(false, "No user found with user_name=" + userName + "!");
            }
            return new CrudResult(true, user);
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while reading user from database. userName= " + userName
                    + ", error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult getAllUsers() {
        try {
            return new CrudResult(true, dao.getAllUsers());
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while reading users from database. Error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult getCurrentUserName(HttpServletRequest req) {
        String curUserName = null;
        if (null != req
                && null != (curUserName = (String) req.getSession().getAttribute(Constants.SESS_ATTR_NAME_USERNAME))) {
            return new CrudResult(true, (Object) curUserName);
        } else {
            return new CrudResult(false);
        }
    }

}
