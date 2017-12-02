package lab1tw;

public class ThreadDec extends Thread {
	public IncDecDummy obj;
	
	public ThreadDec(IncDecDummy obj) {
		this.obj = obj;
	}
	
	public void run() {
		for (int i=0; i<1e6; i++)
			obj.dec();
	}
}
