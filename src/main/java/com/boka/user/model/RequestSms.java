package com.boka.user.model;

import java.io.Serializable;

/**
 * Created by boka on 14-12-19.
 */
public class RequestSms implements Serializable {

    private String sms_key;
    private String phone;
    private String product;
    private String user_id;
    private String user_name;
    private String device_id;
    private String phone_type;
    private String phone_version;
    private String app_name;
    private String app_version;
    private int type;
    private String content;

    public String getSms_key() {
        return sms_key;
    }

    public void setSms_key(String sms_key) {
        this.sms_key = sms_key;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPhone_type() {
        return phone_type;
    }

    public void setPhone_type(String phone_type) {
        this.phone_type = phone_type;
    }

    public String getPhone_version() {
        return phone_version;
    }

    public void setPhone_version(String phone_version) {
        this.phone_version = phone_version;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
