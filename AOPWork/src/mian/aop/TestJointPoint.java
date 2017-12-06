package mian.aop;

import java.lang.reflect.Method;

/**
 * Created by Ryan
 * On 2017/10/6.
 */
public class TestJointPoint {
    private Object proxy;
    private Method method;
    private Object[] args;
    private Object targetObj;
    private Object result;

    public Object getProxy() {
        return proxy;
    }

    public TestJointPoint setProxy(Object proxy) {
        this.proxy = proxy;
        return this;
    }

    public Method getMethod() {
        return method;
    }

    public TestJointPoint setMethod(Method method) {
        this.method = method;
        return this;
    }

    public Object[] getArgs() {
        return args;
    }

    public TestJointPoint setArgs(Object[] args) {
        this.args = args;
        return this;
    }

    public Object getTargetObj() {
        return targetObj;
    }

    public TestJointPoint setTargetObj(Object targetObj) {
        this.targetObj = targetObj;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public TestJointPoint setResult(Object result) {
        this.result = result;
        return this;
    }

    public Object invokeResult() {
        try {
            return method.invoke(targetObj, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TestJointPoint copy() {
        TestJointPoint jp = new TestJointPoint();
        jp.proxy = this.proxy;
        jp.method = this.method;
        jp.args = this.args;
        jp.targetObj = this.targetObj;
        jp.result = this.result;
        return jp;
    }
}
