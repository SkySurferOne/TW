package lab1tw;

public class IncDecDummy {
	private int i = 0;
	
	public synchronized void inc() {
		i++;
	}
	
	public synchronized void dec(){
		i--;
	}
	
	public int getI() {
		return i;
	}
}
