package lab6tw.ActiveObjectPattern;

interface Scheduler<T> extends Runnable {
    void enqueue(T task);
}
