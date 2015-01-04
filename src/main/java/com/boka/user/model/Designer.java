package com.boka.user.model;

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
	private int reservedCnt;
	//门店
	private Shop shop;

	@Override
	public String getProduct() {
		return product;
	}

	@Override
	public void setProduct(String product) {
		this.product = product;
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

	//产品
	private String product = "fzone";

}
