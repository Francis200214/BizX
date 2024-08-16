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
public class AppPageResult<T> {

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;

    /**
     * 返回数据
     */
    private List<T> list;

}
