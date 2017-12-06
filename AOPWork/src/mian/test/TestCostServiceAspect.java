package mian.test;

import mian.aop.TestAfter;
import mian.aop.TestAround;
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
  @TestAround(value = "cost", order = 2)
  public Object around(TestJointPoint point) {
      System.out.println("Cost around start");
      Object result = point.invokeResult();
      System.out.println("Cost around end");
      return result;
  }

  @TestAround(value = "cost", order = 3)
  public Object around2(TestJointPoint point) {
      System.out.println("Cost around2 start");
      Object result = point.invokeResult();
      System.out.println("Cost around2 end");
      return result;
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
