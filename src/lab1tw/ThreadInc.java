package lab1tw;

public class ThreadInc extends Thread {
	public IncDecDummy obj;
	
	public ThreadInc(IncDecDummy obj) {
		this.obj = obj;
	}
	
	public void run() {
		for (int i=0; i<1e6; i++)
			obj.inc();
	}
}
