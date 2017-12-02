package lab2tw;

public class CountingSempahore {
	private int s;
	private final int maxSize;
	
	public CountingSempahore(int value) throws Exception {
		if (value < 0) {
			throw new Exception("Value has to be greater or equal than 0");
		}
		this.s = value; 
		this.maxSize = value; 
	}
	
	public synchronized void take() throws InterruptedException {
		while (s <= 0) {
			this.wait();
		}
		
		s--;
	}
	
	public synchronized void give() {
		if (s < maxSize) {
			s++;
			this.notify();
		}
	}
}
