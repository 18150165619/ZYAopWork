package com.zhuyan.aop.service.impl;

import com.zhuyan.aop.annotation.TestComponent;
import com.zhuyan.aop.service.TestCost;

@TestComponent
public class TestCostServiceImpl implements TestCost{

	@Override
	public void cost() {
		System.out.println("花费了6000元！");
		
	}

}
