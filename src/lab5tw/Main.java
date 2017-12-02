package lab5tw;

public class Main {
    public static void main(String[] args) {
//        int threadNumber = 3;
//
//        new MandelbrotFixedThreadPool(threadNumber)
//                .compute()
//                .setVisible(true);
//
        Mandelbrot.testSpeed(Mandelbrot.FIXED_THREAD_POOL);
        Mandelbrot.testSpeed(Mandelbrot.WORK_STEALING_POOL);

    }
}
