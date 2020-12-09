package com.szzii.cn.util;

import com.szzii.cn.common.expection.DingThresholdException;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 半衰计数器
 */
public class ConcurrentCounter {

    /**
     * 计数器
     */
    private AtomicInteger count;

    /**
     * 开始时间
     */
    private long startTime;

    /**
     * 最大阈值
     */
    private int countThreshold;

    /**
     * 半衰时间,单位毫秒
     */
    private int timeThreshold;

    /**
     * 是否已经异常报告过了
     */
    private boolean flag = true;


    private ReentrantLock lock = new ReentrantLock();



    public ConcurrentCounter() {
        this(0,10,60000);
    }

    public ConcurrentCounter(int initCount) {
        this(initCount,10,60000);
    }

    public ConcurrentCounter(int initCount, int countThreshold, int timeThreshold) {
        count = new AtomicInteger(initCount);
        startTime = System.currentTimeMillis();
        this.countThreshold = countThreshold;
        this.timeThreshold = timeThreshold;
    }

    /**
     * 为提高性能，容忍超出阈值
     * @return
     */
    public boolean incr() throws DingThresholdException {
        if (isThreshold()){
            if (lock.tryLock()) {
                if (flag) {
                    flag = false;
                    throw new DingThresholdException("异常太多了！已经连续发送" + count.get() + "次了，让我休息" + timeThreshold / 1000 + "秒在给你发吧。");
                } else {
                    return false;
                }
            }
        }else {
            startTime = System.currentTimeMillis();
            count.incrementAndGet();
            return true;
        }
        return false;
    }


    public boolean isThreshold(){
        if (count.get() >= countThreshold){
            long difference = System.currentTimeMillis() - startTime;
            if (difference >= timeThreshold){
                startTime = System.currentTimeMillis();
                count.set((int) (count.get()/((difference / timeThreshold) * 2)));
                flag = true;
                return false;
            }else {
                return true;
            }

        }else {
            return false;
        }
    }


    public int getCount(){
        return count.get();
    }
}
