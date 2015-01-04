package com.boka.user.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.boka.user.constant.URLConstant;

@Document(collection="user_info")
public class User {

	//编码
	@Id
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
	//创建时间
	private Date createDate;
	//更新时间
	private Date updateDate;
	//最后登录时间
	private Date lastLoginDate;
	//最近位置
	private Location loc;
	//QQopenId
	private String qqId;
	//微信openId
	private String wechatId;
	//随机盐值
	private String salt;
	//激活状态
	private int activatedStatus;
	//区域
	private Region region;
	//详细地址
	private String address;
	//产品
	private String product;
	//距离
	@Transient
	private Double distance;

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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		if(avatar.indexOf("http") == -1) {
			this.avatar = URLConstant.IMAGE_URL + avatar;
		} else {
			this.avatar = avatar;
		}
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getActivatedStatus() {
		return activatedStatus;
	}

	public void setActivatedStatus(int activatedStatus) {
		this.activatedStatus = activatedStatus;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
}