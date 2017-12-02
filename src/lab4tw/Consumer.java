package lab4tw;

public class Consumer implements Runnable, StreamProcessingUnit {
    private int cursor = 0;
    private final int priority;
    private final BufferManager bufferManager;
    private double totalSum = 0;

    Consumer(BufferManager bufferManager) {
        this.bufferManager = bufferManager;
        this.priority = bufferManager.getNumberOfProcessingThreads();
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Consumer Thread ("+priority+"): requesting access for" +
                        " cell ("+cursor+")");
                this.bufferManager.proccessCell(this);
                System.out.println("Consumer Thread ("+priority+"): ended action on current cell");


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void action(BufferManager.BufferCell bufferCell) {
        totalSum += bufferCell.getValue();
    }

    @Override
    public int getCursor() {
        return this.cursor;
    }

    private void actionAfterBufferConsumption() {
        System.out.println("Total sum is: " + totalSum);
        totalSum = 0;
    }

    @Override
    public void nextCursor() {
        int bufferSize = this.bufferManager.getBufferSize();
        if ((this.cursor + 1) == bufferSize) {
            actionAfterBufferConsumption();
        }

        this.cursor = (this.cursor + 1) % bufferSize;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }
}
