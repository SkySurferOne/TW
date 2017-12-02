package lab4tw;

import java.io.FileWriter;
import java.util.Arrays;

import static java.lang.Thread.sleep;

public class MultiMain {

    public static void main(String[] args) throws Exception {
        if (args.length < 6) {
            throw new Exception("Too less arguments");
        }

        int M = Integer.parseInt(args[0]);
        int p = Integer.parseInt(args[1]);
        int c = Integer.parseInt(args[2]);
        int timeMillis = Integer.parseInt(args[3]);
        boolean fair = args[4].equals("true");
        String dataDir = System.getProperty("user.dir") + "/src/lab4tw/data/";
        String fileName = dataDir + args[5];

        FileWriter fileWriter = new FileWriter(fileName);
        IMultiBuffer multiBuffer;

        try {
            if (fair) {
                multiBuffer = new FairMultiBuffer(2*M);
            } else {
                multiBuffer = new MultiBuffer(2*M);
            }

            MultiProducer producer = new MultiProducer(multiBuffer, M, timeMillis, fileWriter);
            MultiConsumer consumer = new MultiConsumer(multiBuffer, M, timeMillis, fileWriter);

            System.out.println("BufferDefault size="+multiBuffer.getBufferSize() +
                    ", producers="+p+", consumers="+c);

            for (int i=0; i<p; i++) {
                new Thread(producer).start();
            }

            for (int i=0; i<c; i++) {
                new Thread(consumer).start();
            }

            sleep(timeMillis + 500);

            fileWriter.flush();
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
