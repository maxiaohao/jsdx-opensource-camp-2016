package com.easybuy.control.dao;

import java.util.List;

import com.easybuy.model.ProdCat;
import com.easybuy.model.Product;

/**
 * DB CRUD interface for {@link Product}.
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 4, 2016
 *
 */
public interface ProdCatDao {

    ProdCat addProdCat(ProdCat obj) throws Throwable;


    boolean removeProdCat(long id) throws Throwable;


    boolean updateProdCat(long id, ProdCat obj) throws Throwable;


    ProdCat getProdCatById(long id) throws Throwable;


    List<ProdCat> getAllProdCats() throws Throwable;

}
