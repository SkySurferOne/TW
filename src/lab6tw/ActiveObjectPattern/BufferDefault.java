package lab6tw.ActiveObjectPattern;

import java.util.logging.Logger;

class BufferDefault implements Servant, Buffer {
    private final static Logger log = Logger.getLogger(BufferDefault.class.getName());

    private int bufferSize;
    private final int bufferMaxSize;

    BufferDefault(int bufferSize) throws Exception {
        if (bufferSize < 2) {
            throw new Exception("BufferDefault size cannot be less than 2");
        }

        this.bufferSize = 0;
        this.bufferMaxSize = bufferSize;
    }

    int getBufferMaxSize() {
        return this.bufferMaxSize;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public int put(int number) throws Exception {
        if (number > bufferMaxSize / 2) {
            throw new Exception("Putting more than half size of buffer can cause deadlock");
        }

        bufferSize += number;

        log.info("Produced: "+number+", state of buffer: "+bufferSize);

        return bufferSize;
    }

    public int get(int number) throws Exception {
        if (number > bufferMaxSize / 2) {
            throw new Exception("Getting more than half size of buffer can cause deadlock");
        }

        bufferSize -= number;

        log.info("Consumed: "+number+", state of buffer: "+bufferSize);

        return bufferSize;
    }

}
