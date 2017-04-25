package com.education.online.bean;

/**
 * Created by Administrator on 2017/4/21.
 */

public class TransferHistory {
    private String create_at=""; // 申请提现时间
    private String amount=""; // 提现金额
    private String payment_name=""; // 选择收款账户名称
    private String transfer_sn=""; // 提现订单号
    private String status=""; // 提现状态(1：申请 2：拒绝 3：提现中 4.已提现 5.提现失败)
    private String approver_date=""; // 审核完成时间
    private String approver_info=""; // 审批意见
    private String payment_time=""; // 提现完成时间
    private String out_trade_sn=""; // 外部交易流水号
    private String status_desc="";

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    public String getTransfer_sn() {
        return transfer_sn;
    }

    public void setTransfer_sn(String transfer_sn) {
        this.transfer_sn = transfer_sn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApprover_date() {
        return approver_date;
    }

    public void setApprover_date(String approver_date) {
        this.approver_date = approver_date;
    }

    public String getApprover_info() {
        return approver_info;
    }

    public void setApprover_info(String approver_info) {
        this.approver_info = approver_info;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public String getOut_trade_sn() {
        return out_trade_sn;
    }

    public void setOut_trade_sn(String out_trade_sn) {
        this.out_trade_sn = out_trade_sn;
    }
}
