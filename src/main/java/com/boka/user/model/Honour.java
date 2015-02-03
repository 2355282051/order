package com.boka.user.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by boka on 15-2-2.
 */

@Document(collection="designer_honour")
public class Honour {

    @Id
    private String id;
    // 发型师ID
    private String designerId;
    // 发型量信息
    private Designer designer;
    // 主题
    private String title;
    // 描述
    private String desc;
    // 图片集
    private List<WorkImage> workImages;
    // 创建时间
    private Date createDate;
    // 更新时间
    private Date updateDate;

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

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
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
}
