package com.example.cinema.vo;

import com.example.cinema.po.RefundStrategy;

public class RefundStrategyVO {

    public RefundStrategyVO(RefundStrategy refundStrategy){
        this.id=refundStrategy.getId();
        this.startTime=refundStrategy.getStartTime();
        this.endTime=refundStrategy.getEndTime();
        this.percent=refundStrategy.getPercent();
    }

    private Integer id;
    /**
     * 策略规定时间
     */
    private Double startTime;
    private Double endTime;
    /**
     * 时间对应折扣
     */
    private Double percent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getStartTime() {
        return startTime;
    }

    public void setStartTime(Double startTime) {
        this.startTime = startTime;
    }

    public Double getEndTime() {
        return endTime;
    }

    public void setEndTime(Double endTime) {
        this.endTime = endTime;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
