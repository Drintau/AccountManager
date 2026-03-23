package drintau.accountmanager.shared;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ThreadPool {

    private ThreadPool(){}
    private static final ThreadPool instance = new ThreadPool();
    public static ThreadPool getInstance(){
        return instance;
    }

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));

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
