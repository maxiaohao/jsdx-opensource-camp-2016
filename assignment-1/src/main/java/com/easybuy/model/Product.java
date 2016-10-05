package com.easybuy.model;

/**
 * Product
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 4, 2016
 *
 */
public class Product {

    long ep_id;
    String ep_name;
    String ep_description;
    double ep_price;
    long ep_stock;
    long epc_id;
    long epc_child_id;
    String ep_file_name;


    public long getEp_id() {
        return ep_id;
    }


    public void setEp_id(long ep_id) {
        this.ep_id = ep_id;
    }


    public String getEp_name() {
        return ep_name;
    }


    public void setEp_name(String ep_name) {
        this.ep_name = ep_name;
    }


    public String getEp_description() {
        return ep_description;
    }


    public void setEp_description(String ep_description) {
        this.ep_description = ep_description;
    }


    public double getEp_price() {
        return ep_price;
    }


    public void setEp_price(double ep_price) {
        this.ep_price = ep_price;
    }


    public long getEp_stock() {
        return ep_stock;
    }


    public void setEp_stock(long ep_stock) {
        this.ep_stock = ep_stock;
    }


    public long getEpc_id() {
        return epc_id;
    }


    public void setEpc_id(long epc_id) {
        this.epc_id = epc_id;
    }


    public long getEpc_child_id() {
        return epc_child_id;
    }


    public void setEpc_child_id(long epc_child_id) {
        this.epc_child_id = epc_child_id;
    }


    public String getEp_file_name() {
        return ep_file_name;
    }


    public void setEp_file_name(String ep_file_name) {
        this.ep_file_name = ep_file_name;
    }

}
