package com.easybuy.control.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easybuy.control.Constants;
import com.easybuy.control.biz.UserBiz;
import com.easybuy.control.biz.impl.UserBizImpl;
import com.easybuy.model.User;
import com.easybuy.util.CrudResult;
import com.easybuy.util.JsonUtils;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        String veryCode = request.getParameter("veryCode");

        String captcherAnswer = (String) request.getSession().getAttribute(Constants.SESS_ATTR_NAME_CAPTCHA_ANSWER);

        response.setContentType("text/json");

        if (!captcherAnswer.equalsIgnoreCase(veryCode)) {

            JsonUtils.writeAsJson(response.getWriter(), new CrudResult(false, "验证码不正确"));
        } else if (userName == null || passWord == null || userName.length() == 0 || passWord.length() == 0) {
            JsonUtils.writeAsJson(response.getWriter(), new CrudResult(false, "用户名/密码不能为空"));
        } else {
            UserBiz biz = new UserBizImpl();
            User user = (User) biz.getUserByUserName(userName).getData();
            if (null != user || !passWord.equals(user.getEu_password())) {
                JsonUtils.writeAsJson(response.getWriter(), new CrudResult(false, "用户名或密码错误"));
            } else {
                // put username into session
                request.getSession().setAttribute(Constants.SESS_ATTR_NAME_USERNAME, userName);
                JsonUtils.writeAsJson(response.getWriter(), new CrudResult(true));
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }

}