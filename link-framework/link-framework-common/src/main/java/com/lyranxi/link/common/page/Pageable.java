package com.lyranxi.link.common.page;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据接口
 * @author ranxi
 * @date 2025-04-18 11:39
 */
@Data
public class Pageable<T> implements Serializable {

    protected int pageNum = 1;
    protected int pageSize = 20;

    protected int totalNum = 0;
    protected int totalPage = 0;

    protected List<T> data;

    public Pageable(){}


    public Pageable(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Pageable(int pageNum, int pageSize, int totalNum, int totalPage, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.totalPage = totalPage;
        this.data = data;
    }



}
