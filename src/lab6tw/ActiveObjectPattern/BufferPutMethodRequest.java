package lab6tw.ActiveObjectPattern;

import java.util.concurrent.CompletableFuture;

class BufferPutMethodRequest implements MethodRequest<Integer> {
    private final BufferDefault buffer;
    private final int number;
    private final CompletableFuture<Integer> completableFuture;

    BufferPutMethodRequest(int number, BufferDefault buffer, CompletableFuture<Integer> completableFuture) {
        this.number = number;
        this.buffer = buffer;
        this.completableFuture = completableFuture;
    }

    @Override
    public boolean guard() {
        return buffer.getBufferSize() + number <= buffer.getBufferMaxSize();
    }

    @Override
    public Integer call() throws Exception {
        int currentStateBuffer = buffer.put(number);
        completableFuture.complete(currentStateBuffer);

        return currentStateBuffer;
    }
}
