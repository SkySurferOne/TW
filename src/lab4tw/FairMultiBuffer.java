package lab4tw;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class FairMultiBuffer implements IMultiBuffer {
    private int bufferSize;
    private final int bufferMaxSize;
    private final Semaphore bufferSemaphore;

    public FairMultiBuffer(int bufferSize) throws Exception {
        if (bufferSize < 1) {
            throw new Exception("BufferDefault size cannot be less than 1");
        }

        this.bufferSize = 0;
        this.bufferMaxSize = bufferSize;
        this.bufferSemaphore = new Semaphore(bufferSize, true);
    }

    public int getBufferSize() {
        return this.bufferMaxSize;
    }

    @Override
    public void get(int number) {
        if (bufferSize >= number) {
            bufferSemaphore.release(number);

            synchronized (this) {
                bufferSize -= number;
                System.out.println("Consumed "+number);
            }
        }
    }

    @Override
    public void put(int number) {
        try {
            bufferSemaphore.tryAcquire(number, 1, TimeUnit.SECONDS);
            synchronized (this) {
                bufferSize += number;
                System.out.println("Produced "+number);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
