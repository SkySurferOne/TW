package lab2tw;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

public class Baskets {
    private int[] baskets;
    private int basketsAmount;
    private int pointer = 0;
    private CountingSempahore s1;
    private CountingSempahore s2;

    public Baskets(int basketsAmount) throws Exception {
        this.baskets = new int[basketsAmount];
        this.s1 = new CountingSempahore(basketsAmount);
        this.s2 = new CountingSempahore(1);

        for (int i=0; i<basketsAmount; i++) {
            baskets[i] = i+1;
        }
    }

    public void put(int basketNumber) throws InterruptedException  {
        if (pointer >= 0) {
            s2.take();
            baskets[--pointer] = basketNumber;
            s2.give();

            System.out.println("Someone gave back basket: "+basketNumber);
            s1.give();
        }
    }

    public int take() throws InterruptedException {
        s1.take();

        s2.take();
        int basketNumber = baskets[pointer++];
        s2.give();
        System.out.println("Someone took basket: "+basketNumber);

        return basketNumber;
    }
}
