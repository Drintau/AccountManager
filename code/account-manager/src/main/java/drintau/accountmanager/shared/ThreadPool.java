package drintau.accountmanager.shared;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    private ThreadPool(){}
    private static class InitThreadPool {
        private static final ThreadPool INSTANCE = new ThreadPool();
    }
    public static ThreadPool getInstance(){
        return InitThreadPool.INSTANCE;
    }

    // 内部类实现单例已经做到懒加载，所以属性可以在实例化时就初始化了
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));

    public void execute(Runnable task) {
        executor.execute(task);
    }

    /**
     * 等待剩余任务完成再关闭
     */
    public void shutdown() {
        executor.shutdown();
    }

    /**
     * 打断任务强制关闭
     */
    public void shutdownNow() {
        executor.shutdownNow();
    }

}
