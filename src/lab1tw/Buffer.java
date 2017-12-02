package lab1tw;

import lab2tw.BinarySemaphore;

public class Buffer {
	private String text = null;
	private Boolean put = false;
	BinarySemaphore s1 = new BinarySemaphore(true);
	BinarySemaphore s2 = new BinarySemaphore(false);
	
	public void put(String text) throws InterruptedException {
//		while(put == true) 
//			wait();
		
		s1.take();
		this.text = text;
		this.put = true;
		System.out.println("I put "+text);
		s2.give();
		
//		notify();
	}
	
	public void take() throws InterruptedException {
//		while(text == null || put == false) 
//			wait();
		s2.take();
		this.put = false;
		System.out.println("I took "+text);
		s1.give();
		//return this.text;
	}
}
