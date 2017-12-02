package lab2tw;

public class BinarySemaphore {
	int s = 1;
	
	public BinarySemaphore(Boolean setOne) {
		if(!setOne)
			this.s = 0;
	}
	
	public synchronized void take() throws InterruptedException {
		while (s == 0) {
			this.wait();
		}
		
		s--;
	}
	
	public synchronized void give() {
		if (s == 0) {
			s++;
			this.notify();
		}
	}
}
