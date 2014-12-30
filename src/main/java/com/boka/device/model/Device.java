package com.boka.device.model;

import java.util.Date;

/**
 * Created by boka on 14-12-29.
 */
public class Device {

    private String id;
    private String deviceId; // 设备ID
    private String phone; // 手机号码
    private String phoneType; // 手机类型
    private String phoneVersion; // 手机型号
    private String appName; // 应用名称
    private String appVersion; //　应用版本
    private String userId; //　用户ID
    private String userName; // 用户名称
    private Date createTime; //　创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getPhoneVersion() {
        return phoneVersion;
    }

    public void setPhoneVersion(String phoneVersion) {
        this.phoneVersion = phoneVersion;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
