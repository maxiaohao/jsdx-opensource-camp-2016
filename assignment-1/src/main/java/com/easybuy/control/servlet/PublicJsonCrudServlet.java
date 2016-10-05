package com.easybuy.control.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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

/**
 * This servlet serves all the public (non-admin) CRUD requests and always print a json string of {@link CrudResult}.
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 2, 2016
 *
 */
public class PublicJsonCrudServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String model = request.getParameter("model");
        String action = request.getParameter("action");

        PrintWriter writer = response.getWriter();

        if (null == model || null == action) {
            JsonUtils.writeAsJson(writer, new CrudResult(false, "无效的model或action参数"));
        }

        response.setContentType("text/json");

        switch (model) {

        case "user":

            UserBiz biz = new UserBizImpl();

            switch (action) {
            case "login": {
                String userName = request.getParameter("userName");
                String passWord = request.getParameter("passWord");
                String answer = request.getParameter("answer");
                String captcherAnswer = (String) request.getSession().getAttribute(
                        Constants.SESS_ATTR_NAME_CAPTCHA_ANSWER);
                if (!captcherAnswer.equalsIgnoreCase(answer)) {
                    JsonUtils.writeAsJson(writer, new CrudResult(false, "验证码不正确"));
                } else if (userName == null || passWord == null || userName.length() == 0 || passWord.length() == 0) {
                    JsonUtils.writeAsJson(writer, new CrudResult(false, "用户名/密码不能为空"));
                } else {
                    User user = (User) biz.getUserByUserName(userName).getData();
                    if (null == user || !passWord.equals(user.getEu_password())) {
                        JsonUtils.writeAsJson(writer, new CrudResult(false, "用户名或密码错误"));
                    } else {
                        // put username into session
                        request.getSession().setAttribute(Constants.SESS_ATTR_NAME_USERNAME, userName);
                        if (User.STATUS_ADMIN == user.getEu_status()) {
                            // if user is admin, put attr to session also
                            request.getSession().setAttribute(Constants.SESS_ATTR_NAME_IS_ADMIN, true);
                        }
                        JsonUtils.writeAsJson(writer, new CrudResult(true));
                    }
                }
                break;
            }
            case "logout":
                request.getSession().setAttribute(Constants.SESS_ATTR_NAME_USERNAME, null);
                JsonUtils.writeAsJson(writer, new CrudResult(true));
                break;
            case "get_current_user_name":
                JsonUtils.writeAsJson(writer, biz.getCurrentUserName(request));
                break;
            case "is_current_user_admin":
                JsonUtils.writeAsJson(writer, biz.isCurrentUserAdmin(request));
                break;
            case "register": {
                String userName = request.getParameter("userName");
                String passWord = request.getParameter("passWord");
                String rePassWord = request.getParameter("rePassWord");
                String answer = request.getParameter("answer");
                String captcherAnswer = (String) request.getSession().getAttribute(
                        Constants.SESS_ATTR_NAME_CAPTCHA_ANSWER);
                if (!captcherAnswer.equalsIgnoreCase(answer)) {
                    JsonUtils.writeAsJson(writer, new CrudResult(false, "验证码不正确"));
                } else if (userName == null || userName.length() == 0) {
                    JsonUtils.writeAsJson(writer, new CrudResult(false, "用户名不能为空"));
                } else if (passWord == null || passWord.length() == 0 || rePassWord == null || rePassWord.length() == 0) {
                    JsonUtils.writeAsJson(writer, new CrudResult(false, "密码不能为空"));
                } else if (!passWord.equals(rePassWord)) {
                    JsonUtils.writeAsJson(writer, new CrudResult(false, "两次输入的密码不一致"));
                } else {
                    User newUser = new User();
                    newUser.setEu_user_name(userName);
                    newUser.setEu_password(passWord);
                    CrudResult ret = biz.addUser(newUser);
                    if (ret.isSuccess()) {
                        // set as logged in
                        request.getSession().setAttribute(Constants.SESS_ATTR_NAME_USERNAME, userName);
                    }
                    JsonUtils.writeAsJson(writer, ret);
                }
                break;
            }
            default:
            }
            break;
        default:
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }

}
