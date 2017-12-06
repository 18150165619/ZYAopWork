package mian.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Ryan
 * On 2017/10/5.
 */
class AspectHandler implements InvocationHandler {
    private Object targetObj;
    private HashMap<Class<? extends Annotation>, List<AspectAdviceMethod>> mAspectPointcutMethodListMap;
    private HashMap<Method, List<AspectAdviceMethod>> mAfterMethodMap = new HashMap<>();
  
    public AspectHandler(Object targetObj, HashMap<Class<? extends Annotation>, List<AspectAdviceMethod>> map) {
        this.targetObj = targetObj;
        this.mAspectPointcutMethodListMap = map;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      List<AspectAdviceMethod> afterMethods = getAfterMethodList(method);
      Object obj;
      obj = method.invoke(targetObj, args);
       TestJointPoint afterPoint = new TestJointPoint().setTargetObj(targetObj).setProxy(proxy).setMethod(method).setArgs(args).setResult(obj);
        for (AspectAdviceMethod adviceMethod : afterMethods) {
            adviceMethod.invoke(afterPoint);
        }
        return obj;
    }

   
    private static void sortAdviceList(List<? extends AspectAdviceMethod> list) {
        Collections.sort(list, (Comparator<AspectAdviceMethod>) (o1, o2) -> {
            Integer order1 = o1.getPointMethodOrder();
            Integer order2 = o2.getPointMethodOrder();
            return order1.compareTo(order2);
        });
    }

    private List<AspectAdviceMethod> getAfterMethodList(Method method) {
        return getAspectAdviceMethods(TestAfter.class, mAspectPointcutMethodListMap, mAfterMethodMap, method);
    }

 
    private static List<AspectAdviceMethod> getAspectAdviceMethods(Class<? extends Annotation> adviceClass, HashMap<Class<? extends Annotation>, List<AspectAdviceMethod>> dataMap, HashMap<Method, List<AspectAdviceMethod>> methodMap, Method method) {
        List<AspectAdviceMethod> aspectAdviceMethods = methodMap.get(method);
        if (null != aspectAdviceMethods) return aspectAdviceMethods;
        aspectAdviceMethods = new ArrayList<>();
        methodMap.put(method, aspectAdviceMethods);
        List<AspectAdviceMethod> methods = dataMap.get(adviceClass);
        if (null == method) return aspectAdviceMethods;
        for (AspectAdviceMethod adviceMethod : methods) {
            if (adviceMethod.match(method)) {
                aspectAdviceMethods.add(adviceMethod);
            }
        }
        sortAdviceList(aspectAdviceMethods);
        return aspectAdviceMethods;
    }
}
