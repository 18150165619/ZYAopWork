package mian.aop;

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
class TestAspectHandler implements InvocationHandler {
    private Object targetObj;
    private HashMap<Class<? extends Annotation>, List<AspectNormalAdviceMethod>> mAspectPointcutMethodListMap;
    private HashMap<Method, List<AspectNormalAdviceMethod>> mAfterMethodMap = new HashMap<>();
    private HashMap<Method, List<AspectNormalAdviceMethod>> mBeforeMethodMap = new HashMap<>();
    private HashMap<Method, AspectAroundMethod> mAroundMethodMap = new HashMap<>();

    public TestAspectHandler(Object targetObj, HashMap<Class<? extends Annotation>, List<AspectNormalAdviceMethod>> map) {
        this.targetObj = targetObj;
        this.mAspectPointcutMethodListMap = map;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    	List<AspectNormalAdviceMethod> beforeMethods = getBeforeMethodList(method);
    	TestJointPoint beforePoint = new TestJointPoint().setTargetObj(targetObj).setProxy(proxy).setMethod(method).setArgs(args);
        for (AspectNormalAdviceMethod adviceMethod : beforeMethods) {
            adviceMethod.invoke(beforePoint);
        }
        AspectAroundMethod aroundMethod = getAroundMethodList(method);
        Object obj;
        if (null != aroundMethod) {
            obj = aroundMethod.invoke(beforePoint);
        } else {
            obj = method.invoke(targetObj, args);
        }
//        Object obj;
//        obj = method.invoke(targetObj, args);
       List<AspectNormalAdviceMethod> afterMethods = getAfterMethodList(method);
        TestJointPoint afterPoint = new TestJointPoint().setTargetObj(targetObj).setProxy(proxy).setMethod(method).setArgs(args).setResult(obj);
        for (AspectNormalAdviceMethod adviceMethod : afterMethods) {
            adviceMethod.invoke(afterPoint);
        }
        return obj;
    }

    private AspectAroundMethod getAroundMethodList(Method method) {
        if (mAroundMethodMap.containsKey(method)) return mAroundMethodMap.get(method);
        List<AspectNormalAdviceMethod> adviceMethods = mAspectPointcutMethodListMap.get(TestAround.class);
        if (null == adviceMethods) return null;
        List<AspectAroundMethod> list = new ArrayList<>();
        for (AspectNormalAdviceMethod adviceMethod : adviceMethods) {
        	AspectAroundMethod adviceAroundMethod = (AspectAroundMethod) adviceMethod;
            if (adviceAroundMethod.match(method)) {
                list.add(adviceAroundMethod);
            }
        }
        AspectAroundMethod aroundMethod = null;
        if (!list.isEmpty()) {
            sortAdviceList(list);
            AspectAroundMethod upMethod = null;
            for (AspectAroundMethod adviceAroundMethod : list) {
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
    private static void sortAdviceList(List<? extends AspectNormalAdviceMethod> list) {
        Collections.sort(list, (Comparator<AspectNormalAdviceMethod>) (o1, o2) -> {
            Integer order1 = o1.getPointMethodOrder();
            Integer order2 = o2.getPointMethodOrder();
            return order1.compareTo(order2);
        });
    }
    private List<AspectNormalAdviceMethod> getBeforeMethodList(Method method) {
        return getAspectAdviceMethods(TestBefore.class, mAspectPointcutMethodListMap, mBeforeMethodMap, method);
    }
    private List<AspectNormalAdviceMethod> getAfterMethodList(Method method) {
        return getAspectAdviceMethods(TestAfter.class, mAspectPointcutMethodListMap, mAfterMethodMap, method);
    }

 
    private static List<AspectNormalAdviceMethod> getAspectAdviceMethods(Class<? extends Annotation> adviceClass, HashMap<Class<? extends Annotation>, List<AspectNormalAdviceMethod>> dataMap, HashMap<Method, List<AspectNormalAdviceMethod>> methodMap, Method method) {
        List<AspectNormalAdviceMethod> aspectAdviceMethods = methodMap.get(method);
        if (null != aspectAdviceMethods) return aspectAdviceMethods;
        aspectAdviceMethods = new ArrayList<>();
        methodMap.put(method, aspectAdviceMethods);
        List<AspectNormalAdviceMethod> methods = dataMap.get(adviceClass);
        if (null == method) return aspectAdviceMethods;
        for (AspectNormalAdviceMethod adviceMethod : methods) {
            if (adviceMethod.match(method)) {
                aspectAdviceMethods.add(adviceMethod);
            }
        }
        sortAdviceList(aspectAdviceMethods);
        return aspectAdviceMethods;
    }
}
