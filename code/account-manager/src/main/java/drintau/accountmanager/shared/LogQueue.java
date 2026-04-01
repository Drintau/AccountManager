package drintau.accountmanager.shared;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LogQueue {

    private LogQueue(){}
    private static class InitLogQueue {
        private static final LogQueue INSTANCE = new LogQueue();
        static {
            INSTANCE.logQueue = new LinkedBlockingQueue<>(100);
        }
    }
    public static LogQueue getInstance(){
        return InitLogQueue.INSTANCE;
    }

    private LinkedBlockingQueue<String> logQueue;

    public void offer(String logStr) {
        if (logQueue != null) {
            logQueue.offer(logStr);
        }
    }

    public String poll(long timeout) throws InterruptedException {
        if (logQueue != null) {
            return logQueue.poll(timeout, TimeUnit.MILLISECONDS);
        }
        return null;
    }

}
