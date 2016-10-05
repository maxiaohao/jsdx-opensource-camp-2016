package com.easybuy.control.servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import com.easybuy.control.Constants;
import com.easybuy.control.biz.ProdCatBiz;
import com.easybuy.control.biz.ProductBiz;
import com.easybuy.control.biz.UserBiz;
import com.easybuy.control.biz.impl.ProdCatBizImpl;
import com.easybuy.control.biz.impl.ProductBizImpl;
import com.easybuy.control.biz.impl.UserBizImpl;
import com.easybuy.model.ProdCat;
import com.easybuy.model.Product;
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
                ret = biz.updateUser(NumberUtils.toInt(userId), user);
                break;
            }

            case "delete": {
                ret = biz.removeUser(NumberUtils.toInt(userId));
                break;
            }

            default:
            }
            request.setAttribute(Constants.REQ_ATTR_NAME_CURR_ADMIN_PAGE, "user.jsp");
        }
            break;

        case "prodCat": {
            ProdCatBiz biz = new ProdCatBizImpl();

            String epcId = request.getParameter("epcId");
            String epcName = request.getParameter("className");
            String parentId = request.getParameter("parentId");

            ProdCat obj = new ProdCat();

            obj.setEpc_name(epcName);
            obj.setEpc_parent_id(NumberUtils.toLong(parentId));

            switch (action) {
            case "add": {
                ret = biz.addProdCat(obj);
                break;
            }
            case "update": {
                ret = biz.updateProdCat(NumberUtils.toLong(epcId), obj);
                break;
            }

            case "delete": {
                ret = biz.removeProdCat(NumberUtils.toLong(epcId));
                break;
            }
            default:
            }
            request.setAttribute(Constants.REQ_ATTR_NAME_CURR_ADMIN_PAGE, "productClass.jsp");
        }
            break;

        case "product": {
            ProductBiz biz = new ProductBizImpl();

            long epId = NumberUtils.toLong(request.getParameter("epId"));

            Product obj = new Product();
            obj.setEp_name(request.getParameter("productName"));
            obj.setEp_description(request.getParameter("description"));
            obj.setEp_price(NumberUtils.toDouble(request.getParameter("price")));
            obj.setEp_stock(NumberUtils.toLong(request.getParameter("stock")));
            obj.setEpc_id(NumberUtils.toLong(request.getParameter("epcId")));

            switch (action) {
            case "add": {
                ret = biz.addProduct(obj);
                break;
            }
            case "update": {
                ret = biz.updateProduct(epId, obj);
                break;
            }

            case "delete": {
                ret = biz.removeProduct(epId);
                break;
            }
            default:
            }
            request.setAttribute(Constants.REQ_ATTR_NAME_CURR_ADMIN_PAGE, "product.jsp");
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
