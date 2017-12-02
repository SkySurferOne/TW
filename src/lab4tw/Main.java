package lab4tw;

public class Main {

    public static void main(String[] args) {
        int bufferSize = 5;
        int numberOfProcessingThreads = 10;
        try {
            BufferManager bufferManager = new BufferManager(bufferSize, numberOfProcessingThreads);
            Producer producer = new Producer(bufferManager);
            Consumer consumer = new Consumer(bufferManager);

            new Thread(producer).start();
            new Thread(consumer).start();
            for (int i=0; i<numberOfProcessingThreads; i++) {
                Thread processingThread = new Thread(new ProcessingThread(bufferManager, i));
                processingThread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
