package drintau.accountmanager.shared;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPool {

    private ThreadPool(){}
    private static class InitThreadPool {
        private static final ThreadPool INSTANCE = new ThreadPool();
        static {
            INSTANCE.executor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));
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
//        log.debug("线程池关闭");
        executor.shutdown();
    }

    /**
     * 强制关闭
     */
    public void shutdownNow() {
//        log.debug("线程池关闭");
        executor.shutdownNow();
    }

}
