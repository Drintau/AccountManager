package drintau.accountmanager.shared;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LogQueue {

    private LogQueue(){}
    private static class InitLogQueue {
        private static final LogQueue INSTANCE = new LogQueue();
    }
    public static LogQueue getInstance(){
        return InitLogQueue.INSTANCE;
    }

    private LinkedBlockingQueue<String> logQueue;

    public synchronized void init() {
        if (logQueue == null) {
            logQueue = new LinkedBlockingQueue<>(100);
        }
    }

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
