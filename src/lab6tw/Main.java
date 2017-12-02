package lab6tw;

import lab6tw.ActiveObjectPattern.BufferDefaultProxy;
import lab6tw.ActiveObjectPattern.BufferProxy;
import lab6tw.ActiveObjectPattern.ConsumerDefault;
import lab6tw.ActiveObjectPattern.ProducerDefault;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Main {
    enum Action {
        getProducent,
        getConsumer,
        getProducentOrConsumer
    }

    private static int bufferSize = 10;
    private static int producentsNumber = 12;
    private static int consumersNumber = 12;
    private static int clientsNumber = producentsNumber + consumersNumber;
    private static Runnable[] clients = new Runnable[clientsNumber];

    private static volatile CyclicBarrier startSync = new CyclicBarrier(clientsNumber);
    private static volatile CountDownLatch stopSync = new CountDownLatch(clientsNumber);;

    private static volatile BufferDefaultProxy bufferProxy;

    public static void main(String[] args) {
        try {
            bufferProxy = new BufferDefaultProxy(bufferSize);

            for (int i=0; i<clientsNumber; i++) {
                Runnable client = null;
                if (producentsNumber > 0 && consumersNumber > 0) {
                    client = getClient(Action.getProducentOrConsumer);
                } else if (producentsNumber > 0) {
                    client = getClient(Action.getProducent);
                } else if (consumersNumber > 0){
                    client = getClient(Action.getConsumer);
                }

                if (client instanceof ProducerDefault) {
                    producentsNumber--;
                } else if (client instanceof ConsumerDefault) {
                    consumersNumber--;
                }

                clients[i] = client;
            }

            for (Runnable runnable : clients) {
                new Thread(runnable).start();
            }

            stopSync.await();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bufferProxy != null) {
               bufferProxy.shutdownScheduler();
            }
        }
    }

    private static Runnable getClient(Action action) {
        switch (action) {
            case getConsumer:
                return new ConsumerDefault(bufferProxy, startSync, stopSync);

            case getProducent:
                return new ProducerDefault(bufferProxy, startSync, stopSync);

            case getProducentOrConsumer:
                Random random = new Random();
                boolean makeProducer = random.nextFloat() > 0.5;

                return makeProducer ? new ProducerDefault(bufferProxy, startSync, stopSync) :
                        new ConsumerDefault(bufferProxy, startSync, stopSync);

            default:
                return null;
        }
    }

}
