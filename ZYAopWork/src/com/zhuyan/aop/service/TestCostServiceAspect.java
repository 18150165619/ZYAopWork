package com.zhuyan.aop.service;

import com.zhuyan.aop.annotation.TestAfter;
import com.zhuyan.aop.annotation.TestAround;
import com.zhuyan.aop.annotation.TestAspect;
import com.zhuyan.aop.annotation.TestBefore;
import com.zhuyan.aop.annotation.TestJointPoint;
import com.zhuyan.aop.annotation.TestPointcut;

@TestAspect
public class TestCostServiceAspect {
	
  @TestPointcut("^.*?cost\\(\\).*+$")
  public void cost() {
  }

  @TestBefore(value = "cost", order = 1)
  public void beforeOne(TestJointPoint point) {
      System.out.println("Cost beforeOne");
  }

  @TestBefore(value = "cost", order = 2)
  public void beforeTwo(TestJointPoint point) {
      System.out.println("Cost beforeTwo");
  }
  @TestAround(value = "cost", order = 2)
  public Object aroundOne(TestJointPoint point) {
      System.out.println("Cost aroundOne start");
      Object result = point.invokeResult();
      System.out.println("Cost aroundOne end");
      return result;
  }

  @TestAround(value = "cost", order = 3)
  public Object aroundTwo(TestJointPoint point) {
      System.out.println("Cost aroundTwo start");
      Object result = point.invokeResult();
      System.out.println("Cost aroundTwo end");
      return result;
  }
  @TestAfter(value = "cost", order =1)
  public void afterOne(TestJointPoint point) {
      System.out.println("Cost after One");
  }

  @TestAfter(value = "cost", order = 2)
  public void afterTwo(TestJointPoint point) {
      System.out.println("Cost after Two");
  }
	
	

}
