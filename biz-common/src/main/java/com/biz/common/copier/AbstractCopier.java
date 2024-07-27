package com.biz.common.copier;

import com.biz.common.reflection.ReflectionUtils;
import com.biz.common.utils.Common;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 抽象复制类，提供对象复制的通用实现。
 * 该类为抽象类，旨在被继承，以实现特定对象的复制逻辑。
 * <p>
 * 使用泛型P和T，分别表示源对象类型和目标对象类型。
 *
 * @param <P> 源对象类型
 * @param <T> 目标对象类型
 * @author francis
 */
@Slf4j
public abstract class AbstractCopier<P, T> implements Copier<P, T> {

    // 用于缓存构造函数，提高后续复制操作的效率
    private final Class<?>[] aClass = new Class<?>[2];
    private Constructor<T> constructor;

    /**
     * 复制源对象P到新创建的目标对象T。
     * 如果未提前指定构造函数，此方法将尝试动态获取目标对象的无参构造函数。
     *
     * @param p 源对象
     * @return 复制后的新目标对象
     * @throws CopyException 如果复制过程中发生错误，包括无法创建目标对象实例
     */
    @Override
    public T copy(P p) {
        try {
            if (constructor == null) {
                constructor = getConstructor();
            }
            return copy(p, constructor.newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error("Error during object copy", e);
            throw new CopyException("Failed to create instance of " + getClass(CopyClassIndex.T).getName(), e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用已有的目标对象T进行复制。
     * 此方法主要提供给子类实现具体的复制逻辑。
     *
     * @param p 源对象
     * @param t 目标对象
     * @return 复制后的目标对象
     */
    @Override
    public T copy(P p, T t) {
        // 子类实现复制逻辑
        return t;
    }

    /**
     * 获取目标对象的构造函数。
     * 该方法用于动态反射获取T类型的无参构造函数，以便实例化新的目标对象。
     *
     * @return 目标对象的构造函数
     * @throws NoSuchMethodException 如果无法找到合适的构造函数
     */
    private Constructor<T> getConstructor() throws NoSuchMethodException {
        Class<T> clazz = Common.to(getClass(CopyClassIndex.T));
        return clazz.getDeclaredConstructor();
    }

    /**
     * 根据索引获取泛型类。
     * 该方法通过反射机制解析泛型参数类型，为复制操作提供必要的类型信息。
     *
     * @param index 索引值，用于区分P和T
     * @return 泛型类
     */
    private Class<?> getClass(CopyClassIndex index) {
        if (aClass[index.index] == null) {
            Class<?> clz = ReflectionUtils.getSuperClassGenericParameterizedTypeWhichOnes(getClass(), 1);
            if (clz == null) {
                throw new RuntimeException("unknown copier T class");
            }
            aClass[index.index] = clz;
        }
        return aClass[index.index];
    }

    /**
     * 复制操作中的异常类。
     * 用于封装复制过程中可能出现的异常情况。
     */
    public static class CopyException extends RuntimeException {
        public CopyException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * 枚举类型，用于标识P和T在数组中的索引。
     * 由于Java泛型擦除机制，需要通过这种方式来间接获取泛型类的信息。
     */
    private enum CopyClassIndex {
        P(0),
        T(1);

        private int index;

        CopyClassIndex(int i) {
            index = i;
        }

        /**
         * 获取索引值。
         *
         * @return 索引值
         */
        int getCopyClassIndex() {
            return index;
        }
    }
}
