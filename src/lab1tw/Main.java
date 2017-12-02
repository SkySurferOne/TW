package lab1tw;

public class Main {

	public static void main(String[] args) {
//		IncDecDummy incDecDummy = new IncDecDummy();
//		ThreadDec threadDec = new ThreadDec(incDecDummy);
//		ThreadInc threadInc = new ThreadInc(incDecDummy);
//		
//		threadDec.start();
//		threadInc.start();
//		
//		try {
//			threadDec.join();
//			threadInc.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(incDecDummy.getI());
		
		Buffer buffer = new Buffer();
		Producer producer = new Producer(buffer);
		Consumer consumer = new Consumer(buffer);
		
		for (int i =0; i<1; i++) {
			Thread thread = new Thread(producer);
			thread.start();
		}
		
		for (int i =0; i<10; i++) {
			Thread thread = new Thread(consumer);
			thread.start();
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
