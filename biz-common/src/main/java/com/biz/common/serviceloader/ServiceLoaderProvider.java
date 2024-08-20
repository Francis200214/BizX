package com.biz.common.serviceloader;

/**
 * 服务加载器提供者接口。
 * 该接口定义了一个方法，用于根据类类型动态获取服务实例。
 * 使用泛型来实现类型安全的服务获取，增强了代码的可读性和可维护性。
 *
 * <p>通过实现该接口，可以为不同的服务提供动态加载机制，
 * 这在模块化和插件化系统中尤为有用。</p>
 *
 * @author francis
 * @since 1.0.1
 * @version 1.0.1
 */
public interface ServiceLoaderProvider {

    /**
     * 根据指定的类类型获取服务实例。
     * <p>
     * 该方法允许动态地加载和返回指定类型的服务实现。这为系统提供了灵活性，
     * 可以在运行时根据需要加载不同的服务实现。
     *
     * @param <T> 服务的泛型类型，允许方法返回任何类型的对象。
     * @param tClass 指定的服务类类型，用于获取相应服务的实例。
     * @return 返回指定类型的服务实例。如果无法找到对应的服务实现，则可能返回null。
     */
    <T> T get(Class<?> tClass);

}
