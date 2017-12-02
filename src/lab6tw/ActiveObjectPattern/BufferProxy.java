package lab6tw.ActiveObjectPattern;

import java.util.concurrent.Future;

public interface BufferProxy {
    Future<Integer> put(int number);
    Future<Integer> get(int number);
}
