package com.zhuyan.aop.service;

import com.zhuyan.aop.annotation.TestAfter;
import com.zhuyan.aop.annotation.TestAspect;
import com.zhuyan.aop.annotation.TestBefore;
import com.zhuyan.aop.annotation.TestJointPoint;
import com.zhuyan.aop.annotation.TestPointcut;

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
    public void BeforeOne(TestJointPoint point) {
        System.out.println("sell BeforeOne");
    }

    @TestBefore(value = "sell", order = 2)
    public void BeforeTwo(TestJointPoint point) {
        System.out.println("sell BeforeTwo");
    }
    @TestAfter(value = "sell", order = 2)
    public void afterOne(TestJointPoint point) {
        System.out.println("sell after One");
    }

    @TestAfter(value = "sell", order = 1)
    public void afterTwo(TestJointPoint point) {
        System.out.println("sell after Two");
    }
}
