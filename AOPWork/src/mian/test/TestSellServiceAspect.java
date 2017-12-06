package mian.test;

import mian.aop.Aspect;
import mian.aop.TestAfter;
import mian.aop.TestJointPoint;
import mian.aop.TestPointcut;

/**
 * Created by Ryan
 * On 2017/10/5.
 */
@Aspect
public class TestSellServiceAspect {

    @TestPointcut("^.*?sell\\(\\).*+$")
       public void sell() {
    }

    
    @TestAfter(value = "sell", order = 2)
    public void after1(TestJointPoint point) {
        System.out.println("sell after1");
    }

    @TestAfter(value = "sell", order = 1)
    public void after2(TestJointPoint point) {
        System.out.println("sell after2");
    }
}
