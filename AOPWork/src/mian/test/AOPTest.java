package mian.test;

import mian.aop.InstanceFactory;


public class AOPTest {

	public static void main(String[] args) {
		
		InstanceFactory.init("mian");
//		InstanceFactory.init("mian.test");
//		InstanceFactory.init("mian.test.impl");
		TestSell testsellService = InstanceFactory.getInstance("TestSell");
        if (null != testsellService) {
        	testsellService.sell();
	     }
        TestCost testCostService = InstanceFactory.getInstance("TestCost");
        if (null != testCostService) {
        	testCostService.cost();
	     }
	}
}
