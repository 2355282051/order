package com.boka.user.dto;

/**
 * Created by boka on 14-12-29.
 */
public class DeviceTO {

    private String deviceId; // 设备ID
    private String phone; // 手机号码
    private String phoneType; // 手机类型
    private String phoneVersion; // 手机型号
    private String appName; // 应用名称
    private String appVersion; //　应用版本
    private String userId; //　用户ID
    private String userName; // 用户名称

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
}
