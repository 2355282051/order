package com.boka.user.model;

public class Shop {

    //编码
    private String id;
    //门店名称
    private String name;
    //地区
    private Region region;
    //地址
    private String address;
    //博卡连锁代码
    private String custId;
    //博卡公司代码
    private String compId;
    //S3状态
    private int s3Status;
    //连锁URL
    private String chainUrl;
    // 创建人
    private String creator;
    // 管理人
    private String admin;
    // 管理时间
    private String adminDate;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAdminDate() {
        return adminDate;
    }

    public void setAdminDate(String adminDate) {
        this.adminDate = adminDate;
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

    public int getS3Status() {
        return s3Status;
    }

    public void setS3Status(int s3Status) {
        this.s3Status = s3Status;
    }

    public String getChainUrl() {
        return chainUrl;
    }

    public void setChainUrl(String chainUrl) {
        this.chainUrl = chainUrl;
    }
}
