package mian.test;

import mian.aop.TestAfter;
import mian.aop.TestAspect;
import mian.aop.TestBefore;
import mian.aop.TestJointPoint;
import mian.aop.TestPointcut;

@TestAspect
public class TestCostServiceAspect {
	
  @TestPointcut("^.*?cost\\(\\).*+$")
  public void cost() {
  }

  @TestBefore(value = "cost", order = 1)
  public void before1(TestJointPoint point) {
      System.out.println("Cost before1");
  }

  @TestBefore(value = "cost", order = 2)
  public void before2(TestJointPoint point) {
      System.out.println("Cost before2");
  }
  @TestAfter(value = "cost", order =1)
  public void after1(TestJointPoint point) {
      System.out.println("Cost after1");
  }

  @TestAfter(value = "cost", order = 2)
  public void after2(TestJointPoint point) {
      System.out.println("Cost after2");
  }
	
	

}
