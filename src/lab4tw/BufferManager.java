package lab4tw;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferManager {
    private BufferCell[] buffer;
    private int[] priorityBuffer;
    private final int bufferSize;
    private final int numberOfProcessingThreads;
    private int cursor;
    private final Lock lock = new ReentrantLock();
    private final Condition priorityBufferStateChanged  = lock.newCondition();

    BufferManager(int bufferSize, int numberOfProcessingThreads) throws Exception {
        if (bufferSize < 1) {
            throw new Exception("BufferDefault size cannot be less than 1");
        }
        this.buffer = new BufferCell[bufferSize];
        this.priorityBuffer = new int[bufferSize];
        this.bufferSize = bufferSize;
        this.numberOfProcessingThreads = numberOfProcessingThreads;

        for (int i=0; i<this.bufferSize; i++) {
            this.buffer[i] = new BufferCell();
            this.priorityBuffer[i] = -1;
        }
    }

    void proccessCell(StreamProcessingUnit streamProcessingUnit) throws Exception {
        // request access for index
        lock.lock();
        try {
            while (!canAccess(streamProcessingUnit)) {
                priorityBufferStateChanged.await();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        // process, produce or consume
        System.out.println("Thread with priority ("+streamProcessingUnit.getPriority()+"): get access to" +
                " cell ("+streamProcessingUnit.getCursor()+")");
        streamProcessingUnit.action(this.buffer[streamProcessingUnit.getCursor()]);


        // release cell
        lock.lock();
        try {
            changePriorityBufferState(streamProcessingUnit);
            streamProcessingUnit.nextCursor();
            priorityBufferStateChanged.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private boolean canAccess(StreamProcessingUnit streamProcessingUnit) {
        return this.priorityBuffer[streamProcessingUnit.getCursor()] == streamProcessingUnit.getPriority();
    }

    private void changePriorityBufferState(StreamProcessingUnit streamProcessingUnit) {
        if ((streamProcessingUnit.getPriority() == this.numberOfProcessingThreads)) {
            this.priorityBuffer[streamProcessingUnit.getCursor()] = -1;
        } else {
            this.priorityBuffer[streamProcessingUnit.getCursor()] = (streamProcessingUnit.getPriority() + 1);
        }
    }

    int getBufferSize() {
        return this.bufferSize;
    }

    int getNumberOfProcessingThreads() {
        return this.numberOfProcessingThreads;
    }

    static class BufferCell {
        private double value;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
