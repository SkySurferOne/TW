package lab5tw;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import javax.swing.JFrame;


public class Mandelbrot extends JFrame {
    private final int imagePartsNum;
    private final ExecutorService pool;
    private BufferedImage I;
    private static final int MAX_ITER = 570;
    private static final double ZOOM = 150;
    public static final int FIXED_THREAD_POOL = 1;
    public static final int WORK_STEALING_POOL = 2;

    public Mandelbrot(int imagePartsNum, ExecutorService executor) {
        super("Mandelbrot Set");

        this.imagePartsNum = imagePartsNum;
        this.pool = executor;
        this.configure();
    }

    private void configure() {
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    public static class Point {
        private final int x, y, rgb;

        public Point(int x, int y, int rgb) {
            this.x = x;
            this.y = y;
            this.rgb = rgb;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getRgb() {
            return rgb;
        }
    }

    public static class ComputePart implements Callable<Point[]> {
        private final int x1, y1, x2, y2;
        private final Point[] points;
        private double zx, zy, cX, cY, tmp;

        public ComputePart(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.points = new Point[Math.abs(x2-x1)*Math.abs(y2-y1)];
        }

        @Override
        public Point[] call() throws Exception {
            int i = 0;
            for (int y = y1; y < y2; y++) {
                for (int x = x1; x < x2; x++) {
                    zx = zy = 0;
                    cX = (x - 400) / ZOOM;
                    cY = (y - 300) / ZOOM;
                    int iter = MAX_ITER;
                    while (zx * zx + zy * zy < 4 && iter > 0) {
                        tmp = zx * zx - zy * zy + cX;
                        zy = 2.0 * zx * zy + cY;
                        zx = tmp;
                        iter--;
                    }
                    points[i++] = new Point(x, y,  iter | (iter << 8));
                }
            }

            return points;
        }
    }

    public Mandelbrot compute() {
        int height = getHeight();
        int width  = getWidth();
        int partWidth = (int) Math.ceil(width / imagePartsNum);
        Set<Future<Point[]>> set = new HashSet<Future<Point[]>>();

        for (int i = 0; i < imagePartsNum; i++) {
            int x1 = i * partWidth;
            int x2 = (i + 1) * partWidth;
            x2 = x2 > width ? width : x2;

            Callable<Point[]> callable = new ComputePart(x1, 0, x2, height);
            Future<Point[]> future = pool.submit(callable);
            set.add(future);
        }

        for (Future<Point[]> future : set) {
            try {
                Point[] points = future.get();
                for (Point point : points) {
                    I.setRGB(point.getX(), point.getY(), point.getRgb());
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        return this;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void testSpeed(int execServiceType) {
        int vmin = 1;
        int vmax = 30;

        if (execServiceType == FIXED_THREAD_POOL) {
            for (int i = vmin; i <= vmax; i++) {
                long measure[] = new long[10];

                for (int j = 0; j < 10; j++) {
                    Instant before = Instant.now();

                    ExecutorService executorService = Executors.newFixedThreadPool(i);
                    new Mandelbrot(i, executorService).compute();

                    Instant after = Instant.now();
                    long delta = Duration.between(before, after).toMillis();
                    measure[j] = delta;
                }

                computeStat("FixedThreadPool (" + i + ") ", measure);
            }
        } else if (execServiceType == WORK_STEALING_POOL) {
            for (int i = vmin; i <= vmax; i++) {
                long measure[] = new long[10];

                for (int j = 0; j < 10; j++) {
                    Instant before = Instant.now();

                    ExecutorService executorService = Executors.newWorkStealingPool();
                    new Mandelbrot(i, executorService).compute();

                    Instant after = Instant.now();
                    long delta = Duration.between(before, after).toMillis();
                    measure[j] = delta;
                }

                computeStat("WorkStealingPool (" + i + ") ", measure);
            }
        }
    }

    private static void computeStat(String text, long measure[]) {
        Statistics statistics = new Statistics(measure);
        double mean = statistics.getMean();
        double stdDev = statistics.getStdDev();
        System.out.println(text + "mean: "+mean+"[ms], std dev: "+stdDev+" [ms]");
    }

}