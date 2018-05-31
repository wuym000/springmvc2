package com.wuym.controller;

import com.wuym.zookeeper.ZooKeeperConnection;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@RestController
public class ZkController {
    // create static instance for zookeeper class.
    private static ZooKeeper zk;

    // create static instance for ZooKeeperConnection class.
    private static ZooKeeperConnection conn;

    @RequestMapping("/zkcreate")
    public String create(String name){
        conn = new ZooKeeperConnection();
        try {
            zk = conn.connect("localhost");
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "fail";
        }
        try {
            zk.create("/FirstZnodeFromJavaClient", new String("my first zookeeper app").getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zk.close();
        } catch (KeeperException e) {
            e.printStackTrace();
            return "fail";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    @RequestMapping("/getData")
    public String getData(String znode){
        conn = new ZooKeeperConnection();
        try {
            zk = conn.connect ("localhost");

        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "fail";
        }
        Stat stat = null;
        znode = "/" + znode;
        System.out.println(1);
        try {

            stat = zk.exists(znode, true);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final CountDownLatch connectedSignal = new CountDownLatch(1);
        if(stat != null){
            int version = stat.getVersion();
            try {
                byte[] data = zk.getData(znode, new Watcher() {

                    public void process(WatchedEvent we) {
                        System.out.println("接受通知："+we.getState());
                        if (we.getType() == Event.EventType.None) {
                            switch (we.getState()) {
                                case Expired:
                                    connectedSignal.countDown();
                                    break;
                            }

                        } else {
                            String path = "/FirstZnodeFromJavaClient";

                            try {
                                byte[] bn = zk.getData(path,
                                        false, null);
                                String data = new String(bn,
                                        "UTF-8");
                                System.out.println(data);
                                connectedSignal.countDown();

                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                        try {
                            conn.close();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }, null);
                String value = new String(data);
            //    zk.close();
                System.out.println("version is "+ version + ",data is " + value);
                return "version is "+ version + ",data is " + value;
            } catch (KeeperException e) {
                e.printStackTrace();
                return "fail";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "fail";
            }
        }else{
            return "fail";
        }


    }
}
