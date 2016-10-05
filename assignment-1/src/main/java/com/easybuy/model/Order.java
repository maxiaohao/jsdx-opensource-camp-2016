package com.easybuy.model;

import java.util.Date;

/**
 * Order
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 4, 2016
 *
 */
public class Order {

    public final static int ORDER_STATUS_CREATE = 1;
    public final static int ORDER_STATUS_PASS_AUDIT = 2;
    public final static int ORDER_STATUS_ALLOCATION = 3;
    public final static int ORDER_STATUS_DELIVERING = 4;
    public final static int ORDER_STATUS_FINISHED = 5;
    public final static int ORDER_TYPE_PAY_ON_DELIVERY = 1;
    public final static int ORDER_TYPE_PAY_ONLINE = 2;

    long eo_id;
    long eo_user_id;
    String eo_user_name;
    String eo_user_address;
    Date eo_create_time;
    double eo_cost;
    int eo_status;
    int eo_type;


    public long getEo_id() {
        return eo_id;
    }


    public void setEo_id(long eo_id) {
        this.eo_id = eo_id;
    }


    public long getEo_user_id() {
        return eo_user_id;
    }


    public void setEo_user_id(long eo_user_id) {
        this.eo_user_id = eo_user_id;
    }


    public String getEo_user_name() {
        return eo_user_name;
    }


    public void setEo_user_name(String eo_user_name) {
        this.eo_user_name = eo_user_name;
    }


    public String getEo_user_address() {
        return eo_user_address;
    }


    public void setEo_user_address(String eo_user_address) {
        this.eo_user_address = eo_user_address;
    }


    public Date getEo_create_time() {
        return eo_create_time;
    }


    public void setEo_create_time(Date eo_create_time) {
        this.eo_create_time = eo_create_time;
    }


    public double getEo_cost() {
        return eo_cost;
    }


    public void setEo_cost(double eo_cost) {
        this.eo_cost = eo_cost;
    }


    public int getEo_status() {
        return eo_status;
    }


    public void setEo_status(int eo_status) {
        this.eo_status = eo_status;
    }


    public int getEo_type() {
        return eo_type;
    }


    public void setEo_type(int eo_type) {
        this.eo_type = eo_type;
    }

}
