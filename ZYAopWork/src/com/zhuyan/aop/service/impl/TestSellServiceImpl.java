package com.zhuyan.aop.service.impl;

import com.zhuyan.aop.annotation.TestComponent;
import com.zhuyan.aop.service.TestSell;

@TestComponent
public class TestSellServiceImpl implements TestSell {

	@Override
	public void sell() {
		// TODO Auto-generated method stub
		System.out.println("���빫˾�������ԣ��엦׼��ȥ��");
	}

}
