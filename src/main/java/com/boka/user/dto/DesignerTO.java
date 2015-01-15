package com.boka.user.dto;

import com.boka.user.model.Designer;
import com.boka.user.model.Region;
import com.boka.user.model.ReserveInfo;
import com.boka.user.model.Shop;

public class DesignerTO {

    //编码
    private String id;
    //昵称
    private String name;
    //手机号
    private String mobile;
    //性别
    private int sex;
    //头像
    private String avatar;
    //用户级别
    private int level = 0;
    //排名
    private Integer rank;
    //被预约数
    private int reservedCnt;
    //门店
    private Shop shop;
    //平均分
    private Integer score;
    //区域
    private Region region;
    //详细地址
    private String address;
    //距离
    private Double distance;
    //预约信息
    private ReserveInfo reserveInfo;
    //员工编码
    private String empId;
    //粉丝数
    private int fansCount;
    //作品数
    private int workCount;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getWorkCount() {
        return workCount;
    }

    public void setWorkCount(int workCount) {
        this.workCount = workCount;
    }

    public DesignerTO() {
    }

    public DesignerTO(Designer bean) {
        this.id = bean.getId();
        this.name = bean.getName();
        this.sex = bean.getSex();
        this.avatar = bean.getAvatar();
        this.level = bean.getLevel();
        this.rank = bean.getRank();
        this.reservedCnt = bean.getReservedCnt();
        this.shop = bean.getShop();
        this.score = bean.getScore();
        this.region = bean.getRegion();
        this.address = bean.getAddress();
        this.distance = bean.getDistance();
        this.reserveInfo = bean.getReserveInfo();
        this.empId = bean.getEmpId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public int getReservedCnt() {
        return reservedCnt;
    }

    public void setReservedCnt(int reservedCnt) {
        this.reservedCnt = reservedCnt;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public ReserveInfo getReserveInfo() {
        return reserveInfo;
    }

    public void setReserveInfo(ReserveInfo reserveInfo) {
        this.reserveInfo = reserveInfo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
