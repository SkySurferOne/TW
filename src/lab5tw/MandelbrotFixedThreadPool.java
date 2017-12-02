package lab5tw;

import java.util.concurrent.Executors;

public class MandelbrotFixedThreadPool extends Mandelbrot {

    public MandelbrotFixedThreadPool(int threadNum) {
        super(threadNum,  Executors.newFixedThreadPool(threadNum));
    }

}
