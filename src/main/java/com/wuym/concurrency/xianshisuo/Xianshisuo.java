package com.wuym.concurrency.xianshisuo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 显示锁
 * */
public class Xianshisuo {
    private static Xianshisuo suo = new Xianshisuo();
    private final Lock lock = new ReentrantLock();

    /**
     * 读写锁
     * */
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

  // private final Lock lock = new ReentrantLock(true); 参数true为公平锁
    private int seq = 0;
    public int nextSeq(){
        lock.lock();
        try {
            seq++;
            Thread.sleep(1);
            return seq;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return seq;
        } finally {
            lock.unlock();
        }
    }

    public static Xianshisuo getInstance(){
        return suo;
    }

}
