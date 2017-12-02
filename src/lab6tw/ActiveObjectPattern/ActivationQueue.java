package lab6tw.ActiveObjectPattern;

interface ActivationQueue<T> {
    void enqueue(T task) throws InterruptedException;
    T dequeue() throws InterruptedException;
}
