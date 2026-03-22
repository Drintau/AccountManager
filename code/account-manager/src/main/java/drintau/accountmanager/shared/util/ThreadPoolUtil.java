package drintau.accountmanager.shared.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ThreadPoolUtil {

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10));

    public static void execute(Runnable task) {
        executor.execute(task);
    }

    /**
     * 关闭线程池
     */
    public static void shutdown() {
        executor.shutdown();
    }

}
