package com.biz.verification.condition;

import com.biz.common.utils.Common;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 包路径检查条件。
 * <p>用于检查是否配置了自定义校验包路径，并判断类名是否在配置的包路径下。</p>
 *
 * <pre>
 * 示例配置：
 * biz.verification.scan-packages=com.controller,com.service
 * </pre>
 *
 * <p>使用 {@link Value} 注解从配置文件中读取包路径，并在 {@link PostConstruct} 方法中初始化包路径列表。</p>
 *
 * @author francis
 * @since 1.0.1
 **/
public class CheckScanPackageCondition {

    @Value("${biz.verification.scan-packages:}")
    private String scanPackages;

    /**
     * 包路径列表，用于存储配置的包路径。
     */
    private static List<String> packageList;

    /**
     * 初始化方法，在 Bean 初始化后调用，解析配置的包路径并存储在列表中。
     */
    @PostConstruct
    private void init() {
        if (Common.isBlank(scanPackages)) {
            packageList = new ArrayList<>();
        } else {
            packageList = Arrays.asList(scanPackages.split(","));
        }
    }

    /**
     * 是否配置了自定义校验包路径。
     *
     * @return 如果配置了自定义校验包路径返回 true；否则返回 false
     */
    public static boolean hasText() {
        return !Common.isEmpty(packageList);
    }

    /**
     * 判断类名是否在配置的包路径下。
     *
     * @param className 类名，不能为空
     * @return 如果类名在配置的包路径下，返回 true；否则返回 false
     */
    public static boolean classNameInPackage(String className) {
        if (Common.isEmpty(packageList)) {
            return false;
        }
        for (String scanPackage : packageList) {
            if (className.startsWith(scanPackage.trim())) {
                return true;
            }
        }
        return false;
    }
}
