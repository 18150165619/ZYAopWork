package com.zhuyan.aop.test;

import com.zhuyan.aop.service.TestCost;
import com.zhuyan.aop.service.TestSell;
import com.zhuyan.aop.util.ZYInitFactory;


public class AOPTest {

	public static void main(String[] args) {
		
		ZYInitFactory.init("com.zhuyan.aop");
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
