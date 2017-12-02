package lab4tw;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiBuffer implements IMultiBuffer {
    private int bufferSize;
    private final int bufferMaxSize;
    private final Lock lock = new ReentrantLock();
    private final Condition moreProduced  = lock.newCondition();
    private final Condition moreConsumed  = lock.newCondition();

    public MultiBuffer(int bufferSize) throws Exception {
        if (bufferSize < 1) {
            throw new Exception("BufferDefault size cannot be less than 1");
        }

        this.bufferSize = 0;
        this.bufferMaxSize = bufferSize;
    }

    public int getBufferSize() {
        return this.bufferMaxSize;
    }

    public void get(int number) {
        lock.lock();
        try {
            while (bufferSize < number) {
                moreProduced.await(1, TimeUnit.SECONDS);
            }

            bufferSize -= number;
            System.out.println("Consumed "+number);

            moreConsumed.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void put(int number) {
        lock.lock();
        try {
            while (bufferSize + number > bufferMaxSize) {
                moreConsumed.await(1, TimeUnit.SECONDS);
            }

            bufferSize += number;
            System.out.println("Produced "+number);

            moreProduced.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
