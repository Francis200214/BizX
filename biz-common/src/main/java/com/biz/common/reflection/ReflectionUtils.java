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
 * 反射工具类
 * <br/>
 * <p>
 * 封装反射的方法
 *
 * @author francis
 * @create 2023/4/2 9:47
 */
public class ReflectionUtils {

    /**
     * 判断是否是包装类型
     *
     * @param clazz Class
     * @return true 是包装类 false 不是包装类
     */
    public static boolean isPackagingType(Class<?> clazz) {
        return clazz.isPrimitive();
    }

    /**
     * 是否是字符串类型
     *
     * @param clazz Class
     * @return true 是字符串类型 false 不是字符串类型
     */
    public static boolean isStringClass(Class<?> clazz) {
        return clazz == String.class;
    }

    /**
     * 获取 Class 的包名
     *
     * @param clazz Class
     * @return 包名
     */
    public static String getPackageName(Class<?> clazz) {
        Package pck = getPackage(clazz);
        if (pck == null) {
            return null;
        }

        return pck.getName();
    }


    /**
     * 获取父类的全类名
     *
     * @param clazz Class
     * @return 父类的全类名
     */
    public static String getSuperClassName(Class<?> clazz) {
        Class<?> superClass = clazz.getSuperclass();
        return superClass.getName();
    }


    /**
     * 获取全类名
     *
     * @param clazz Class
     * @return 全类名
     */
    public static String getClassName(Class<?> clazz) {
        return clazz.getName();
    }


    /**
     * 获取实现的接口名
     *
     * @param clazz Class
     * @return 所有的接口名
     */
    public static Set<String> getInterfacesName(Class<?> clazz) {
        Set<String> set = new HashSet<>();
        for (Class<?> anInterface : clazz.getInterfaces()) {
            set.add(anInterface.getSimpleName());
        }
        return set;
    }


    /**
     * 获取所有实现的接口
     *
     * @param clazz Class
     * @return
     */
    public static Set<Class<?>> getInterfaces(Class<?> clazz) {
        return new HashSet<>(Arrays.asList(clazz.getInterfaces()));
    }

    /**
     * 从一个List<Class> 中寻找实现了 clazz 接口的 class
     *
     * @param clazz
     * @param classList
     * @return
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
     * 判断 aClass 是否实现了 bClass
     *
     * @param aClass aClass
     * @param bClass bClass
     * @return
     */
    public static boolean isImplementsClass(Class<?> aClass, Class<?> bClass) {
        return aClass.isAssignableFrom(bClass);
    }

    /**
     * 获取所有属性
     *
     * @param clazz Class
     * @return 所有的属性
     */
    public static Set<FieldModel> getFields(Class<?> clazz) {
        return buildFieldModelSet(clazz.getDeclaredFields());
    }


    /**
     * 获取所有公共的属性
     *
     * @param clazz Class
     * @return 所有公共的属性
     */
    public static Set<FieldModel> getPublicFields(Class<?> clazz) {
        return buildFieldModelSet(clazz.getFields());
    }


    /**
     * 获取所有构造方法
     *
     * @param clazz Class
     * @return 所有的构造方法
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
     * 获取所有自身的方法
     *
     * @param clazz Class
     * @return 所有自身的方法
     */
    public static Set<MethodModel> getMethods(Class<?> clazz) {
        return buildMethodModelSet(clazz.getDeclaredMethods());
    }


    /**
     * 获取所有公共的方法
     *
     * @param clazz Class
     * @return 所有公共的方法
     */
    public static Set<MethodModel> getPublicMethods(Class<?> clazz) {
        return buildMethodModelSet(clazz.getMethods());
    }


    /**
     * 获取所有的注解名
     *
     * @return 所有的注解名
     */
    public static Set<String> getAnnotationNames(Class<?> clazz) {
        Set<String> set = new HashSet<>();
        for (Annotation annotation : clazz.getAnnotations()) {
            set.add(annotation.annotationType().getSimpleName());
        }
        return set;
    }

    /**
     * 获取父类的泛型
     *
     * @param clazz Class
     * @return 父类的泛型
     */
    public static Class<?> getSuperClassGenericParameterizedType(Class<?> clazz) {
        Type genericSuperClass = clazz.getGenericSuperclass();
        // 判断父类是否有泛型
        if (genericSuperClass instanceof ParameterizedType) {
            ParameterizedType pt = Common.to(genericSuperClass);
            return Common.to(pt.getActualTypeArguments()[0]);
        }
        return null;
    }


    /**
     * 获取接口的所有泛型
     *
     * @param clazz Class
     * @return 所有的泛型接口
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
     *
     * @return 对应的实例
     */
    public static <T> T getNewInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        return (T) clazz.newInstance();
    }


    /**
     * 根据传入的类的Class对象，以及构造方法的形参的Class对象，获取对应的构造方法对象
     *
     * @param clazz          类的Class对象
     * @param parameterTypes 构造方法的形参的Class对象
     * @return 构造方法对象
     */
    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
        return clazz.getDeclaredConstructor(parameterTypes);
    }


    /**
     * 根据传入的构造方法对象，以及，获取对应的实例
     *
     * @param constructor 构造方法对象
     * @param initrans    传入构造方法的实参
     * @return 对应的实例
     */
    public static <T> T getNewInstance(Constructor<?> constructor, Object... initrans)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // 不检查java权限控制
        constructor.setAccessible(true);
        return (T) constructor.newInstance(initrans);
    }


    /**
     * 根据传入的属性名字符串，修改对应的属性值
     *
     * @param clazz 类的Class对象
     * @param name  属性名
     * @param obj   要修改的实例对象
     * @param value 修改后的新值
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
     *
     * @param field 属性字段
     * @param value 属性字段中的值
     * @return
     */
    public static Object getByFieldValue(Field field, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(value);
    }


    /**
     * 根据传入的方法名字符串，获取对应的方法
     *
     * @param clazz          要操作的类的Class对象
     * @param name           要寻找哪个方法名
     * @param parameterTypes 方法的形参对应的Class类型【可以不写】
     * @return 方法对象【Method类型】
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes)
            throws NoSuchMethodException, SecurityException {
        return clazz.getDeclaredMethod(name, parameterTypes);
    }


    /**
     * 根据传入的方法对象，调用对应的方法
     *
     * @param method 方法对象
     * @param obj    要调用的实例对象【如果是调用静态方法，则可以传入null】
     * @param args   传入方法的实参【可以不写】
     * @return 方法的返回值【没有返回值，则返回null】
     */
    public static Object invokeMethod(Method method, Object obj, Object... args)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // 不检查java权限控制
        method.setAccessible(true);
        return method.invoke(obj, args);
    }


    /**
     * 获取 Class 的包
     *
     * @param clazz Class
     * @return
     */
    private static Package getPackage(Class<?> clazz) {
        return clazz.getPackage();
    }


    /**
     * 构建方法参数实体对象模型
     *
     * @param parameterTypes
     * @return
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
     * 构建属性
     *
     * @param fields 属性数组
     * @return
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
     * 构建方法实体模型
     *
     * @param methods 方法
     * @return
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
