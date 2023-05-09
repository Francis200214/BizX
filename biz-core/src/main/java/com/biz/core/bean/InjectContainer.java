package com.biz.core.bean;

import com.biz.common.bean.BizXBeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;

import javax.inject.Inject;
import java.lang.reflect.Field;

/**
 * 实现@Inject注解
 *
 * @author francis
 * @create: 2023-04-08 21:53
 **/
@Order(0)
public class InjectContainer implements InitializingBean {


    @Override
    public void afterPropertiesSet() {
//        for (String beanDefinitionName : BizXBeanUtils.getBeanDefinitionNames()) {
//            String bean = BizXBeanUtils.getBean(beanDefinitionName.getClass());
//            injectDependencies(bean);
//        }
    }


    private void injectDependencies(Object obj) {
        // 获取对象的类
        Class<?> clazz = obj.getClass();
        // 获取类中所有的字段
        Field[] fields = clazz.getDeclaredFields();
        // 遍历所有字段
        for (Field field : fields) {
            // 检查字段是否被 @Inject 注解标记
            if (field.isAnnotationPresent(Inject.class)) {
                // 获取字段的类型
                Class<?> fieldType = field.getType();
                // 从容器中获取依赖对象
                Object dependency = BizXBeanUtils.getBean(fieldType);
                // 设置字段可访问
                field.setAccessible(true);
                try {
                    // 为字段注入依赖
                    field.set(obj, dependency);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
