package com.demo.test;

import com.biz.common.reflection.ReflectionUtils;
import com.biz.common.strategy.StrategyService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author francis
 * @create: 2023-04-16 20:27
 **/
public class StrategyTest {


    public static void main(String[] args) {

        List<Class<?>> classList = getClassList();
        List<Class<?>> implementsClass = ReflectionUtils.getImplementsClass(StrategyService.class, classList);
        Map<Type, List<Class<?>>> result = new HashMap<>();

        for (Class<?> aClass : implementsClass) {
            for (Class<?> anInterface : ReflectionUtils.getInterfaces(aClass)) {
                if (ReflectionUtils.isImplementsClass(StrategyService.class, anInterface)) {
                    result.computeIfAbsent(anInterface, k -> new ArrayList<>()).add(aClass);
                }
            }
        }

        result.forEach((k, values) -> {
            System.out.println(k);
            for (Class<?> value : values) {
                System.out.println(value.getName());
            }
        });

    }


    private static List<Class<?>> getClassList() {
        List<Class<?>> results = new ArrayList<>();
//        results.add(ManImpl.class);
//        results.add(WomanImpl.class);
//        results.add(AAA.class);
//        results.add(BBB.class);
//        results.add(CCC.class);
        return results;
    }


}
