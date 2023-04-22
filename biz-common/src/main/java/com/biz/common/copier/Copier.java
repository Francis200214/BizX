package com.biz.common.copier;


public interface Copier<P, T> {

    /**
     * @param p
     * @return
     */
    T copy(P p);

    T copy(P p, T t);

}
