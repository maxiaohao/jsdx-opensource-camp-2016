package com.easybuy.control.dao.impl;

import java.util.List;

import com.easybuy.control.dao.ProdCatDao;
import com.easybuy.model.ProdCat;
import com.easybuy.model.User;
import com.easybuy.util.db.GenericDao;

/**
 * MySQL CRUD implementation for {@link ProdCat}.
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 4, 2016
 *
 */
public class ProdCatDaoImplMySql extends GenericDao implements ProdCatDao {

    @Override
    public ProdCat addProdCat(ProdCat obj) throws Throwable {
        if (null == obj) {
            return null;
        }
        int newId = insert(
                User.class,
                "insert into easybuy_product_category (epc_name, epc_parent_id) values (?,?)",
                obj.getEpc_name(), obj.getEpc_parent_id() <= 0 ? null : obj.getEpc_parent_id());
        obj.setEpc_id(newId);
        return obj;
    }


    @Override
    public boolean removeProdCat(long id) throws Throwable {
        return exec("delete from easybuy_product_category where epc_id=?", id) > 0;
    }


    @Override
    public boolean updateProdCat(long id, ProdCat obj) throws Throwable {
        return exec(
                "update easybuy_product_category set epc_name=?, epc_parent_id=? where epc_id=?",
                obj.getEpc_name(), obj.getEpc_parent_id() <= 0 ? null : obj.getEpc_parent_id(), id) > 0;
    }


    @Override
    public ProdCat getProdCatById(long id) throws Throwable {
        return queryObj(ProdCat.class, "select * from easybuy_product_category where epc_id=?", id);
    }


    @Override
    public List<ProdCat> getAllProdCats() throws Throwable {
        return queryObjs(ProdCat.class, "select * from easybuy_product_category order by epc_id");
    }

}
