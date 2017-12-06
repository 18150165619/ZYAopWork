package mian.test;

public class TestCostServiceAspect implements TestCost{

	@Override
	public void cost() {
		System.out.println("花费了6000元！");
		
	}
	
	

}
