package com.biz.common.copier;

/**
 * 一个实现了 {@code Copier} 接口的类，提供了基于反射的默认实现。
 * <p>注意：由于 Java 的类型擦除，运行时需要确保 {@code P} 和 {@code T} 是兼容的。</p>
 *
 * <p>该接口定义了两个方法，用于在对象之间进行深度复制。第一个方法用于创建并返回一个新对象，第二个方法用于将源对象的内容复制到提供的目标对象中。</p>
 *
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 *  public class Test {
 *     private final Copier<MyClassA, MyClassB> myClassAToMyClassBCopier = new AbstractCopier<MyClassA, MyClassB>() {
 *         @Override
 *         public BVo copy(MyClassA a, MyClassB b) {
 *             ...
 *             return b;
 *         }
 *     };
 *
 *     public void test() {
 *         MyClassA sourceObject = ...;
 *         MyClassB targetObject = ...;
 *         myClassAToMyClassBCopier.copy(sourceObject, targetObject);
 *     }
 *  }
 * }
 * </pre>
 *
 * @param <P> 源对象类型
 * @param <T> 目标对象类型
 * @author francis
 * @version 1.0.1
 * @see java.lang.reflect.Field
 * @see java.lang.reflect.Method
 * @since 1.0.1
 */
public interface Copier<P, T> {

    /**
     * 使用反射复制对象。
     * <p>该方法将源对象 {@code p} 的内容复制到一个新创建的目标对象中并返回。</p>
     *
     * @param p 源对象，不能为空
     * @return 复制后的新对象
     * @see java.lang.reflect.Field
     * @see java.lang.reflect.Method
     */
    T copy(P p);

    /**
     * 使用反射复制对象到提供的目标对象中。
     * <p>该方法将源对象 {@code p} 的内容复制到提供的目标对象 {@code t} 中，并返回该目标对象。</p>
     *
     * @param p 源对象，不能为空
     * @param t 目标对象，不能为空
     * @return 复制后的目标对象
     * @see java.lang.reflect.Field
     * @see java.lang.reflect.Method
     */
    T copy(P p, T t);
}
