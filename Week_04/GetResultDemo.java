import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class GetResultDemo {

    static int result = 0;

    /**
     * FutureTask
     */
    private static void demo1() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            getResult();
            return result;
        });
        new Thread(futureTask).start();

        System.out.println(Thread.currentThread().getName() + " : " + futureTask.get());
    }

    /**
     * join
     */
    private static void demo2() throws InterruptedException {
        Thread thread = new Thread(GetResultDemo::getResult);
        thread.start();
        thread.join();
        System.out.println(Thread.currentThread().getName() + " : " + result);
    }

    /**
     * wait - notify
     */
    private static void demo3() throws InterruptedException {
        Thread thread = new Thread(() -> {
            synchronized (GetResultDemo.class) {
                getResult();
                GetResultDemo.class.notifyAll();
            }
        });

        synchronized (GetResultDemo.class) {
            thread.start();
            GetResultDemo.class.wait();
            System.out.println(Thread.currentThread().getName() + " : " + result);
        }
    }

    /**
     * Lock.condition
     */
    private static void demo4() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread thread = new Thread(() -> {
            lock.lock();
            try {
                getResult();
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        });

        lock.lock();
        try {
            thread.start();
            condition.await();
            System.out.println(Thread.currentThread().getName() + " : " + result);
        } finally {
            lock.unlock();
        }
    }

    /**
     * CountDownLatch
     */
    private static void demo5() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(() -> {
            getResult();
            countDownLatch.countDown();
        }).start();

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + " : " + result);
    }

    /**
     * CyclicBarrier
     */
    private static void demo6() throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        new Thread(() -> {
            getResult();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();

        cyclicBarrier.await();
        System.out.println(Thread.currentThread().getName() + " : " + result);
    }

    /**
     * Semaphore
     */
    private static void demo7() throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        new Thread(() -> {
            try {
                semaphore.acquire();
                getResult();
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        while (true) {
            if (semaphore.availablePermits() == 0) {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " : " + result);
                semaphore.release();
                break;
            }
        }
    }

    /**
     * Exchanger
     */
    private static void demo8() throws InterruptedException {
        Exchanger<Integer> exchanger = new Exchanger<>();

        new Thread(() -> {
            try {
                getResult();
                exchanger.exchange(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Integer result = exchanger.exchange(-1);
        System.out.println(Thread.currentThread().getName() + " : " + result);
    }

    /**
     * LockSupport
     */
    private static void demo9() {
        Thread mainThread = Thread.currentThread();

        new Thread(() -> {
            getResult();
            LockSupport.unpark(mainThread);
        }).start();

        LockSupport.park();
        System.out.println(Thread.currentThread().getName() + " : " + result);
    }

    /**
     * ExecutorService.service()
     */
    private static void demo10() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Integer> future = service.submit(() -> {
            getResult();
            return result;
        });
        System.out.println(Thread.currentThread().getName() + " : " + future.get());
        service.shutdown();
    }

    /**
     * ExecutorService.execute()
     */
    private static void demo11() {
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(GetResultDemo::getResult);
        service.shutdown();
        while (true) {
            if (service.isTerminated()) {
                System.out.println(Thread.currentThread().getName() + " : " + result);
                break;
            }
        }
    }

    /**
     * CompletableFuture
     */
    private static void demo12() throws ExecutionException, InterruptedException {
        CompletableFuture
                .runAsync(GetResultDemo::getResult)
                .thenRun(() -> System.out.println(Thread.currentThread().getName() + " : " + result))
                .get();
    }

    /**
     * BlockingQueue
     */
    private static void demo13() throws InterruptedException {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(1);

        new Thread(() -> {
            getResult();
            blockingQueue.offer(result);
        }).start();

        Integer result = blockingQueue.take();
        System.out.println(Thread.currentThread().getName() + " : " + result);
    }

    /**
     * volatile
     */
    volatile static boolean isDone = false;
    private static void demo14() {
        new Thread(() -> {
            getResult();
            isDone = true;
        }).start();

        while (true) {
            if (isDone) {
                System.out.println(Thread.currentThread().getName() + " : " + result);
                break;
            }
        }
    }

    /**
     * PipelineStream
     */
    private static void demo15() throws IOException {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream inputStream = new PipedInputStream();
        inputStream.connect(outputStream);

        new Thread(() -> {
            try {
                getResult();
                outputStream.write(result);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        int result = inputStream.read();
        System.out.println(Thread.currentThread().getName() + " : " + result);
        inputStream.close();
        outputStream.close();
    }

    private static void getResult() {
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName() + " done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = 10;
    }
}
