package com.boka.user.dto;

import com.boka.user.model.Location;
import com.boka.user.model.Profession;
import com.boka.user.model.Region;
import com.boka.user.model.Shop;

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
    //注册产品
    private String regProduct;
    //激活状态 0 未激活　1:激活　2:未认证激活
    private int activatedStatus;
    //token
    private String access_token;

    // 注册时间
    private Date createDate;

    // 邀请码
    private String inviteCode;

    // 会员失效时间
    private Date expireDate;

    //门店
    private Shop shop;

    //管理员状态
    private Integer adminStatus;

    //员工编码
    private String empId;

    //真实姓名
    private String realName;

    //重置密码状态
    private Integer resetStatus;

    // 员工序列号
    private String empSerial;

    // 接受加入状态
    private int acceptStatus;

    // 月薪
    private Double salary;

    //职业
    private Profession profession;

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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getRegProduct() {
        return regProduct;
    }

    public void setRegProduct(String regProduct) {
        this.regProduct = regProduct;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Integer getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Integer adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getResetStatus() {
        return resetStatus;
    }

    public void setResetStatus(Integer resetStatus) {
        this.resetStatus = resetStatus;
    }

    public String getEmpSerial() {
        return empSerial;
    }

    public void setEmpSerial(String empSerial) {
        this.empSerial = empSerial;
    }

    public int getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(int acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }
}
