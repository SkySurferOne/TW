package lab4tw;

import java.util.Random;

public class Producer implements Runnable, StreamProcessingUnit {
    private int cursor = 0;
    private final int priority = -1;
    private final BufferManager bufferManager;
    private Random generator = new Random();

    public Producer(BufferManager bufferManager) {
        this.bufferManager = bufferManager;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("ProducerDefault Thread ("+priority+"): requesting access for" +
                        " cell ("+cursor+")");
                this.bufferManager.proccessCell(this);
                System.out.println("ProducerDefault Thread ("+priority+"): ended action on current cell");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void action(BufferManager.BufferCell bufferCell) {
        //int num = generator.nextInt(100) + 1;
        int num = 0;
        bufferCell.setValue(num);
    }

    @Override
    public int getCursor() {
        return this.cursor;
    }


    @Override
    public void nextCursor() {
        this.cursor = (this.cursor + 1) % this.bufferManager.getBufferSize();
    }

    @Override
    public int getPriority() {
        return this.priority;
    }
}
