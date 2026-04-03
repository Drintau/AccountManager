package drintau.accountmanager.shared;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DaemonScheduler {

    private DaemonScheduler(){}
    private static class InitDaemonScheduler {
        private static final DaemonScheduler INSTANCE = new DaemonScheduler();
        static {
            INSTANCE.scheduler =
                    Executors.newScheduledThreadPool(1, r -> {
                        Thread t = new Thread(r, "am-daemon-scheduler");
                        t.setDaemon(true);  // 设置为守护线程
                        return t;
                    });
        }
    }
    public static DaemonScheduler getInstance(){
        return InitDaemonScheduler.INSTANCE;
    }

    private ScheduledExecutorService scheduler;

    /**
     * scheduleWithFixedDelay 是 上一个任务执行完后，再等待一个时间，执行下一个任务
     * 不是说等待到了固定时间就立刻执行下一个任务，可以避免执行任务时间过长导致任务堆积
     * @param task 任务
     * @param initialDelay 第一次任务延迟多少时间执行
     * @param delay 任务完成后，延迟多久执行下一次任务
     * @param unit 时间单位
     */
    public void submitDelayTask(Runnable task, long initialDelay, long delay, TimeUnit unit) {
        scheduler.scheduleWithFixedDelay(wrapTask(task), initialDelay, delay, unit);
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

    public void shutdown() {
//        log.debug("调度器关闭");
        scheduler.shutdown();
    }

    public void shutdownNow() {
//        log.debug("调度器关闭");
        scheduler.shutdownNow();
    }

}
