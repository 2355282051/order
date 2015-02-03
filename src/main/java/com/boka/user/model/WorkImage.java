package com.boka.user.model;

import com.boka.common.constant.URLConstant;

public class WorkImage {

	//图片编号
	private String id;
	//图片地址
	private String url;
	//缩略图地址
	private String thumbnailUrl;
	//图片类型
	private String type;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = URLConstant.HEADER_URL + url;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = URLConstant.HEADER_URL + thumbnailUrl + "?imageView2/1/w/100/h/100";
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
