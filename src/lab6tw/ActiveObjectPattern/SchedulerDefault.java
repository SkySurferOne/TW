package lab6tw.ActiveObjectPattern;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class SchedulerDefault implements Scheduler<MethodRequest<Integer>> {
    private final ActivationQueue<MethodRequest<Integer>> activationQueue = new ActivationQueueDefault();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private boolean runLoop = true;

    @Override
    public void enqueue(MethodRequest<Integer> task) {
        try {
            activationQueue.enqueue(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (runLoop) {
                try {
                    MethodRequest<Integer> methodRequest = activationQueue.dequeue();

                    if (methodRequest.guard()) {
                        Future<Integer> future = executorService.submit(methodRequest);
                        while (!future.isDone());

                    } else {
                        activationQueue.enqueue(methodRequest);
                    }

                } catch (InterruptedException e) {
                    throw new RuntimeException("Task execution was failed!");
                }
            }
        } finally {
            executorService.shutdown();
        }
    }

    void stopScheduler() {
        runLoop = false;
    }
}
