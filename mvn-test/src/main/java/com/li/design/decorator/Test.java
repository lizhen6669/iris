package com.li.design.decorator;

/**
 * 装饰模式测试类
 * 典型案例：
 * java.io.InputStream(Component);
 * java.io.FilterInputStream(Decorator);
 * java.io.BufferedInputStream(ConcreteDecorator)
 * @author lizhen
 *
 */
public class Test {
	public static void main(String args[]) {
		Component component = new ConcreteComponent();
		Component decorator = new ConcreteDecorator(component);
		decorator.operation();
	}
}
