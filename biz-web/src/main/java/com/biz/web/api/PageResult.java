package com.biz.web.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 请求返回对象
 *
 * @author francis
 * @since 1.0.1
 **/
@Setter
@Getter
@ToString
public class PageResult<T> {

    /**
     * 总共多少页
     */
    private Long total;

    /**
     * 总共多少条数据
     */
    private Long count;

    /**
     * 返回数据
     */
    private List<T> list;

}
