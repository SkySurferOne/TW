package lab4tw;

public class ProcessingThread implements Runnable, StreamProcessingUnit {
    private int cursor = 0;
    private final int priority;
    private final BufferManager bufferManager;

    public ProcessingThread(BufferManager bufferManager, int priority) {
        this.priority = priority;
        this.bufferManager = bufferManager;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Processing Thread ("+priority+"): requesting access for" +
                        " cell ("+cursor+")");
                this.bufferManager.proccessCell(this);
                System.out.println("Processing Thread ("+priority+"): ended action on current cell");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void action(BufferManager.BufferCell bufferCell) {
        try {
            randomWait(200, 10);
            bufferCell.setValue(bufferCell.getValue() + 1);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
