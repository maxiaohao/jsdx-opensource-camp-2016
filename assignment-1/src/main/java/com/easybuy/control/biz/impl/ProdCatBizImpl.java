package com.easybuy.control.biz.impl;

import com.easybuy.control.biz.ProdCatBiz;
import com.easybuy.control.dao.ProdCatDao;
import com.easybuy.control.dao.impl.ProdCatDaoImplMySql;
import com.easybuy.model.ProdCat;
import com.easybuy.util.CrudResult;

/**
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 5, 2016
 *
 */
public class ProdCatBizImpl implements ProdCatBiz {

    private ProdCatDao dao = new ProdCatDaoImplMySql();


    @Override
    public CrudResult addProdCat(ProdCat obj) {
        if (null == obj) {
            return new CrudResult(false, "invalid prodCat null!");
        }
        if (null == obj.getEpc_name() || obj.getEpc_name().trim().length() == 0) {
            return new CrudResult(false, "分类名称不能为空");
        }
        try {

            ProdCat newObj = dao.addProdCat(obj);
            return new CrudResult(true, newObj);

        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while accessing database. Error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult removeProdCat(long id) {
        // TODO check products that refer to this cat
        try {
            boolean ret = dao.removeProdCat(id);
            if (ret) {
                return new CrudResult(true);
            } else {
                return new CrudResult(false, "no prod cat with id=" + id + " were removed from database");
            }
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while deleting prod cat from database. epc_id= " + id
                    + ", error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult updateProdCat(long id, ProdCat obj) {
        if (null == obj) {
            return new CrudResult(false, "invalid ProdCat null!");
        }
        String newCatName = obj.getEpc_name();
        if (null == newCatName) {
            return new CrudResult(false, "类型名称不能为空");
        }
        if(obj.getEpc_parent_id()==id){
            return new CrudResult(false, "不能选择自身为父分类");
        }
        try {
            boolean ret = dao.updateProdCat(id, obj);
            if (ret) {
                return new CrudResult(true);
            } else {
                return new CrudResult(false, "no prod cat with id=" + id + " were updated");
            }
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while updating prod cat from database. epc_id= " + id
                    + ", error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult getProdCatById(long id) {
        try {
            ProdCat obj = dao.getProdCatById(id);
            if (null == obj) {
                return new CrudResult(false, "No prod cat found with epc_id=" + id + "!");
            }
            return new CrudResult(true, obj);
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while reading prod cat from database. epc_id= " + id
                    + ", error msg: "
                    + e.getLocalizedMessage());
        }
    }


    @Override
    public CrudResult getAllProdCats() {
        try {
            return new CrudResult(true, dao.getAllProdCats());
        } catch (Throwable e) {
            return new CrudResult(false, "Exception occurred while reading prod cats from database. Error msg: "
                    + e.getLocalizedMessage());
        }
    }

}
