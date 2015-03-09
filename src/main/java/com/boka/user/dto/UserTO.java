package com.boka.user.dto;

import com.boka.user.model.Location;
import com.boka.user.model.Region;

import java.util.Date;

public class UserTO {

    //编码
    private String id;
    //手机号
    private String mobile;
    //昵称
    private String name;
    //性别
    private int sex;
    //密码
    private String password;
    //头像
    private String avatar;
    //最近位置
    private Location loc;
    //地理信息
    private Region region;
    //QQopenId
    private String qqId;
    //微信openId
    private String wechatId;
    //验证码
    private String authcode;
    //产品
    private String product;
    //激活状态 0 未激活　１：激活　２:未认证激活
    private int activatedStatus;
    //token
    private String access_token;

    private Date createDate; // 注册时间

    private String inviteCode; // 邀请码


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getActivatedStatus() {
        return activatedStatus;
    }

    public void setActivatedStatus(int activatedStatus) {
        this.activatedStatus = activatedStatus;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
