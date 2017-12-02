package lab6tw.ActiveObjectPattern;

import java.util.concurrent.CompletableFuture;

class BufferGetMethodRequest implements MethodRequest<Integer> {
    private final BufferDefault buffer;
    private final int number;
    private final CompletableFuture<Integer> completableFuture;

    public BufferGetMethodRequest(int number, BufferDefault buffer,
                                  CompletableFuture<Integer> completableFuture) {
        this.buffer = buffer;
        this.number = number;
        this.completableFuture = completableFuture;
    }

    @Override
    public boolean guard() {
        return number <= buffer.getBufferSize();
    }

    @Override
    public Integer call() throws Exception {
        int currentStateBuffer = buffer.get(number);
        completableFuture.complete(currentStateBuffer);

        return currentStateBuffer;
    }
}
