package com.easybuy.control.biz.impl;

import com.easybuy.control.biz.ProductBiz;
import com.easybuy.control.dao.ProductDao;
import com.easybuy.control.dao.impl.ProductDaoImplMySql;
import com.easybuy.model.Product;
import com.easybuy.util.CrudResult;
import com.easybuy.util.PagingCriterion;

/**
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 5, 2016
 *
 */
public class ProductBizImpl implements ProductBiz {

    private ProductDao dao = new ProductDaoImplMySql();


    @Override
    public CrudResult addProduct(Product obj) {
        if (null == obj) {
            return new CrudResult(false, "invalid Product null!");
        }
        if (null == obj.getEp_name() || obj.getEp_name().trim().length() == 0) {
            return new CrudResult(false, "商品名称不能为空");
        }
        try {

            Product newObj = dao.addProduct(obj);
            return new CrudResult(true, newObj);

        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while accessing database. Error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult removeProduct(long id) {
        // TODO check orders that refer to this product
        try {
            boolean ret = dao.removeProduct(id);
            if (ret) {
                return new CrudResult(true);
            } else {
                return new CrudResult(false, "no product with id=" + id + " were removed from database");
            }
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while deleting product from database. ep_id= " + id
                    + ", error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult updateProduct(long id, Product obj) {
        if (null == obj) {
            return new CrudResult(false, "invalid Product null!");
        }
        String newName = obj.getEp_name();
        if (null == newName) {
            return new CrudResult(false, "商品名称不能为空");
        }
        try {
            boolean ret = dao.updateProduct(id, obj);
            if (ret) {
                return new CrudResult(true);
            } else {
                return new CrudResult(false, "no product with id=" + id + " were updated");
            }
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while updating product from database. ep_id= " + id
                    + ", error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult getProductById(long id) {
        try {
            Product obj = dao.getProductById(id);
            if (null == obj) {
                return new CrudResult(false, "No prod cat found with epc_id=" + id + "!");
            }
            return new CrudResult(true, obj);
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while reading product from database. ep_id= " + id
                    + ", error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult getAllProducts() {
        try {
            return new CrudResult(true, dao.getAllProducts());
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while reading products from database. Error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult getProductsInRange(int startRow, int endRow) {
        try {
            return new CrudResult(true, dao.getProductsInRange(startRow, endRow));
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while reading products from database. Error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult getProducts(long epcId) {
        try {
            return new CrudResult(true, dao.getAllProducts());
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while reading products from database. Error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult getProductsInRange(long epcId, int startRow, int endRow) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public PagingCriterion getPagingCriterion() {
        // TODO Auto-generated method stub
        return null;
    }

}
