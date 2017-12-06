package mian.test;

import mian.aop.TestAfter;
import mian.aop.TestAspect;
import mian.aop.TestBefore;
import mian.aop.TestJointPoint;
import mian.aop.TestPointcut;

/**
 * Created by Ryan
 * On 2017/10/5.
 */
@TestAspect
public class TestSellServiceAspect {

    @TestPointcut("^.*?sell\\(\\).*+$")
       public void sell() {
    }
    @TestBefore(value = "sell", order = 1)
    public void Before1(TestJointPoint point) {
        System.out.println("sell Before1");
    }

    @TestBefore(value = "sell", order = 2)
    public void Before2(TestJointPoint point) {
        System.out.println("sell Before2");
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
