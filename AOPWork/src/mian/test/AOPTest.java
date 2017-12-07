package mian.test;

import mian.aop.ZYInitFactory;


public class AOPTest {

	public static void main(String[] args) {
		
		ZYInitFactory.init("mian");
		TestSell testsellService = ZYInitFactory.getInstance("TestSell");
        if (null != testsellService) {
        	testsellService.sell();
	     }
        TestCost testCostService = ZYInitFactory.getInstance(TestCost.class);
        if (null != testCostService) {
        	testCostService.cost();
	     }
	}
}
