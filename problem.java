import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Item {

    private final String data;

    public Item(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}

class Producer implements Runnable {
    private final String name;
    private final BlockingQueue<Item> queue;

    public Producer(String name, BlockingQueue<Item> queue) {
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Item item = new Item(name + " item: " + i);
                
                queue.put(item);
                System.out.println("Producer " + name + " produced item: " + item.getData());
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

class Consumer implements Runnable {
    private final String name;
    private final BlockingQueue<Item> queue;

    public Consumer(String name, BlockingQueue<Item> queue) {
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Item item = queue.take();
                System.out.println("Consumer " + name + " consumed item: " + item.getData());
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

class ProducerConsumerDemo {

    public static void main(String[] args) throws InterruptedException {
        
        BlockingQueue<Item> queue = new LinkedBlockingQueue<>(5);

      
        Producer producer1 = new Producer("A", queue);
        Producer producer2 = new Producer("B", queue);
        Consumer consumer1 = new Consumer("X", queue);
        Consumer consumer2 = new Consumer("Y", queue);

 
        new Thread(producer1).start();
        new Thread(producer2).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();

        
        Thread.sleep(5000); 
        System.out.println("Exiting main thread...");
    }
}
