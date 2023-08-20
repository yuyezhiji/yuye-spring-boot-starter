package com.yuye.base.common.test;

import com.yuye.base.common.queue.BaseLocalQueueRunner;
import com.yuye.base.common.queue.LocalQueue;
import org.apache.tomcat.jni.Local;

import javax.annotation.PostConstruct;

public class LocalCacheTest {
    public static void main(String[] args) {


        LocalQueue localQueue = new LocalQueue(new TestThread());
        localQueue.offerByRoundRobin("123");
        localQueue.offerByRoundRobin("456");
        localQueue.offerByRoundRobin("789");
    }

public static class TestThread extends BaseLocalQueueRunner {
    @Override
    public BaseLocalQueueRunner getInstance() {
        return new TestThread();
    }

    @Override
    public void run() {
        try {
            Object cache = blockingQueue.take();
            System.out.println(cache);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
}
