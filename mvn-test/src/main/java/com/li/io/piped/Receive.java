package com.li.io.piped;

import java.io.IOException;
import java.io.PipedInputStream;

public class Receive implements Runnable{

	private PipedInputStream input;
	
	public Receive() {
		this.input = new PipedInputStream();
	}
	
	public PipedInputStream getInput() {
		return input;
	}

	@Override
	public void run() {
		byte[] b;
		try {
			b = new byte[input.available()];
			int len = input.read(b);
			String str = new String(b, 0, len);
			System.out.println("receive:"+str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
