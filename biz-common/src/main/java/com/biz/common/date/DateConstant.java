package com.biz.common.date;

/**
 * 时间常量类，定义了常用的日期和时间格式字符串。
 * <p>该类是一个工具类，包含了一些常用的日期和时间格式的常量，这些常量可以在日期和时间操作中被广泛使用。</p>
 *
 * <h2>示例代码：</h2>
 * <pre>{@code
 *     String dateFormat = DateConstant.DEFAULT_DATE;
 *     DateFormat df = new SimpleDateFormat(dateFormat);
 *     String formattedDate = df.format(new Date());
 * }</pre>
 *
 * <p>该类是不可实例化的，因为它只包含静态常量。</p>
 *
 * <p>注意：这些常量主要用于日期和时间格式化时使用的模式字符串。</p>
 *
 * @author francis
 * @version 1.0.1
 * @since 1.0.1
 */
public final class DateConstant {

    /**
     * 默认日期格式，格式为 "yyyy-MM-dd"。
     */
    public static final String DEFAULT_DATE = "yyyy-MM-dd";

    /**
     * 默认时间格式，格式为 "HH:mm:ss"。
     */
    public static final String DEFAULT_TIME = "HH:mm:ss";

    /**
     * 默认日期时间格式，格式为 "yyyy-MM-dd HH:mm:ss"。
     */
    public static final String DEFAULT_DATETIME = DEFAULT_DATE + " " + DEFAULT_TIME;

    /**
     * 默认年份格式，格式为 "yyyy"。
     */
    public static final String DEFAULT_YEAR = "yyyy";

    /**
     * 默认月份格式，格式为 "MM"。
     */
    public static final String DEFAULT_MM = "MM";

    /**
     * 默认无分隔符的日期格式，格式为 "yyyyMMdd"。
     */
    public static final String DEFAULT_YYYY_MM_DD = "yyyyMMdd";

    // 私有构造函数，防止实例化
    private DateConstant() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
