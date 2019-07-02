package com.ikspl.hps.Models;

import java.util.Date;

public class InstallmentDetailsModel {

    String headName,installment,amount,discount,latefee,payamt,paidamt,dueamt;//duedate,paiddate;
    Date duedate,paiddate;

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getLatefee() {
        return latefee;
    }

    public void setLatefee(String latefee) {
        this.latefee = latefee;
    }

    public String getPayamt() {
        return payamt;
    }

    public void setPayamt(String payamt) {
        this.payamt = payamt;
    }

    public String getPaidamt() {
        return paidamt;
    }

    public void setPaidamt(String paidamt) {
        this.paidamt = paidamt;
    }

    public String getDueamt() {
        return dueamt;
    }

    public void setDueamt(String dueamt) {
        this.dueamt = dueamt;
    }

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public Date getPaiddate() {
        return paiddate;
    }

    public void setPaiddate(Date paiddate) {
        this.paiddate = paiddate;
    }
}
