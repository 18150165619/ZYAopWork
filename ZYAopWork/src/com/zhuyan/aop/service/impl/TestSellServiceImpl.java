package com.zhuyan.aop.service.impl;

import com.zhuyan.aop.annotation.TestComponent;
import com.zhuyan.aop.service.TestSell;

@TestComponent
public class TestSellServiceImpl implements TestSell {

	@Override
	public void sell() {
		// TODO Auto-generated method stub
		System.out.println("联想公司在卖电脑，朱棪准备去买！");
	}

}
