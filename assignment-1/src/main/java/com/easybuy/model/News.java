package com.easybuy.model;

import java.util.Date;

/**
 * News
 *
 * @author xma11 <maxiaohao@gmail.com>
 * @date Oct 4, 2016
 *
 */
public class News {

    long en_id;
    String en_title;
    String en_content;
    Date en_create_time;


    public long getEn_id() {
        return en_id;
    }


    public void setEn_id(long en_id) {
        this.en_id = en_id;
    }


    public String getEn_title() {
        return en_title;
    }


    public void setEn_title(String en_title) {
        this.en_title = en_title;
    }


    public String getEn_content() {
        return en_content;
    }


    public void setEn_content(String en_content) {
        this.en_content = en_content;
    }


    public Date getEn_create_time() {
        return en_create_time;
    }


    public void setEn_create_time(Date en_create_time) {
        this.en_create_time = en_create_time;
    }

}
