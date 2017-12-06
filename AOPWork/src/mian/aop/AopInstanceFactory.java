package mian.aop;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ryan
 * On 2017/10/5.
 */
public class AopInstanceFactory {
    private static final HashMap<Class<?>, Object> classBeanMap = new HashMap<>();
    private static final HashMap<String, Object> beanNameMap = new HashMap<>();
    private static final HashMap<Class<? extends Annotation>, List<AspectNormalAdviceMethod>> mAspectPointcutMethodListMap = new HashMap<>();

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
                 List<AspectNormalAdviceMethod> adviceList = getAspectAdviceList(TestBefore.class);
                 adviceList.add(new AspectNormalAdviceMethod(adviceBefore.value(), adviceBefore.order(), obj, method));
             }
            if (method.isAnnotationPresent(TestAfter.class)) {
            	TestAfter adviceAfter = method.getAnnotation(TestAfter.class);
                List<AspectNormalAdviceMethod> adviceList = getAspectAdviceList(TestAfter.class);
                adviceList.add(new AspectNormalAdviceMethod(adviceAfter.value(), adviceAfter.order(), obj, method));
            }
            if (method.isAnnotationPresent(TestAround.class)) {
            	TestAround adviceAround = method.getAnnotation(TestAround.class);
                List<AspectNormalAdviceMethod> adviceList = getAspectAdviceList(TestAround.class);
                adviceList.add(new AspectAroundMethod(adviceAround.value(), adviceAround.order(), obj, method));
            }
           
        }

    }

    private static List<AspectNormalAdviceMethod> getAspectAdviceList(Class<? extends Annotation> adviceClazz) {
        List<AspectNormalAdviceMethod> methodList = mAspectPointcutMethodListMap.get(adviceClazz);
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
