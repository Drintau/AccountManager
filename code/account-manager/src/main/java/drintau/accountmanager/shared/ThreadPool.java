package drintau.accountmanager.shared;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    private ThreadPool(){}
    private static class InitThreadPool {
        private static final ThreadPool INSTANCE = new ThreadPool();
        static {
            INSTANCE.executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));
        }
    }
    public static ThreadPool getInstance(){
        return InitThreadPool.INSTANCE;
    }

    private ThreadPoolExecutor executor;

    public void execute(Runnable task) {
        executor.execute(task);
    }

    /**
     * 关闭线程池
     */
    public void shutdown() {
        executor.shutdown();
    }

}
