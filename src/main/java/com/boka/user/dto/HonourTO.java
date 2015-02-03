package com.boka.user.dto;

import com.boka.user.model.Honour;
import com.boka.user.model.WorkImage;

import java.util.Date;
import java.util.List;

/**
 * Created by boka on 15-2-2.
 */

public class HonourTO {

    private String id;
    // 发型师ID
    private String designerId;
    // 主题
    private String title;
    // 描述
    private String desc;
    // 图片集
    private List<WorkImage> workImages;
    // 更新时间
    private Date updateDate;

    public HonourTO() {}

    public HonourTO(Honour honour) {
        this.id = honour.getId();
        this.desc = honour.getDesc();
        this.title = honour.getTitle();
        this.workImages = honour.getWorkImages();
        this.updateDate = honour.getUpdateDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getDesignerId() {
        return designerId;
    }

    public void setDesignerId(String designerId) {
        this.designerId = designerId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<WorkImage> getWorkImages() {
        return workImages;
    }

    public void setWorkImages(List<WorkImage> workImages) {
        this.workImages = workImages;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
