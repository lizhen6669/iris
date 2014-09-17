package com.li.design.adapter;

/**
 * 适配器模式测试类
 * 适配器模式典型案例：
 * java.io.InputStream(target);
 * java.io.ByteArrayInputStream(adapter),byte[](adaptee);
 * java.io.FileInputStream(adapter), java.io.File(adaptee)
 * @author lizhen
 *
 */
public class Test {
	public static void main(String args[]) {
		Adaptee adaptee = new Adaptee();
		Target t = new Adapter(adaptee);
		t.request();
	}
}
