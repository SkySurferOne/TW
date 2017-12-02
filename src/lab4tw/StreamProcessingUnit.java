package lab4tw;

import java.util.Random;

import static java.lang.Thread.sleep;

public interface StreamProcessingUnit {
    void action(BufferManager.BufferCell bufferCell);
    int getCursor();
    void nextCursor();
    int getPriority();
    default void randomWait(int max, int min) throws InterruptedException {
        Random generator = new Random();
        int waitTime = generator.nextInt(max + 1) + min;
        sleep(waitTime);
    }
}
