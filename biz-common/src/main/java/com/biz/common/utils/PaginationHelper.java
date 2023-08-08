package com.biz.common.utils;

import java.util.List;

/**
 * 分页
 *
 * @author francis
 * @create: 2023-08-08 09:53
 **/
public final class PaginationHelper<T> {

    /**
     * 数据
     */
    private final List<T> dataList;
    /**
     * 第几页
     */
    private final int pageSize;
    /**
     * 每页多少条
     */
    private final int pageNumber;

    /**
     * 数据总数量
     */
    private final int size;

    /**
     * @param pageNumber 第几页
     * @param pageSize 每页多少条
     * @param dataList 数据
     */
    public PaginationHelper(int pageNumber, int pageSize, List<T> dataList) {
        this.dataList = dataList;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;

        this.size = dataList.size();
    }

    /**
     * 获取分页后的数据
     *
     * @return
     */
    public List<T> getPageData() {
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, dataList.size());

        return dataList.subList(startIndex, endIndex);
    }

    /**
     * 获取总页数
     * @return
     */
    public int getTotalPageCount() {
        int totalCount = dataList.size();
        return (totalCount + pageSize - 1) / pageSize;
    }

    /**
     * 获取数据总数量
     *
     * @return
     */
    public int getDataListSize() {
        return size;
    }



}
