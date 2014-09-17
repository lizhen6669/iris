package com.li.io.piped;

import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		Send send = new Send("hello");
		Receive receive = new Receive();
		try {
			send.getOutPut().connect(receive.getInput());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread t1 = new Thread(send);
		Thread t2 = new Thread(receive);
		t1.start();
		t2.start();
		
	}

}
