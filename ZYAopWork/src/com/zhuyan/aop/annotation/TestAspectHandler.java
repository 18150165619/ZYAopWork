package com.zhuyan.aop.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ryan
 * On 2017/10/5.
 */
public class TestAspectHandler implements InvocationHandler {
    private Object targetObj;
    private HashMap<Class<? extends Annotation>, List<ZYAspectNormalMethod>> mAspectPointcutMethodListMap;
    private HashMap<Method, List<ZYAspectNormalMethod>> mAfterMethodMap = new HashMap<>();
    private HashMap<Method, List<ZYAspectNormalMethod>> mBeforeMethodMap = new HashMap<>();
    private HashMap<Method, ZYAspectAroundMethod> mAroundMethodMap = new HashMap<>();

    public TestAspectHandler(Object targetObj, HashMap<Class<? extends Annotation>, List<ZYAspectNormalMethod>> map) {
        this.targetObj = targetObj;
        this.mAspectPointcutMethodListMap = map;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    	List<ZYAspectNormalMethod> beforeMethods = getBeforeMethodList(method);
    	TestJointPoint beforePoint = new TestJointPoint().setTargetObj(targetObj).setProxy(proxy).setMethod(method).setArgs(args);
        for (ZYAspectNormalMethod adviceMethod : beforeMethods) {
            adviceMethod.invoke(beforePoint);
        }
        ZYAspectAroundMethod aroundMethod = getAroundMethodList(method);
        Object obj;
        if (null != aroundMethod) {
            obj = aroundMethod.invoke(beforePoint);
        } else {
            obj = method.invoke(targetObj, args);
        }
//        Object obj;
//        obj = method.invoke(targetObj, args);
       List<ZYAspectNormalMethod> afterMethods = getAfterMethodList(method);
        TestJointPoint afterPoint = new TestJointPoint().setTargetObj(targetObj).setProxy(proxy).setMethod(method).setArgs(args).setResult(obj);
        for (ZYAspectNormalMethod adviceMethod : afterMethods) {
            adviceMethod.invoke(afterPoint);
        }
        return obj;
    }

    private ZYAspectAroundMethod getAroundMethodList(Method method) {
        if (mAroundMethodMap.containsKey(method)) return mAroundMethodMap.get(method);
        List<ZYAspectNormalMethod> adviceMethods = mAspectPointcutMethodListMap.get(TestAround.class);
        if (null == adviceMethods) return null;
        List<ZYAspectAroundMethod> list = new ArrayList<>();
        for (ZYAspectNormalMethod adviceMethod : adviceMethods) {
        	ZYAspectAroundMethod adviceAroundMethod = (ZYAspectAroundMethod) adviceMethod;
            if (adviceAroundMethod.match(method)) {
                list.add(adviceAroundMethod);
            }
        }
        ZYAspectAroundMethod aroundMethod = null;
        if (!list.isEmpty()) {
            sortAdviceList(list);
            ZYAspectAroundMethod upMethod = null;
            for (ZYAspectAroundMethod adviceAroundMethod : list) {
                if (null == aroundMethod) aroundMethod = adviceAroundMethod;
                if (null != upMethod) {
                    upMethod.setNextMethod(adviceAroundMethod);
                }
                upMethod = adviceAroundMethod;
            }
        }
        mAroundMethodMap.put(method, aroundMethod);
        return aroundMethod;
    }
    private static void sortAdviceList(List<? extends ZYAspectNormalMethod> list) {
        Collections.sort(list, (Comparator<ZYAspectNormalMethod>) (o1, o2) -> {
            Integer order1 = o1.getPointMethodOrder();
            Integer order2 = o2.getPointMethodOrder();
            return order1.compareTo(order2);
        });
    }
    private List<ZYAspectNormalMethod> getBeforeMethodList(Method method) {
        return getAspectAdviceMethods(TestBefore.class, mAspectPointcutMethodListMap, mBeforeMethodMap, method);
    }
    private List<ZYAspectNormalMethod> getAfterMethodList(Method method) {
        return getAspectAdviceMethods(TestAfter.class, mAspectPointcutMethodListMap, mAfterMethodMap, method);
    }

 
    private static List<ZYAspectNormalMethod> getAspectAdviceMethods(Class<? extends Annotation> adviceClass, HashMap<Class<? extends Annotation>, List<ZYAspectNormalMethod>> dataMap, HashMap<Method, List<ZYAspectNormalMethod>> methodMap, Method method) {
        List<ZYAspectNormalMethod> aspectAdviceMethods = methodMap.get(method);
        if (null != aspectAdviceMethods) return aspectAdviceMethods;
        aspectAdviceMethods = new ArrayList<>();
        methodMap.put(method, aspectAdviceMethods);
        List<ZYAspectNormalMethod> methods = dataMap.get(adviceClass);
        if (null == method) return aspectAdviceMethods;
        for (ZYAspectNormalMethod adviceMethod : methods) {
            if (adviceMethod.match(method)) {
                aspectAdviceMethods.add(adviceMethod);
            }
        }
        sortAdviceList(aspectAdviceMethods);
        return aspectAdviceMethods;
    }
}
