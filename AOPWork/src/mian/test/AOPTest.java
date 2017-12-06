package mian.test;

import mian.aop.AopInstanceFactory;


public class AOPTest {

	public static void main(String[] args) {
		
		AopInstanceFactory.init("mian");
		TestSell testsellService = AopInstanceFactory.getInstance("TestSell");
        if (null != testsellService) {
        	testsellService.sell();
	     }
        TestCost testCostService = AopInstanceFactory.getInstance(TestCost.class);
        if (null != testCostService) {
        	testCostService.cost();
	     }
	}
}
