package drintau.accountmanager.shared;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public final class LogQueue {

    private LogQueue(){}
    private static final LogQueue instance = new LogQueue();
    public static LogQueue getInstance(){
        return instance;
    }

    private final Queue<String> logQueue = new LinkedBlockingQueue<>(100);

    public void offer(String logStr) {
        logQueue.offer(logStr);
    }

    public String poll() {
        return logQueue.poll();
    }

}
