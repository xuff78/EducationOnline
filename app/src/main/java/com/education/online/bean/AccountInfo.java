package com.education.online.bean;

import java.io.Serializable;

/**
 * Created by 可爱的蘑菇 on 2017/4/18.
 */

public class AccountInfo implements Serializable{

    private String account_type="";
    private String account_name="";
    private String account_no="";

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }
}
