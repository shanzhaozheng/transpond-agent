package com.szzii.cn.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {

    public static final int corePoolSize = 5;

    public static final int MaxCorePoolSize = 5;

    public static final int MaxQueueSize = 100;

    public static final ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize,MaxCorePoolSize,0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(MaxQueueSize), new ThreadPoolExecutor.DiscardPolicy());



}
