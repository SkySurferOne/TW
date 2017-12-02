package lab3tw;

import java.util.Random;

import static java.lang.Thread.sleep;

public class RestaurantCustomer implements Runnable {

    private final WaiterMonitor waiterMonitor;
    private final Random generator = new Random();
    private final int pairNumber;

    public RestaurantCustomer(WaiterMonitor waiterMonitor, int pairNumber) {
        this.waiterMonitor = waiterMonitor;
        this.pairNumber = pairNumber;
    }

    private void waitRandom() throws InterruptedException {
        int waitTime = generator.nextInt(191) + 10;
        sleep(waitTime);
    }

    @Override
    public void run() {
        while (true) {
            try {
                waitRandom();
                waiterMonitor.getTable(pairNumber);
                waitRandom();
                waiterMonitor.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
