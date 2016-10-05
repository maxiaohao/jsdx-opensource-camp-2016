package com.easybuy.control.biz;

import com.easybuy.model.Product;
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


    CrudResult getAllProductCount();


    CrudResult getProductsInCat(long epcId);


    CrudResult getProductCountInCat(long epcId);


    /**
     *
     * @param startRow
     *            start row num (0-based) in total rows, inclusive
     * @param startRow
     *            end row num (0-based) in total rows, inclusive
     * @return
     */
    CrudResult getProductsInRange(long startRow, long endRow);


    /**
     *
     * @param epcId
     *            product cat id
     * @param startRow
     *            start row num (0-based) in total rows, inclusive
     * @param startRow
     *            end row num (0-based) in total rows, inclusive
     * @return
     */
    CrudResult getProductsInCatInRange(long epcId, long startRow, long endRow);

}
