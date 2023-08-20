package com.yuye.base.common.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuye
 * @version 1.0
 */
//@Component
public class LocalQueue {

    /**
     * 配置的任务内存队列数量
     */
    private Integer queueNum;

    /**
     * 处理任务的队列列表
     */
    private final List<BlockingQueue> operateQueue = new ArrayList<>();

    /**
     * 下一个任务处理队列在列表中的下标
     */
    private AtomicInteger index = new AtomicInteger();




    public LocalQueue(BaseLocalQueueRunner command) {
        this(command, 32, 150000);
    }

    public LocalQueue(BaseLocalQueueRunner command, int queueNum, int capacity) {
        if(queueNum <= 0 || capacity <= 0){
            throw new RuntimeException("LocalQueue queueNum/capacity is not less than 0");
        }
        if(command == null){
            throw new RuntimeException("LocalQueue command is not blank");
        }
        this.queueNum = queueNum;
        ExecutorService executors = Executors.newFixedThreadPool(queueNum);
        for (int i = 0; i< queueNum; i++){
            //设置一个队列最大容纳数量
            BlockingQueue blockingQueue = new ArrayBlockingQueue(capacity);
            operateQueue.add(blockingQueue);
            BaseLocalQueueRunner runnable = command.getInstance();
            runnable.setBlockingQueue(blockingQueue);
            executors.execute(runnable);
        }
    }



    /**
     * 轮询
     * @param object
     * @return
     */
    public boolean offerByRoundRobin(Object object) {
        return offerByRoundRobin(object, index.getAndIncrement() % queueNum);
    }

    /**
     * 轮询
     * @param object
     * @param indexQueue 选择的queue index
     * @return
     */
    public boolean offerByRoundRobin(Object object, int indexQueue) {
        index.compareAndSet(queueNum * 10000,0);
        boolean offer = operateQueue.get(indexQueue).offer(object);
        return offer;
    }

    /**
     * 所有队列任务处理完成
     * @return
     */
    public boolean checkProcessFinish() {
        for (BlockingQueue blockingQueue : operateQueue) {
            if (blockingQueue.size() > 0) {
                return false;
            }
        }
        return true;
    }

}
