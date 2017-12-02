package lab6tw.ActiveObjectPattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class ActivationQueueDefault implements ActivationQueue<MethodRequest<Integer>> {
    private final BlockingQueue<MethodRequest<Integer>> taskQueue = new LinkedBlockingQueue<>();

    @Override
    public void enqueue(MethodRequest<Integer> task) throws InterruptedException {
        taskQueue.put(task);
    }

    @Override
    public MethodRequest<Integer> dequeue() throws InterruptedException {
        return taskQueue.take();
    }
}
