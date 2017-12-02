package lab6tw.ActiveObjectPattern;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class BufferDefaultProxy implements Proxy, BufferProxy {
    private final SchedulerDefault scheduler;
    private BufferDefault buffer;

    public BufferDefaultProxy(int bufferSize) throws Exception {
        this.scheduler = new SchedulerDefault();
        this.buffer = new BufferDefault(bufferSize);

        runScheduler();
    }

    public Future<Integer> put(int number) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        MethodRequest<Integer> methodRequest = new BufferPutMethodRequest(number, buffer, completableFuture);
        scheduler.enqueue(methodRequest);

        return completableFuture;
    }

    public Future<Integer> get(int number) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        MethodRequest<Integer> methodRequest = new BufferGetMethodRequest(number, buffer, completableFuture);
        scheduler.enqueue(methodRequest);

        return completableFuture;
    }

    private void runScheduler() {
        Thread schedulerThread = new Thread(scheduler);
        schedulerThread.start();
    }

    public void shutdownScheduler() {
        scheduler.stopScheduler();
    }

    int getBufferMaxSize() {
        return buffer.getBufferMaxSize();
    }
}
