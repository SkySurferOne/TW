package lab6tw.ActiveObjectPattern;

import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class ProducerDefault implements Runnable {
    private final static Logger log = Logger.getLogger(ProducerDefault.class.getName());

    private final BufferDefaultProxy bufferProxy;
    private final Random generator = new Random();
    private final CyclicBarrier startSync;
    private final CountDownLatch countDownLatch;

    public ProducerDefault(BufferDefaultProxy bufferProxy,
                           CyclicBarrier startSync, CountDownLatch countDownLatch) {
        this.bufferProxy = bufferProxy;
        this.startSync = startSync;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        String threadId = "(tid: " + Thread.currentThread().getId() + ")";

        try {
            startSync.await();

            while (true) {
                int number = getRandomNumber();

                log.info(threadId + " produce request: " + number);

                randomWait(300, 1);
                Future<Integer> currentStateBufferFuture = bufferProxy.put(number);

                while (!currentStateBufferFuture.isDone());

                log.info(threadId + " current state of buffer: " + currentStateBufferFuture.get());
            }

            // countDownLatch.countDown();

        } catch (InterruptedException | BrokenBarrierException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private int getRandomNumber() {
        return (generator.nextInt(bufferProxy.getBufferMaxSize()) / 2) + 1;
    }

    void randomWait(int max, int min) throws InterruptedException {
        Random generator = new Random();
        int waitTime = generator.nextInt(max - min + 1) + min;
        sleep(waitTime);
    }
}
