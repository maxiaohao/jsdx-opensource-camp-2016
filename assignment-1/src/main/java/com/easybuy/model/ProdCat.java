package com.easybuy.model;

/**
 * Product category
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 4, 2016
 *
 */
public class ProdCat {

    long epc_id;
    String epc_name;
    long epc_parent_id;


    public long getEpc_id() {
        return epc_id;
    }


    public void setEpc_id(long epc_id) {
        this.epc_id = epc_id;
    }


    public String getEpc_name() {
        return epc_name;
    }


    public void setEpc_name(String epc_name) {
        this.epc_name = epc_name;
    }


    public long getEpc_parent_id() {
        return epc_parent_id;
    }


    public void setEpc_parent_id(long epc_parent_id) {
        this.epc_parent_id = epc_parent_id;
    }

}
