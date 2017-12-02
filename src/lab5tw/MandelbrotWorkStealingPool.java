package lab5tw;

import java.util.concurrent.Executors;

public class MandelbrotWorkStealingPool extends Mandelbrot {

    public MandelbrotWorkStealingPool(int imagePartsNum) {
            super(imagePartsNum, Executors.newWorkStealingPool());
    }

}
