package com.li.io.piped;

import java.io.IOException;
import java.io.PipedOutputStream;

public class Send implements Runnable{
	
	private PipedOutputStream outPut;
	
	private String str;
	
	public Send(String str) {
		this.outPut = new PipedOutputStream();
		this.str = str;
	}
	
	public PipedOutputStream getOutPut() {
		return outPut;
	}

	@Override
	public void run() {
		byte[] b = str.getBytes();
		try {
			outPut.write(b);
			System.out.println("send:"+str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				outPut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
