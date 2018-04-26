package com.wuym.concurrency.xianshisuo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 显示锁
 * */
public class Xianshisuo {
    private static Xianshisuo suo = new Xianshisuo();
    private final Lock lock = new ReentrantLock();
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
