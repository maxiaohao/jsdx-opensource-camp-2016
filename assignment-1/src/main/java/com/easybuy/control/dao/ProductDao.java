package com.easybuy.control.dao;

import java.util.List;

import com.easybuy.model.Product;

/**
 * DB CRUD interface for {@link Product}.
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 4, 2016
 *
 */
public interface ProductDao {

    Product addProduct(Product obj) throws Throwable;


    boolean removeProduct(long id) throws Throwable;


    boolean updateProduct(long id, Product obj) throws Throwable;


    Product getProductById(long id) throws Throwable;


    long getAllProductsCount() throws Throwable;


    List<Product> getAllProducts() throws Throwable;


    long getProductsCount(long epcId) throws Throwable;


    List<Product> getProducts(long epcId) throws Throwable;


    List<Product> getProductsInRange(int startRow, int endRow) throws Throwable;


    List<Product> getProductsInRange(long epcId, int startRow, int endRow) throws Throwable;

}
