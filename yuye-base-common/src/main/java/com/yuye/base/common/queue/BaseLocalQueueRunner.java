package com.yuye.base.common.queue;

import java.util.concurrent.BlockingQueue;

public class BaseLocalQueueRunner implements Runnable{

    protected BlockingQueue blockingQueue;

    public BlockingQueue getBlockingQueue() {
        return blockingQueue;
    }

    public void setBlockingQueue(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public BaseLocalQueueRunner getInstance(){
        //TODO 复制一个属性 写完之后，替换这里
        return null;
    }

    @Override
    public void run() {

    }
}
