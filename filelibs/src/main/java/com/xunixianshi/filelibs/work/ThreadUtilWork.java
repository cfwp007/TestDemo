package com.xunixianshi.filelibs.work;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
  * @Description:     TODO 异步工具
  * @Author:         zhongxiaoming
  * @CreateDate:     2020/9/28 13:57
  * @Version:        1.0
 */
public class ThreadUtilWork {

    private BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
    private ExecutorService executorService = null;


    private volatile static ThreadUtilWork sInstance;

    public static ThreadUtilWork getInstance() {
        if (sInstance == null) {
            synchronized (ThreadUtilWork.class) {
                if (sInstance == null) {
                    sInstance = new ThreadUtilWork();
                }
            }
        }
        return sInstance;
    }


    public static void destroy() {
        if (sInstance != null) {
            sInstance.executorService.shutdownNow();
            sInstance.executorService = null;
            sInstance = null;
        }
    }

//    1. newCachedThreadPool 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
//    2. newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
//    3. newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
//    4. newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
    private ThreadUtilWork() {

        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        int KEEP_ALIVE_TIME = 1;
        TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

        executorService = Executors.newSingleThreadExecutor();/*new ThreadPoolExecutor(NUMBER_OF_CORES,
                NUMBER_OF_CORES * 2,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                taskQueue,
                new ThreadPoolExecutor.AbortPolicy());*/
    }


    public void execute(Runnable command) {
        if (executorService != null) {
            executorService.execute(command);
        }
    }


}
