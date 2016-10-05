package com.easybuy.model;

/**
 * Order detail
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 4, 2016
 *
 */
public class OrderDetail {

    long eod_id;
    long eo_id;
    long ep_id;
    long eod_quantity;
    double eod_cost;


    public long getEod_id() {
        return eod_id;
    }


    public void setEod_id(long eod_id) {
        this.eod_id = eod_id;
    }


    public long getEo_id() {
        return eo_id;
    }


    public void setEo_id(long eo_id) {
        this.eo_id = eo_id;
    }


    public long getEp_id() {
        return ep_id;
    }


    public void setEp_id(long ep_id) {
        this.ep_id = ep_id;
    }


    public long getEod_quantity() {
        return eod_quantity;
    }


    public void setEod_quantity(long eod_quantity) {
        this.eod_quantity = eod_quantity;
    }


    public double getEod_cost() {
        return eod_cost;
    }


    public void setEod_cost(double eod_cost) {
        this.eod_cost = eod_cost;
    }

}
