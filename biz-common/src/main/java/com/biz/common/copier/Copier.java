package com.biz.common.copier;

/**
 * 两个对象转换
 *
 * @param <P>
 * @param <T>
 */
public interface Copier<P, T> {

    /**
     * @param p
     * @return
     */
    T copy(P p);

    T copy(P p, T t);

}
