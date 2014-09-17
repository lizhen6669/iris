package com.li.design.decorator;

/**
 * 装饰类
 * @author lizhen
 *
 */
public class Decorator implements Component{
	
	private Component component;
	
	public Decorator(Component component) {
		this.component = component;
	}

	@Override
	public void operation() {
		// TODO Auto-generated method stub
		component.operation();
	}

}
