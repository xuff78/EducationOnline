package com.education.online.bean;

/**
 * Created by Great Gao on 2016/12/24.
 */
public class WalletLogBean {
    private String content  ="";
    private String amount ="";
    private String balance ="";
    private String created_at ="";

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
