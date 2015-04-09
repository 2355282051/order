package com.boka.user.model;

import java.util.Date;

public class ReserveInfo {

    //预约状态
    private Integer status;
    //预约开始时间
    private Date startTime;
    //预约结束时间
    private Date endTime;
    //预约间隔
    private Integer interval;
    //最少提前几天预约
    private Integer inAdvanceMin;
    //最多提前几天预约
    private Integer inAdvanceMax;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getInAdvanceMin() {
        return inAdvanceMin;
    }

    public void setInAdvanceMin(Integer inAdvanceMin) {
        this.inAdvanceMin = inAdvanceMin;
    }

    public Integer getInAdvanceMax() {
        return inAdvanceMax;
    }

    public void setInAdvanceMax(Integer inAdvanceMax) {
        this.inAdvanceMax = inAdvanceMax;
    }
}
