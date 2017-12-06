package mian.test;

import mian.aop.InstanceFactory;


public class AOPTest {

	public static void main(String[] args) {
		
		InstanceFactory.init("mian");
//		InstanceFactory.init("mian.test");
//		InstanceFactory.init("mian.test.impl");
		TestSell testService = InstanceFactory.getInstance("TestSell");
        if (null != testService) {
	    	 testService.sell();
	     }
	}
}
