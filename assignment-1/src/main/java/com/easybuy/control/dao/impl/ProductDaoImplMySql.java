package com.easybuy.control.dao.impl;

import java.util.List;

import com.easybuy.control.dao.ProductDao;
import com.easybuy.model.Product;
import com.easybuy.model.User;
import com.easybuy.util.db.GenericDao;

/**
 * MySQL CRUD implementation for {@link Product}.
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 4, 2016
 *
 */
public class ProductDaoImplMySql extends GenericDao implements ProductDao {

    @Override
    public Product addProduct(Product obj) throws Throwable {
        if (null == obj) {
            return null;
        }
        int newId = insert(
                User.class,
                "insert into easybuy_product (ep_name, ep_description, ep_price, ep_stock, epc_id, epc_child_id, ep_file_name) "
                        + "values (?,?,?,?,?,?,?)",
                obj.getEp_name(), obj.getEp_description(), obj.getEp_price(), obj.getEp_stock(), obj.getEpc_id(),
                obj.getEpc_child_id(), obj.getEp_file_name());
        obj.setEp_id(newId);
        return obj;
    }


    @Override
    public boolean removeProduct(long id) throws Throwable {
        return exec("delete from easybuy_product where ep_id=?", id) > 0;
    }


    @Override
    public boolean updateProduct(long id, Product obj) throws Throwable {
        return exec(
                "update easybuy_product set ep_name=?, ep_description=?, ep_price=?"
                        + ", ep_stock=?, epc_id=?, epc_child_id=?, ep_file_name=? where ep_id=?",
                obj.getEp_name(), obj.getEp_description(), obj.getEp_price(), obj.getEp_stock(), obj.getEpc_id(),
                obj.getEpc_child_id(), obj.getEp_file_name(), id) > 0;
    }


    @Override
    public Product getProductById(long id) throws Throwable {
        return queryObj(Product.class, "select * from easybuy_product where ep_id=?", id);
    }


    @Override
    public long getAllProductCount() throws Throwable {
        return ((Number) query1stCell("select count(*) from easybuy_product")).longValue();
    }


    @Override
    public List<Product> getAllProducts() throws Throwable {
        return queryObjs(Product.class, "select * from easybuy_product order by ep_id");
    }


    @Override
    public long getProductCountInCat(long epcId) throws Throwable {
        return ((Number) query1stCell("select count(*) from easybuy_product where epc_id=?", epcId)).longValue();
    }


    @Override
    public List<Product> getProductsInCat(long epcId) throws Throwable {
        return queryObjs(Product.class, "select * from easybuy_product where epc_id=? order by ep_id", epcId);
    }


    @Override
    public List<Product> getProductsInRange(long startRow, long endRow) throws Throwable {
        return queryObjs(Product.class, "select * from easybuy_product order by ep_id limit ?,?", startRow, endRow
                - startRow + 1);
    }


    @Override
    public List<Product> getProductsInCatInRange(long epcId, long startRow, long endRow) throws Throwable {
        return queryObjs(Product.class, "select * from easybuy_product where epc_id=? order by ep_id limit ?,?", epcId,
                startRow, endRow
                        - startRow + 1);
    }

}
