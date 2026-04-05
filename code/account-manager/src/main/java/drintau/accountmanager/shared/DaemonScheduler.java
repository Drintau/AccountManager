package drintau.accountmanager.shared;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DaemonScheduler {

    private DaemonScheduler() {}
    private static class InitDaemonScheduler {
        private static final DaemonScheduler INSTANCE = new DaemonScheduler();
    }
    public static DaemonScheduler getInstance() {
        return InitDaemonScheduler.INSTANCE;
    }

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2, r -> {
        Thread t = new Thread(r, "am-daemon-scheduler");
        t.setDaemon(true);  // 设置为守护线程
        return t;
    });


    /**
     * scheduleWithFixedDelay 是 上一个任务执行完后，再等待一个时间，执行下一个任务
     * 不是说等待到了固定时间就立刻执行下一个任务，可以避免执行任务时间过长导致任务堆积
     *
     * @param task         任务
     * @param initialDelay 第一次任务延迟多少时间执行
     * @param delay        任务完成后，延迟多久执行下一次任务
     * @param unit         时间单位
     */
    public void submitDelayTask(Runnable task, long initialDelay, long delay, TimeUnit unit) {
        scheduler.scheduleWithFixedDelay(wrapTask(task), initialDelay, delay, unit);
    }

    /**
     * 一次性的延时任务
     *
     * @param task         任务
     * @param initialDelay 延迟多久执行
     * @param unit         时间单位
     */
    public void submitOnceDelayTask(Runnable task, long initialDelay, TimeUnit unit) {
        scheduler.schedule(wrapTask(task), initialDelay, unit);
    }

    /**
     * 包装任务，添加异常处理
     */
    private Runnable wrapTask(Runnable task) {
        return () -> {
            try {
                task.run();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        };
    }

    /**
     * 等待剩余任务完成再关闭
     */
    public void shutdown() {
        scheduler.shutdown();
    }

    /**
     * 打断任务强制关闭
     */
    public void shutdownNow() {
        scheduler.shutdownNow();
    }

}
