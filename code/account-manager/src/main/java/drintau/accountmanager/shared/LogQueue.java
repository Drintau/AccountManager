package drintau.accountmanager.shared;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LogQueue {

    private LogQueue(){}
    private static final LogQueue instance = new LogQueue();
    public static LogQueue getInstance(){
        return instance;
    }

    private LinkedBlockingQueue<String> logQueue;

    public void init() {
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
