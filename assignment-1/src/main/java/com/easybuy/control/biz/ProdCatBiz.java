package com.easybuy.control.biz;

import com.easybuy.model.ProdCat;
import com.easybuy.util.CrudResult;

/**
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 4, 2016
 *
 */
public interface ProdCatBiz {

    CrudResult addProdCat(ProdCat obj);


    CrudResult removeProdCat(long id);


    CrudResult updateProdCat(long id, ProdCat obj);


    CrudResult getProdCatById(long id);


    CrudResult getAllProdCats();

}
