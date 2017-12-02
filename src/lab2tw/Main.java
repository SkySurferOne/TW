package lab2tw;

public class Main {
    public static void main(String[] args) {
        try {
            Baskets baskets = new Baskets(10);
            Shopper shopper = new Shopper(baskets);

            for (int i =0; i<50; i++) {
                Thread thread = new Thread(shopper);
                thread.start();
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
