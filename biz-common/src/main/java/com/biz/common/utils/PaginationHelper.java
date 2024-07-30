package com.biz.common.utils;

import java.util.List;

/**
 * 分页辅助类，用于提供分页功能的支持。
 * 该类是线程安全的，但要求dataList在创建PaginationHelper实例后不应被修改。
 *
 * @param <T> 泛型参数，表示分页数据的类型。
 * @author francis
 * @create 2023-08-08 09:53
 **/
public final class PaginationHelper<T> {

    /**
     * 分页数据列表。
     */
    private final List<T> dataList;
    /**
     * 每页的记录数。
     */
    private final int pageSize;
    /**
     * 当前页码。
     */
    private final int pageNumber;
    /**
     * 数据总记录数。
     */
    private final int size;

    /**
     * 构造函数，初始化分页辅助对象。
     *
     * @param pageNumber 当前页码，必须大于0。
     * @param pageSize   每页的记录数，必须大于0。
     * @param dataList   分页数据列表，不应在创建PaginationHelper实例后被修改。
     * @throws IllegalArgumentException 如果pageNumber或pageSize不满足要求。
     */
    public PaginationHelper(int pageNumber, int pageSize, List<T> dataList) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("pageNumber must be greater than 0");
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize must be greater than 0");
        }

        this.dataList = dataList;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;

        this.size = dataList.size();
    }

    /**
     * 获取当前页的数据列表。
     *
     * @return 当前页的数据列表。
     */
    public List<T> getPageData() {
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, dataList.size());

        return dataList.subList(startIndex, endIndex);
    }

    /**
     * 计算总页数。
     *
     * @return 总页数。
     */
    public int getTotalPageCount() {
        // 优化总页数的计算，避免对size进行额外的加法和除法运算
        return (size + pageSize - 1) / pageSize;
    }

    /**
     * 获取数据总记录数。
     *
     * @return 数据总记录数。
     */
    public int getDataListSize() {
        return size;
    }
}
