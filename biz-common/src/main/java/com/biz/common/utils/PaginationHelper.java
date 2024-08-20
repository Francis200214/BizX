package com.biz.common.utils;

import java.util.List;

/**
 * 分页辅助类，用于提供分页功能的支持。
 * 该类是线程安全的，但要求`dataList`在创建`PaginationHelper`实例后不应被修改。
 * <p>
 * 通过该类，开发者可以方便地实现对数据列表的分页操作，获取当前页的数据，以及计算总页数等功能。
 * </p>
 *
 * <pre>{@code
 * // 示例用法
 * List<String> data = Arrays.asList("A", "B", "C", "D", "E", "F");
 * PaginationHelper<String> paginationHelper = new PaginationHelper<>(2, 2, data);
 * List<String> pageData = paginationHelper.getPageData(); // 返回 ["C", "D"]
 * int totalPages = paginationHelper.getTotalPageCount(); // 返回 3
 * int totalItems = paginationHelper.getDataListSize(); // 返回 6
 * }</pre>
 *
 * @param <T> 泛型参数，表示分页数据的类型。
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
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
     * @param dataList   分页数据列表，不应在创建`PaginationHelper`实例后被修改。
     * @throws IllegalArgumentException 如果`pageNumber`或`pageSize`不满足要求。
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
