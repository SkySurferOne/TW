package lab6tw.ActiveObjectPattern;

import java.util.concurrent.Callable;

interface MethodRequest<T> extends Callable<T> {
    boolean guard();
}
