package com.easybuy.control.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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

        String prodImageUploadPath = request.getSession().getServletContext().getRealPath("/images/product");

        Map<String, String> paramMap = new HashMap<String, String>();
        List<FileItem> files = new ArrayList<FileItem>();
        // handle multipart/form-data request
        if (null != request.getContentType() && request.getContentType().startsWith("multipart/form-data")) {
            try {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                // factory.setRepository(path);
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> list = upload.parseRequest(request);
                upload.setHeaderEncoding("UTF-8");
                for (FileItem item : list) {
                    if (!item.isFormField()) {
                        files.add(item);
                    } else {
                        paramMap.put(item.getFieldName(), item.getString("UTF-8"));
                        // System.out.println("param-> " + item.getFieldName() + ": " + item.getString("UTF-8"));
                    }
                }
            } catch (Exception e) {
                ret = new CrudResult(false, "文件上传出错，错误信息" + e.getLocalizedMessage());
            }

            model = paramMap.get("model");
            action = paramMap.get("action");
        }

        if (null == model || null == action) {
            request.setAttribute(Constants.REQ_ATTR_NAME_CURR_ADMIN_CRUD_RESULT, new CrudResult(false,
                    "无效的model或action参数"));
            request.getRequestDispatcher(Constants.PAGE_ADMIN_MANAGE_RESULT).forward(request, response);
            return;
        }

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

            long epId = NumberUtils.toLong(paramMap.get("epId"));

            Product obj = new Product();
            obj.setEp_name(paramMap.get("productName"));
            obj.setEp_description(paramMap.get("description"));
            obj.setEp_price(NumberUtils.toDouble(paramMap.get("price")));
            obj.setEp_stock(NumberUtils.toLong(paramMap.get("stock")));
            obj.setEpc_id(NumberUtils.toLong(paramMap.get("epcId")));

            if (files.size() > 0) {
                FileItem fi = files.get(0);
                String fileName = fi.getName();
                fileName = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length());
                fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                String uploadFileName = epId + "." + fileExt;
                obj.setEp_file_name(uploadFileName);
                try {
                    fi.write(new File(prodImageUploadPath, uploadFileName));
                } catch (Exception e) {
                    ret = new CrudResult(false, "文件上传出错，错误信息" + e.getLocalizedMessage());
                    break;
                }

            }

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
                if (ret.isSuccess()) {
                    // delete the image file too
                    File img = new File(prodImageUploadPath,
                            ((Product) biz.getProductById(epId).getData()).getEp_file_name());
                    if (img.isFile()) {
                        img.delete();
                    }
                }
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
