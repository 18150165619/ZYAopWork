package mian.test.impl;

import mian.aop.TestComponent;
import mian.test.TestCost;

@TestComponent
public class TestCostServiceImpl implements TestCost{

	@Override
	public void cost() {
		System.out.println("今天花费了6000元！");
		
	}

}
