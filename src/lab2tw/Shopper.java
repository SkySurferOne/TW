package lab2tw;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Shopper implements Runnable {
    private Baskets baskets;
    private Random generator = new Random();

    public Shopper(Baskets baskets) {
        this.baskets = baskets;
    }

    private void doShopping() throws InterruptedException {
        int waitTime = generator.nextInt(191) + 10;
        sleep(waitTime);
    }

    public void run() {
        try {
            int basketNumber = baskets.take();
            doShopping();
            baskets.put(basketNumber);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
