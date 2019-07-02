package com.ikspl.hps.Models;

public class FeesDetailsModel {

    String fees_HeadName;
    Float headAmounts,discount,total;
    Integer installments;

    public String getFees_HeadName() {
        return fees_HeadName;
    }

    public void setFees_HeadName(String fees_HeadName) {
        this.fees_HeadName = fees_HeadName;
    }

    public Float getHeadAmounts() {
        return headAmounts;
    }

    public void setHeadAmounts(Float headAmounts) {
        this.headAmounts = headAmounts;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }
}
