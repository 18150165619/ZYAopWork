package com.zhuyan.aop.util;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zhuyan.aop.annotation.TestAfter;
import com.zhuyan.aop.annotation.TestAround;
import com.zhuyan.aop.annotation.TestAspect;
import com.zhuyan.aop.annotation.TestAspectHandler;
import com.zhuyan.aop.annotation.TestBefore;
import com.zhuyan.aop.annotation.TestComponent;
import com.zhuyan.aop.annotation.ZYAspectAroundMethod;
import com.zhuyan.aop.annotation.ZYAspectNormalMethod;
/**
 * Created by Ryan
 * On 2017/10/5.
 */
public class ZYInitFactory {
    private static final HashMap<Class<?>, Object> classBeanMap = new HashMap<>();
    private static final HashMap<String, Object> beanNameMap = new HashMap<>();
    private static final HashMap<Class<? extends Annotation>, List<ZYAspectNormalMethod>> mAspectPointcutMethodListMap = new HashMap<>();

    public static <T> T getInstance(Class<T> clazz) {
        return (T) classBeanMap.get(clazz);
    }

    public static <T> T getInstance(String beanName) {
        return (T) beanNameMap.get(beanName);
    }

    public static void init(String pnScan) {
        List<Class<?>> classes = ClassUtil.getClasses(pnScan);
        for (Class<?> clazz : classes) {
            if (isAspectClazz(clazz)) {
                initAspect(clazz);
            }
        }
        for (Class<?> clazz : classes) {
            if (isBeanClazz(clazz)) {
                initBean(clazz);
            }
        }
    }

    private static void initAspect(Class<?> aspectClazz) {
       
    	Method[] methods = aspectClazz.getMethods();
        Object obj;
        try {
            obj = aspectClazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (Method method : methods) {
           
        	 if (method.isAnnotationPresent(TestBefore.class)) {
             	TestBefore adviceBefore = (TestBefore) method.getAnnotation(TestBefore.class);
                 List<ZYAspectNormalMethod> adviceList = getAspectAdviceList(TestBefore.class);
                 adviceList.add(new ZYAspectNormalMethod(adviceBefore.value(), adviceBefore.order(), obj, method));
             }
            if (method.isAnnotationPresent(TestAfter.class)) {
            	TestAfter adviceAfter = method.getAnnotation(TestAfter.class);
                List<ZYAspectNormalMethod> adviceList = getAspectAdviceList(TestAfter.class);
                adviceList.add(new ZYAspectNormalMethod(adviceAfter.value(), adviceAfter.order(), obj, method));
            }
            if (method.isAnnotationPresent(TestAround.class)) {
            	TestAround adviceAround = method.getAnnotation(TestAround.class);
                List<ZYAspectNormalMethod> adviceList = getAspectAdviceList(TestAround.class);
                adviceList.add(new ZYAspectAroundMethod(adviceAround.value(), adviceAround.order(), obj, method));
            }
           
        }

    }

    private static List<ZYAspectNormalMethod> getAspectAdviceList(Class<? extends Annotation> adviceClazz) {
        List<ZYAspectNormalMethod> methodList = mAspectPointcutMethodListMap.get(adviceClazz);
        if (null == methodList) {
            methodList = new ArrayList<>();
            mAspectPointcutMethodListMap.put(adviceClazz, methodList);
        }
        return methodList;
    }

    private static boolean isAspectClazz(Class<?> aClass) {
        if (aClass.isAnnotationPresent(TestAspect.class)) {
            return true;
        }
        return false;
    }

    private static void initBean(Class<?> beanClazz) {
    
        Class<?>[] interfaces = beanClazz.getInterfaces();
        if (null == interfaces) return;
        for (Class<?> anInterface : interfaces) {
            String beanName = getBeanName(anInterface);
            Object obj = newInstanceProxyClass(anInterface, beanClazz);
            beanNameMap.put(beanName, obj);
            classBeanMap.put(anInterface, obj);
        }
    }

    private static Object newInstanceProxyClass(Class<?> anInterface, Class<?> beanClazz) {
        try {
            Object targetObj = beanClazz.newInstance();
            return Proxy.newProxyInstance(targetObj.getClass().getClassLoader(), new Class<?>[]{anInterface}, new TestAspectHandler(targetObj, mAspectPointcutMethodListMap));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getBeanName(Class<?> anInterface) {
        return anInterface.getSimpleName();
    }

    private static boolean isBeanClazz(Class<?> aClass) {
        if (aClass.isAnnotationPresent(TestComponent.class)) {
            return true;
        }
        return false;
    }
}
