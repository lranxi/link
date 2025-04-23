package com.lyranxi.link.mysql.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lyranxi.link.common.page.PageInfo;
import com.lyranxi.link.common.page.Pageable;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 *
 * @author ranxi
 * @date 2025-04-18 11:45
 */
@ToString
@JsonIgnoreProperties({"pages", "records", "size", "current", "orders"})
public class Page<T> extends Pageable<T> implements IPage<T> {

    private Page() {
    }

    public Page(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public static <T> Page<T> from(PageInfo pageInfo) {
        if (pageInfo == null) {
            pageInfo = new PageInfo();
        }
        Page<T> page = new Page<>();
        page.setPageNum(pageInfo.getPageNum());
        page.setPageSize(pageInfo.getPageSize());
        return page;
    }


    /**
     * 计算总页数
     */
    private void computeTotalPage() {
        if (totalNum != 0) {
            this.totalPage = (totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1);
        }
    }

    /**
     * implements IPage
     *****************************************/
    @Override
    public List<T> getRecords() {
        return getData();
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        setData(records);
        return this;
    }

    @Override
    public long getTotal() {
        return this.totalNum;
    }

    @Override
    public IPage<T> setTotal(long total) {
        this.totalNum = (int) total;
        computeTotalPage();
        return this;
    }

    @Override
    public long getSize() {
        return getPageSize();
    }

    @Override
    public IPage<T> setSize(long size) {
        setPageSize((int) size);
        return this;
    }

    @Override
    public long getCurrent() {
        return getPageNum();
    }

    @Override
    public IPage<T> setCurrent(long current) {
        setPageNum((int) current);
        return this;
    }

    /**
     * 排序字段信息
     */
    protected List<OrderItem> orders = new ArrayList<>();

    @Override
    public List<OrderItem> orders() {
        return orders;
    }
}
