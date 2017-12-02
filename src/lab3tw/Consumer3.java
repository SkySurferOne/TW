package lab3tw;

public class Consumer3 implements Runnable {
    private BoundedBuffer buffer;

    public Consumer3(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

   public void run() {
        //for(int i = 0; i < 10; i++) {
            try {
            	String str = (String) buffer.take();
            	System.out.println("I get "+str);
            	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        //}

    }
}
