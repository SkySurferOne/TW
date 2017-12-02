package lab3tw;


public class Producer3 implements Runnable {
	private BoundedBuffer buffer;

    public Producer3(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

   public void run() {
        for(int i = 0;  i < 100; i++) {
            try {
				buffer.put("message "+i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }

    }
}
