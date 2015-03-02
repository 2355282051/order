package com.boka.user.model;

import com.boka.common.constant.ProductType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_info")
public class Designer extends User {

    //用户级别
    private int level = 0;
    //博卡连锁代码
    private String custId;
    //博卡公司代码
    private String compId;
    //博卡员工代码
    private String empId;
    //排名
    private Integer rank;
    //被预约数
    private int reserveCnt;
    //被预约数
    private int reservedCnt;

    //门店
    private Shop shop;
    //平均分
    private Integer score;
    //预约信息
    private ReserveInfo reserveInfo;
    //粉丝数
    private int fansCount;
    //作品数
    private int workCount;

    // 用户等级
    private String grade;
    // 剪发价格
    private double haircutPrice;
    // 烫染价格
    private double modelingPrice;
    //评论数
    private int commentCount;
    //喜欢数
    private int likeCount;
    //签名
    private String signature;

    public int getReservedCnt() {
        return reservedCnt;
    }

    public void setReservedCnt(int reservedCnt) {
        this.reservedCnt = reservedCnt;
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

    public Designer() {
        product = ProductType.FZONE;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
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

    public ReserveInfo getReserveInfo() {
        return reserveInfo;
    }

    public void setReserveInfo(ReserveInfo reserveInfo) {
        this.reserveInfo = reserveInfo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getHaircutPrice() {
        return haircutPrice;
    }

    public void setHaircutPrice(double haircutPrice) {
        this.haircutPrice = haircutPrice;
    }

    public int getReserveCnt() {
        return reserveCnt;
    }

    public void setReserveCnt(int reserveCnt) {
        this.reserveCnt = reserveCnt;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public double getModelingPrice() {
        return modelingPrice;
    }

    public void setModelingPrice(double modelingPrice) {
        this.modelingPrice = modelingPrice;
    }
}
