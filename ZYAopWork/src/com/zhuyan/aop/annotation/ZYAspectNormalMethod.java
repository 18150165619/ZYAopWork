package com.zhuyan.aop.annotation;

import java.lang.reflect.Method;

/**
 * Created by Ryan
 * On 2017/10/6.
 */
public class ZYAspectNormalMethod {
    
	private String pointMethodName;
    private int pointMethodOrder;
    protected Object aspectObj;
    protected Method pointcutMethod;
    private TestPointcut pointcut;

    public ZYAspectNormalMethod(String pointMethodName, int pointMethodOrder,Object aspectObj, Method pointcutMethod) {
        this.pointMethodName = pointMethodName;
        this.pointMethodOrder = pointMethodOrder;
        this.aspectObj = aspectObj;
        this.pointcutMethod = pointcutMethod;
        try {
            pointcut = aspectObj.getClass().getMethod(pointMethodName).getAnnotation(TestPointcut.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Object invoke(TestJointPoint point) {
        try {
            return pointcutMethod.invoke(aspectObj, point);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean match(Method method) {
        String methodName = method.toString();
        if (methodName.matches(pointcut.value())) {
            return true;
        }
        return false;
    }

    public int getPointMethodOrder() {
        return pointMethodOrder;
    }
}
