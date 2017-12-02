package lab3tw;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WaiterMonitor {
    private final Lock lock = new ReentrantLock();
    private boolean bothSeatsOccupied = false;
    private int occupiedPairNumber = -1;

    private final Condition imHereHoney  = lock.newCondition();
    private final Condition freeBothSeat  = lock.newCondition();

    public void getTable(int pairNumber) {
        lock.lock();
        try {
            // wait when two seat are occupied or is not my pair
            while (bothSeatsOccupied || (occupiedPairNumber != pairNumber &&
            occupiedPairNumber != -1)) {
                freeBothSeat.await();
            }

            if (occupiedPairNumber == -1) {
                occupiedPairNumber = pairNumber;
                System.out.println("The half of pair ("+pairNumber+") is waiting for companion...");
                while (!bothSeatsOccupied) {
                    imHereHoney.await();
                }
            } else {
                bothSeatsOccupied = true;
                imHereHoney.signal();
                System.out.println("Both seats are occupied by pair number: "+pairNumber);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
         lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            if (occupiedPairNumber != -1) {
                System.out.println("Adiós! One from pair ("+occupiedPairNumber+") left seat");
                occupiedPairNumber = -1;
            } else {
                bothSeatsOccupied = false;
                freeBothSeat.signalAll();
                System.out.println("That was delicious! Both seat are free.");
            }
        } finally {
         lock.unlock();
        }
    }
}
