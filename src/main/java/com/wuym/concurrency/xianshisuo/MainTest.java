package com.wuym.concurrency.xianshisuo;

public class MainTest implements Runnable{
    public static void main(String[] args) {
        Thread[] threads = new Thread[40];
        for(int i=0; i < threads.length; i++){
            threads[i] = new Thread(new MainTest());
        }
        for(Thread thread : threads){
            thread.start();
        }
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        Xianshisuo varible = Xianshisuo.getInstance();
        System.out.println(varible.nextSeq());
    }
}
