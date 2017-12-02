package lab4tw;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Thread.sleep;

public class MultiProducer implements Runnable {
    private final IMultiBuffer multiBuffer;
    private Random generator = new Random();
    private int maxProduce;
    // private final int iterNum;
    private final Writer writer;
    private final long timeMillis;

    public MultiProducer(IMultiBuffer multiBuffer, int maxProduce, long timeMillis, Writer writer) {
        this.multiBuffer = multiBuffer;
        this.maxProduce = maxProduce;
        this.timeMillis = timeMillis;
        this.writer = writer;
    }

    @Override
    public void run() {
        long startLooping = System.currentTimeMillis();
        while(System.currentTimeMillis() - startLooping < timeMillis) {
            int num = generateRandom();
            long start = System.nanoTime();
            multiBuffer.put(num);
            long end = System.nanoTime();

            long time = end - start;

            try {
                CSVUtils.writeLine(writer, Arrays.asList("p", Long.toString(time), Integer.toString(num)), ';');
            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//                randomWait(200, 10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    private int generateRandom() {
        return generator.nextInt(maxProduce) + 1;
    }

    void randomWait(int max, int min) throws InterruptedException {
        Random generator = new Random();
        int waitTime = generator.nextInt(max + 1) + min;
        sleep(waitTime);
    }
}
