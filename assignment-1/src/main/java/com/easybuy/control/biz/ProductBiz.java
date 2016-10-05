package com.easybuy.control.biz;

import com.easybuy.model.Product;
import com.easybuy.util.PagingCriterion;
import com.easybuy.util.CrudResult;

/**
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 4, 2016
 *
 */
public interface ProductBiz {

    CrudResult addProduct(Product obj);


    CrudResult removeProduct(long id);


    CrudResult updateProduct(long id, Product obj);


    CrudResult getProductById(long id);


    CrudResult getAllProducts();


    CrudResult getProducts(long epcId);


    CrudResult getProductsInRange(int startRow, int endRow);


    CrudResult getProductsInRange(long epcId, int startRow, int endRow);


    PagingCriterion getPagingCriterion();

}
