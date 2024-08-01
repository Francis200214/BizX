package com.biz.common.reflection;

import com.biz.common.reflection.model.ConstructorMethodModel;
import com.biz.common.reflection.model.FieldModel;
import com.biz.common.reflection.model.MethodModel;
import com.biz.common.reflection.model.ParameterTypeModel;
import com.biz.common.utils.Common;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * 提供关于类、字段、方法等的反射工具方法。
 *
 * @author francis
 * @since 2023/4/2 9:47
 */
public class ReflectionUtils {

    /**
     * 判断给定类是否为原始类型。
     *
     * @param clazz 待检查的类
     * @return 如果是原始类型返回true，否则返回false
     */
    public static boolean isPackagingType(Class<?> clazz) {
        return clazz.isPrimitive();
    }

    /**
     * 判断给定类是否为String类型。
     *
     * @param clazz 待检查的类
     * @return 如果是String类型返回true，否则返回false
     */
    public static boolean isStringClass(Class<?> clazz) {
        return clazz == String.class;
    }

    /**
     * 获取类的包名。
     *
     * @param clazz 待检查的类
     * @return 类的包名，如果类没有包则返回null
     */
    public static String getPackageName(Class<?> clazz) {
        Package pck = getPackage(clazz);
        if (pck == null) {
            return null;
        }

        return pck.getName();
    }

    /**
     * 获取类的父类名。
     *
     * @param clazz 待检查的类
     * @return 类的父类名，如果类没有父类则返回null
     */
    public static String getSuperClassName(Class<?> clazz) {
        Class<?> superClass = clazz.getSuperclass();
        return superClass.getName();
    }

    /**
     * 获取类的全名。
     *
     * @param clazz 待检查的类
     * @return 类的全名
     */
    public static String getClassName(Class<?> clazz) {
        return clazz.getName();
    }

    /**
     * 获取类实现的所有接口名。
     *
     * @param clazz 待检查的类
     * @return 类实现的所有接口名的集合
     */
    public static Set<String> getInterfacesName(Class<?> clazz) {
        Set<String> set = new HashSet<>();
        for (Class<?> anInterface : clazz.getInterfaces()) {
            set.add(anInterface.getSimpleName());
        }
        return set;
    }

    /**
     * 获取类实现的所有接口。
     *
     * @param clazz 待检查的类
     * @return 类实现的所有接口的集合
     */
    public static Set<Class<?>> getInterfaces(Class<?> clazz) {
        try {
            return new HashSet<>(Arrays.asList(clazz.getInterfaces()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve interfaces for class: " + clazz.getName(), e);
        }
    }

    /**
     * 从类列表中找出实现指定接口的类。
     *
     * @param clazz     指定的接口
     * @param classList 类列表
     * @return 实现指定接口的类的列表
     */
    public static List<Class<?>> getImplementsClass(Class<?> clazz, List<Class<?>> classList) {
        List<Class<?>> result = new ArrayList<>();
        for (Class<?> aClass : classList) {
            if (clazz.isAssignableFrom(aClass)) {
                result.add(aClass);
            }
        }
        return result;
    }

    /**
     * 判断一个类是否实现或继承了另一个类。
     *
     * @param aClass 目标类
     * @param bClass 指定类
     * @return 如果目标类实现或继承了指定类返回true，否则返回false
     */
    public static boolean isImplementsClass(Class<?> aClass, Class<?> bClass) {
        return aClass.isAssignableFrom(bClass);
    }

    /**
     * 获取类的所有字段。
     *
     * @param clazz 待检查的类
     * @return 类的所有字段的集合
     */
    public static Set<FieldModel> getFields(Class<?> clazz) {
        return buildFieldModelSet(clazz.getDeclaredFields());
    }

    /**
     * 获取类的所有公共字段。
     *
     * @param clazz 待检查的类
     * @return 类的所有公共字段的集合
     */
    public static Set<FieldModel> getPublicFields(Class<?> clazz) {
        return buildFieldModelSet(clazz.getFields());
    }

    /**
     * 获取类的所有构造方法。
     *
     * @param clazz 待检查的类
     * @return 类的所有构造方法的集合
     */
    public static Set<ConstructorMethodModel> getConstructors(Class<?> clazz) {
        Set<ConstructorMethodModel> set = new HashSet<>();
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            set.add(ConstructorMethodModel.builder()
                    .modifier(Modifier.toString(constructor.getModifiers()))
                    .name(clazz.getSimpleName())
                    .parameterTypeModels(buildParameterTypeModelSet(constructor.getParameterTypes()))
                    .build());
        }

        return set;
    }

    /**
     * 获取类的所有方法。
     *
     * @param clazz 待检查的类
     * @return 类的所有方法的集合
     */
    public static Set<MethodModel> getMethods(Class<?> clazz) {
        return buildMethodModelSet(clazz.getDeclaredMethods());
    }

    /**
     * 获取类的所有公共方法。
     *
     * @param clazz 待检查的类
     * @return 类的所有公共方法的集合
     */
    public static Set<MethodModel> getPublicMethods(Class<?> clazz) {
        return buildMethodModelSet(clazz.getMethods());
    }

    /**
     * 获取类上所有注解的名称。
     *
     * @param clazz 待检查的类
     * @return 类上所有注解名称的集合
     */
    public static Set<String> getAnnotationNames(Class<?> clazz) {
        Set<String> set = new HashSet<>();
        for (Annotation annotation : clazz.getAnnotations()) {
            set.add(annotation.annotationType().getSimpleName());
        }
        return set;
    }

    /**
     * 判断类上是否包含特定注解。
     *
     * @param clazz      待检查的类
     * @param annotation 注解类型
     * @return 如果类上包含指定注解返回true，否则返回false
     */
    public static boolean checkAnnotationInClass(Class<?> clazz, Class<? extends Annotation> annotation) {
        return clazz.isAnnotationPresent(annotation);
    }

    /**
     * 获取类上的指定注解。
     *
     * @param clazz      待检查的类
     * @param annotation 注解类型
     * @param <A>        注解的泛型
     * @return 类上的指定注解实例，如果类上没有该注解则返回null
     */
    public static <A extends Annotation> A getAnnotationInClass(Class<?> clazz, Class<A> annotation) {
        return clazz.getAnnotation(annotation);
    }

    /**
     * 获取类上的所有注解。
     *
     * @param clazz 待检查的类
     * @return 类上的所有注解数组
     */
    public static Annotation[] getClassAnnotations(Class<?> clazz) {
        if (clazz == null) {
            throw new RuntimeException("clazz is null");
        }
        return clazz.getAnnotations();
    }

    /**
     * 获取父类的第一个泛型参数类型。
     *
     * @param clazz 待检查的类
     * @return 父类的第一个泛型参数类型，如果父类没有泛型参数则返回null
     */
    public static Class<?> getSuperClassGenericParameterizedTypeForOne(Class<?> clazz) {
        Type genericSuperClass = clazz.getGenericSuperclass();
        // 判断父类是否有泛型
        if (genericSuperClass instanceof ParameterizedType) {
            ParameterizedType pt = Common.to(genericSuperClass);
            return Common.to(pt.getActualTypeArguments()[0]);
        }
        return null;
    }

    /**
     * 获取父类的第N个泛型参数类型。
     *
     * @param clazz 待检查的类
     * @param n     泛型参数的位置
     * @return 父类的第N个泛型参数类型，如果父类没有足够的泛型参数则返回null
     */
    public static Class<?> getSuperClassGenericParameterizedTypeWhichOnes(Class<?> clazz, int n) {
        Type genericSuperClass = clazz.getGenericSuperclass();
        // 判断父类是否有泛型
        if (genericSuperClass instanceof ParameterizedType) {
            ParameterizedType pt = Common.to(genericSuperClass);
            Type actualTypeArgument = pt.getActualTypeArguments()[n];
            if (actualTypeArgument == null) {
                throw new RuntimeException("not find n TypeArgument");
            }
            return Common.to(actualTypeArgument);
        }
        return null;
    }

    /**
     * 获取接口的所有泛型参数类型。
     *
     * @param clazz 待检查的类
     * @return 接口的所有泛型参数类型的集合
     */
    public static Set<Class<?>> getInterfaceGenericParameterizedTypes(Class<?> clazz) {
        Set<Class<?>> set = new HashSet<>();
        for (Type genericInterface : clazz.getGenericInterfaces()) {
            // 判断接口是否有泛型
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType pt = Common.to(genericInterface);
                // 得到所有的泛型
                for (Type actualTypeArgument : pt.getActualTypeArguments()) {
                    Class<?> interfaceClass = Common.to(actualTypeArgument);
                    set.add(interfaceClass);
                }
            }
        }
        return set;
    }


    /**
     * 根据Class类型，获取对应的实例
     * 通过调用Class的newInstance方法创建并返回一个该类的新实例。
     *
     * @param clazz 待创建实例的Class对象
     * @return 类的新实例
     * @throws InstantiationException 如果类没有无参构造方法或实例化失败
     * @throws IllegalAccessException 如果访问类或构造方法被限制
     */
    public static <T> T getNewInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        return Common.to(clazz.newInstance());
    }


    /**
     * 使用 Class 的无参构造方法创建对象实例
     * 通过获取并调用类的无参构造方法来创建并返回一个该类的新实例。
     *
     * @param clazz 待创建实例的Class对象
     * @return 类的新实例
     * @throws InstantiationException    如果类没有无参构造方法或实例化失败
     * @throws IllegalAccessException    如果访问构造方法被限制
     * @throws NoSuchMethodException     如果类没有定义无参构造方法
     * @throws InvocationTargetException 如果构造方法抛出异常
     */
    public static <T> T getDeclaredConstructorNewInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        return Common.to(clazz.getDeclaredConstructor().newInstance());
    }


    /**
     * 根据传入的类的Class对象，以及构造方法的形参的Class对象，获取对应的构造方法对象
     * 通过指定参数类型获取类的构造方法对象。
     *
     * @param clazz          类的Class对象
     * @param parameterTypes 构造方法的参数类型数组
     * @return 对应参数类型的构造方法对象
     * @throws NoSuchMethodException 如果找不到指定参数类型的构造方法
     * @throws SecurityException     如果访问构造方法被限制
     */
    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
        return clazz.getDeclaredConstructor(parameterTypes);
    }


    /**
     * 根据传入的构造方法对象，以及传入构造方法的实参，获取对应的实例
     * 通过调用构造方法并传入参数来创建并返回类的实例。
     *
     * @param constructor 构造方法对象
     * @param initrans    传入构造方法的实参数组
     * @return 类的实例
     * @throws InstantiationException    如果实例化失败
     * @throws IllegalAccessException    如果访问构造方法被限制
     * @throws IllegalArgumentException  如果参数不合法
     * @throws InvocationTargetException 如果构造方法抛出异常
     */
    public static <T> T getNewInstance(Constructor<?> constructor, Object... initrans)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (constructor == null) {
            throw new IllegalArgumentException("Constructor cannot be null");
        }
        // 不检查java权限控制
        constructor.setAccessible(true);
        return Common.to(constructor.newInstance(initrans));
    }

    /**
     * 根据传入的属性名字符串，修改对应的属性值
     * 通过反射机制获取类的指定字段并设置其值。
     *
     * @param clazz 类的Class对象
     * @param name  属性名
     * @param obj   要修改属性值的实例对象
     * @param value 新的属性值
     * @throws NoSuchFieldException     如果找不到指定字段
     * @throws SecurityException        如果访问字段被限制
     * @throws IllegalArgumentException 如果参数不合法
     * @throws IllegalAccessException   如果访问字段值被限制
     */
    public static void setField(Class<?> clazz, String name, Object obj, Object value)
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        Field field = clazz.getDeclaredField(name);
        // 不检查java权限控制
        field.setAccessible(true);
        field.set(obj, value);
    }


    /**
     * 取属性字段中的值
     * 通过反射机制获取类的指定字段的值。
     *
     * @param field 属性字段对象
     * @param value 属性字段所属的实例对象
     * @return 属性字段的值
     * @throws IllegalAccessException 如果访问字段值被限制
     */
    public static Object getByFieldValue(Field field, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(value);
    }


    /**
     * 根据传入的方法名字符串，获取对应的方法
     * 通过指定方法名和参数类型数组获取类的Method对象。
     *
     * @param clazz          要操作的类的Class对象
     * @param name           方法名
     * @param parameterTypes 方法的参数类型数组
     * @return 对应方法名和参数类型的Method对象
     * @throws NoSuchMethodException 如果找不到指定方法
     * @throws SecurityException     如果访问方法被限制
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes)
            throws NoSuchMethodException, SecurityException {
        return clazz.getDeclaredMethod(name, parameterTypes);
    }

    /**
     * 根据传入的方法对象，调用对应的方法
     * 通过传入Method对象和参数数组来调用相应的方法。
     *
     * @param method 方法对象
     * @param obj    要调用方法的实例对象（如果方法为静态方法，此参数可为null）
     * @param args   方法的参数数组
     * @return 方法的返回值（如果方法无返回值，则返回null）
     * @throws IllegalAccessException    如果访问方法被限制
     * @throws IllegalArgumentException  如果参数不合法
     * @throws InvocationTargetException 如果方法抛出异常
     */
    public static Object invokeMethod(Method method, Object obj, Object... args)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // 不检查java权限控制
        method.setAccessible(true);
        return method.invoke(obj, args);
    }


    /**
     * 获取 Class 的包
     * 通过Class对象获取对应的Package对象。
     *
     * @param clazz Class对象
     * @return Package对象
     */
    private static Package getPackage(Class<?> clazz) {
        return clazz.getPackage();
    }


    /**
     * 构建方法参数实体对象模型
     * 根据参数类型数组构建一个方法参数实体对象模型的集合。
     *
     * @param parameterTypes 参数类型数组
     * @return 方法参数实体对象模型的集合
     */
    private static Set<ParameterTypeModel> buildParameterTypeModelSet(Class<?>[] parameterTypes) {
        Set<ParameterTypeModel> set = new HashSet<>();
        for (Class<?> parameterType : parameterTypes) {
            set.add(ParameterTypeModel.builder()
                    .name(parameterType.getSimpleName())
                    .build());
        }
        return set;
    }


    /**
     * 根据类的字段信息构建字段模型集合。
     * 字段模型包含了字段的修饰符、类型、名称、注解等信息。
     *
     * @param fields 类的字段数组
     * @return 字段模型的集合
     */
    private static Set<FieldModel> buildFieldModelSet(Field[] fields) {
        Set<FieldModel> set = new HashSet<>();
        for (Field field : fields) {
            // 不检查java权限控制
            field.setAccessible(true);
            set.add(FieldModel.builder()
                    .modifier(Modifier.toString(field.getModifiers()))
                    .typeName(field.getType().getSimpleName())
                    .name(field.getName())
                    .annotations(field.getAnnotations())
                    .field(field)
                    .build());
        }
        return set;
    }


    /**
     * 根据类的方法信息构建方法模型集合。
     * 方法模型包含了方法的修饰符、名称、返回类型、参数类型等信息。
     *
     * @param methods 类的方法数组
     * @return 方法模型的集合
     */
    private static Set<MethodModel> buildMethodModelSet(Method[] methods) {
        Set<MethodModel> set = new HashSet<>();
        for (Method method : methods) {
            set.add(MethodModel.builder()
                    .modifier(Modifier.toString(method.getModifiers()))
                    .name(method.getName())
                    .returnType(method.getReturnType().getSimpleName())
                    .parameterTypeModels(buildParameterTypeModelSet(method.getParameterTypes()))
                    .build());
        }
        return set;
    }

}
