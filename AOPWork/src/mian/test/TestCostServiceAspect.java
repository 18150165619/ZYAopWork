package mian.test;

import mian.aop.Aspect;
import mian.aop.TestAfter;
import mian.aop.TestJointPoint;
import mian.aop.TestPointcut;

@Aspect
public class TestCostServiceAspect {
	
  @TestPointcut("^.*?cost\\(\\).*+$")
     public void cost() {
  }

  
  @TestAfter(value = "cost", order = 2)
  public void after1(TestJointPoint point) {
      System.out.println("Cost after1");
  }

  @TestAfter(value = "cost", order = 1)
  public void after2(TestJointPoint point) {
      System.out.println("Cost after2");
  }
	
	

}
