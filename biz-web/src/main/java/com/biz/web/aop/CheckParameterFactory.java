package com.biz.web.aop;

import com.biz.library.bean.BizXComponent;
import com.biz.web.aop.handler.CheckParameterStrategy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * 检查参数工厂
 *
 * 主要做注册 Bean 后将实现 CheckParameterStrategy 接口的实现类加入到 Map 中，
 * 方便后续使用 CheckParameterStrategy 下各个实现类
 *
 * @author francis
 * @create: 2023-04-08 17:05
 **/
@BizXComponent
public class CheckParameterFactory implements InitializingBean, ApplicationContextAware, CheckParameterService {

    private static final Map<Class<?>, CheckParameterStrategy> CHECK_PARAMETER_STRATEGY_MAP = new HashMap<>();

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
        // 将 Spring 容器中所有的 CheckParameterStrategy 注册到 LOGIN_HANDLER_MAP
        for (CheckParameterStrategy handler : applicationContext.getBeansOfType(CheckParameterStrategy.class).values()) {
            CHECK_PARAMETER_STRATEGY_MAP.put(handler.getCheckAnnotation(), handler);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void handle(Parameter parameter, Object[] args) {

    }
}
