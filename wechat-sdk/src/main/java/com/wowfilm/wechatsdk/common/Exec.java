package com.wowfilm.wechatsdk.common;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.log4j.Logger;

public class Exec {

    private static Logger log = Logger.getLogger(Exec.class);
    
    private static AtomicBoolean isInited = new AtomicBoolean();
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static LinkedBlockingQueue<Runnable> queue;
    private static ThreadPoolExecutor executor;
    
    /**
     * 线程池初始化
     * @param threadCount
     */
    public static void init(int threadCount) {
        
        if(isInited.get())
            throw new IllegalStateException("内部线程池已初始化，请不要重复执行");
        WriteLock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            ThreadFactory factory = new ThreadFactory() {
                
                private final AtomicInteger threadNumber = new AtomicInteger(1);
                @Override
                public Thread newThread(Runnable paramRunnable) {
                    return new Thread(paramRunnable, 
                            "InnerExec-" + this.threadNumber.getAndIncrement());
                }
                
            };
            queue = new LinkedBlockingQueue<Runnable>();
            executor = new ThreadPoolExecutor(threadCount, threadCount, 0L,
                           TimeUnit.MILLISECONDS, queue, factory);
            log.info(String.format("内部线程池初始化成功，固定线程数 [%s]", threadCount));
            isInited.set(true);
        } finally {
            writeLock.unlock();
        }
    }
    
    /**
     * 等待任务完成并关闭线程池
     */
    public static void destroy() {
        
        WriteLock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            if(queue.size() > 0)
                log.info(String.format("尚有进行中的任务 %s 条，正在关闭中", queue.size()));
            executor.shutdown();
            queue = null;
            executor = null;
            log.info("线程池关闭成功");
            isInited.set(false);
        } finally {
            writeLock.unlock();
        }
    }
    
    /**
     * 提交任务
     * @param call
     * @return
     */
    public static Future<String> submit(Callable<String> call) {
        
        if(!isInited.get()) init(20);
        ReadLock readLock = lock.readLock();
        try {
            readLock.lock();
            return executor.submit(call);
        } finally {
            readLock.unlock();
        }
    }
    
}
