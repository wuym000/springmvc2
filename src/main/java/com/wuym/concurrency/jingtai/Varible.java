package com.wuym.concurrency.jingtai;

public class Varible {

    private static Varible varible = new Varible();
    private int  seq = 0;
    private Varible(){

    }

    public /*synchronized*/ int nextSeq(){
        synchronized (this) {
            seq++;

        /*for(int i=0; i<600; i++){
            int j = i ;
        }*/
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        /*System.out.println("原子操作当中seq=" + seq);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

            return seq;
        }
    }

    public int getSeq(){
        return seq;
    }

    public void setSeq(int seq){
        this.seq = seq;
    }

    public static Varible getInstance(){
        return varible;
    }
}
