package com.biz.common.copier;

/**
 * 一个实现了Copier接口的类，提供了基于反射的默认实现。
 * 注意：由于Java的类型擦除，运行时需要确保P和T是兼容的。
 *
 * @param <P> 源对象类型
 * @param <T> 目标对象类型
 */
public interface Copier<P, T> {

    /**
     * 使用反射复制对象。
     *
     * @param p 源对象
     * @return 复制后的新对象
     */
    T copy(P p);

    /**
     * 使用反射复制对象到提供的目标对象中。
     *
     * @param p 源对象
     * @param t 目标对象
     * @return 复制后的目标对象
     */
    T copy(P p, T t);

}
