package com.li.design.decorator;

/**
 * 装饰类的具体实现
 * @author lizhen
 *
 */
public class ConcreteDecorator extends Decorator{

	public ConcreteDecorator(Component component) {
		super(component);
		// TODO Auto-generated constructor stub
	}

	public void operation() {
		System.out.println("do some decoration");
		super.operation();
	}
}
