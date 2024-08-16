package com.biz.common.jts;

import org.locationtech.jts.geom.GeometryFactory;

/**
 * JTS 格式数据工具类。
 * <p>该类提供了一个公共的 {@link GeometryFactory} 实例，供其他几何操作工具类使用。</p>
 *
 * <p>通过此类，您可以方便地获取一个 {@link GeometryFactory} 实例，用于创建各种几何对象。</p>
 *
 * <p>该类是不可实例化的，因为它只包含静态字段。</p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 * GeometryFactory factory = JTSUtils.GEOMETRY_FACTORY;
 * Point point = factory.createPoint(new Coordinate(1.0, 2.0));
 * }</pre>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
public final class JTSUtils {

    /**
     * {@link GeometryFactory} 实例，用于创建几何对象。
     */
    public static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

    // 私有构造函数，防止实例化
    private JTSUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
