package mian.aop;

import java.lang.reflect.Method;

/**
 * Created by Ryan
 * On 2017/10/6.
 */
class AspectAroundMethod extends AspectNormalAdviceMethod {
  
	private AspectAroundMethod method;

    public AspectAroundMethod(String pointMethodName, int pointMethodOrder, Object aspectObj, Method pointcutMethod) {
        super(pointMethodName, pointMethodOrder, aspectObj, pointcutMethod);
    }

    @Override
    public Object invoke(TestJointPoint point) {
        if (null != method) {
            return super.invoke(point.copy().setMethod(method.pointcutMethod).setTargetObj(aspectObj).setArgs(new Object[]{point}));
        } else {
            return super.invoke(point);
        }
    }

    public AspectAroundMethod setNextMethod(AspectAroundMethod method) {
        this.method = method;
        return this;
    }
}
