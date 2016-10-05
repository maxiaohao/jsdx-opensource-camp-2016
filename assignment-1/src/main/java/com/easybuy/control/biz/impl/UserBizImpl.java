package com.easybuy.control.biz.impl;

import javax.servlet.http.HttpServletRequest;

import com.easybuy.control.Constants;
import com.easybuy.control.biz.UserBiz;
import com.easybuy.control.dao.UserDao;
import com.easybuy.control.dao.impl.UserDaoImplMySql;
import com.easybuy.model.User;
import com.easybuy.util.CrudResult;

/**
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Sep 28, 2016
 *
 */
public class UserBizImpl implements UserBiz {

    private UserDao dao = new UserDaoImplMySql();


    @Override
    public CrudResult addUser(User user) {
        if (null == user) {
            return new CrudResult(false, "invalid user null!");
        }
        if (null == user.getEu_user_name() || user.getEu_user_name().trim().length() == 0) {
            return new CrudResult(false, "用户名不能为空");
        }
        try {
            if (null != dao.getUserByUserName(user.getEu_user_name())) {
                return new CrudResult(false, "用户名'" + user.getEu_user_name() + "'已经被他人使用， 请尝试其他用户名!");
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
        String newUserName = user.getEu_user_name();
        if (null == newUserName) {
            return new CrudResult(false, "用户名不能为空");
        } else {
            User otherUser = null;
            try {
                otherUser = dao.getUserByUserName(newUserName);
            } catch (Throwable e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (null != otherUser && userId != otherUser.getEu_user_id()) {
                return new CrudResult(false, "修改后的用户名'" + newUserName + "'已经被其他用户所用！");
            }
        }
        try {
            boolean ret = dao.updateUser(userId, user);
            if (ret) {
                return new CrudResult(true);
            } else {
                return new CrudResult(false, "no user with id=" + userId + " were updated");
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


    @Override
    public CrudResult isCurrentUserAdmin(HttpServletRequest req) {
        String curUserName = null;
        if (null != req
                && null != (curUserName = (String) req.getSession().getAttribute(Constants.SESS_ATTR_NAME_USERNAME))) {
            try {
                User user = dao.getUserByUserName(curUserName);
                return new CrudResult(true, null != user && user.getEu_status() == User.STATUS_ADMIN);
            } catch (Throwable e) {
                return new CrudResult(false, e.getLocalizedMessage());
            }
        } else {
            return new CrudResult(false);
        }
    }

}
