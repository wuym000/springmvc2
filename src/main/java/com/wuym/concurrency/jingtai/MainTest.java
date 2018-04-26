package com.wuym.concurrency.jingtai;

public class MainTest implements Runnable {
    public static void main(String[] args) {
        Thread[] threads = new Thread[40];
        for(int i=0; i < threads.length; i++){
            threads[i] = new Thread(new MainTest());
        }
        for(Thread thread : threads){
            thread.start();
        }

        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        /*System.out.println(Thread.currentThread().getName() + "线程唤醒");

        new Thread(new ReadSeq()).start();*/
    }

    static class ReadSeq implements Runnable {
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
            System.out.println(Varible.getInstance().getSeq());
            Varible.getInstance().setSeq(0);
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
        Varible varible = Varible.getInstance();
        System.out.println(varible.nextSeq());
    }
}
