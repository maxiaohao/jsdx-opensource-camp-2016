package com.easybuy.control.biz;

import javax.servlet.http.HttpServletRequest;

import com.easybuy.model.User;
import com.easybuy.util.CrudResult;

/**
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Sep 28, 2016
 *
 */
public interface UserBiz {

    CrudResult addUser(User user);


    CrudResult removeUser(long userId);


    CrudResult updateUser(long userId, User user);


    CrudResult getUserById(long userId);


    CrudResult getUserByUserName(String userName);


    CrudResult getAllUsers();


    CrudResult getCurrentUserName(HttpServletRequest req);

    CrudResult isCurrentUserAdmin(HttpServletRequest req);
}
