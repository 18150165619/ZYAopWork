package mian.test.impl;

import mian.aop.TestComponent;
import mian.test.TestSell;

@TestComponent
public class TestSellServiceImpl implements TestSell {

	@Override
	public void sell() {
		// TODO Auto-generated method stub
		System.out.println("我是卖电脑的！");
	}

}
