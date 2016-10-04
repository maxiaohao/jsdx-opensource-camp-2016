package com.easybuy.control.servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import com.easybuy.control.Constants;
import com.easybuy.control.biz.UserBiz;
import com.easybuy.control.biz.impl.UserBizImpl;
import com.easybuy.model.User;
import com.easybuy.util.CrudResult;

/**
 * This servlet serves all the management (admin) CRUD requests from jsp files using a session-based scenario.
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 2, 2016
 *
 */
public class AdminCrudServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CrudResult ret = null;

        String model = request.getParameter("model");
        String action = request.getParameter("action");

        switch (model) {

        case "user": {

            UserBiz biz = new UserBizImpl();

            String userId = request.getParameter("userId");
            String userName = request.getParameter("userName");
            String passWord = request.getParameter("passWord");
            String sex = request.getParameter("sex");
            String birthyear = request.getParameter("birthyear");
            String birthmonth = request.getParameter("birthmonth");
            String birthday = request.getParameter("birthday");
            String identityCode = request.getParameter("identityCode");
            String email = request.getParameter("email");
            String mobile = request.getParameter("mobile");
            String address = request.getParameter("address");
            String status = request.getParameter("status");

            User user = new User();

            user.setEu_user_name(userName);
            user.setEu_password(passWord);
            user.setEu_sex(sex);
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, NumberUtils.toInt(birthyear));
            c.set(Calendar.MONTH, NumberUtils.toInt(birthmonth) - 1);
            c.set(Calendar.DATE, NumberUtils.toInt(birthday));
            user.setEu_birthday(c.getTime());
            user.setEu_identity_code(identityCode);
            user.setEu_email(email);
            user.setEu_mobile(mobile);
            user.setEu_address(address);
            user.setEu_status(NumberUtils.toInt(status));

            switch (action) {
            case "add": {
                ret = biz.addUser(user);
                break;
            }
            case "update": {
                ret = biz.updateUser(Long.parseLong(userId), user);
                break;
            }

            case "delete": {
                ret = biz.removeUser(Long.parseLong(userId));
                break;
            }

            default:
            }
            request.setAttribute(Constants.REQ_ATTR_NAME_CURR_ADMIN_PAGE, "user.jsp");
        }
            break;
        default:
        }
        request.setAttribute(Constants.REQ_ATTR_NAME_CURR_ADMIN_CRUD_RESULT, ret);
        request.getRequestDispatcher(Constants.PAGE_ADMIN_MANAGE_RESULT).forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        doGet(request, response);
    }

}
