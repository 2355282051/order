package com.boka.user.dto;

import java.util.Date;

/**
 * Created by boka on 15-1-20.
 */
public class Pay {

    // 编号ID
    private String payId;
    // 支付方式
    private int payType;
    // 支付流水号
    private String payNum;
    // 订单号
    private String orderId;
    // 支付金额
    private double payAmount;
    // 使用数量
    private long useQuantity;
    // 支付日期
    private Date createDate;
    //
    private Date updateDate;

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayNum() {
        return payNum;
    }

    public void setPayNum(String payNum) {
        this.payNum = payNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public long getUseQuantity() {
        return useQuantity;
    }

    public void setUseQuantity(long useQuantity) {
        this.useQuantity = useQuantity;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }



}
