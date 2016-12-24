package com.education.online.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Great Gao on 2016/12/24.
 */
public class WalletInfoBean {
    private String balance = "";
    private String payment_amout ="";
    private String recharge_amount ="";
    private String withdraw_amount ="";
    private String page_total  ="";
    private String total ="";
    private String current_page  ="";
    private List<WalletLogBean> wallet_log = new ArrayList<>();

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPayment_amout() {
        return payment_amout;
    }

    public void setPayment_amout(String payment_amout) {
        this.payment_amout = payment_amout;
    }

    public String getRecharge_amount() {
        return recharge_amount;
    }

    public void setRecharge_amount(String recharge_amount) {
        this.recharge_amount = recharge_amount;
    }

    public String getWithdraw_amount() {
        return withdraw_amount;
    }

    public void setWithdraw_amount(String withdraw_amount) {
        this.withdraw_amount = withdraw_amount;
    }

    public String getPage_total() {
        return page_total;
    }

    public void setPage_total(String page_total) {
        this.page_total = page_total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public List<WalletLogBean> getWallet_log() {
        return wallet_log;
    }

    public void setWallet_log(List<WalletLogBean> wallet_log) {
        this.wallet_log = wallet_log;
    }
}
