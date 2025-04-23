package com.lyranxi.link.common.page;

import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ranxi
 * @date 2025-03-14 11:09
 */
@ToString
public class PageInfo {

    /**
     * 页数
     */
    protected int pageNum = 1;
    /**
     * 每页条数,默认20,最大200
     */
    protected int pageSize = 20;
    /**
     * 最大更新时间
     */
    protected LocalDateTime maxUpdateTime;
    /**
     * 最大ID
     */
    protected Serializable maxId;

    public PageInfo() {
    }

    public PageInfo(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public LocalDateTime getMaxUpdateTime() {
        return maxUpdateTime;
    }

    public void setMaxUpdateTime(LocalDateTime maxUpdateTime) {
        this.maxUpdateTime = maxUpdateTime;
    }

    public Serializable getMaxId() {
        return maxId;
    }

    public void setMaxId(Serializable maxId) {
        this.maxId = maxId;
    }


    public String limitSql() {
        return "limit " + ((pageNum - 1) * pageSize) + "," + pageSize;
    }

    public static PageInfo DEFAULT() {
        return new PageInfo();
    }
}
