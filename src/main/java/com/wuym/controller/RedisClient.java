package com.wuym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by wuym on 2017/6/19.
 */
@Controller
public class RedisClient {
    @Autowired
    private ShardedJedisPool shardedJedisPool;
    @RequestMapping("/redis")
    public ModelAndView testRedis(String key){
        ModelAndView mav = new ModelAndView("redisClient");
        Jedis jedis = new Jedis("139.196.122.8", 6379, 50000,50000);
        jedis.auth("741258qq");
        String value = jedis.get(key);
        mav.addObject("java", value);
        return mav;
    }

    private void returnResource(ShardedJedis shardedJedis) {
        try {
            shardedJedis.close();
            //shardedJedisPool.returnResource(shardedJedis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/shardRedis")
    public ModelAndView shard(String key){
        ModelAndView mav = new ModelAndView("redisClient");
        ShardedJedis jedis = shardedJedisPool.getResource();
       try{
            String value = jedis.get(key);
            /*try {

                // 去redis中取回序列化后的对象
                byte[] obj = jedis.get(key.getBytes());

                // 取回的对象反序列化
                if (obj != null) {
                    ret = SerializeUtil.unserialize(obj);
                }*/
            mav.addObject("java", value);
        } catch (Exception e) {
            jedis.close();
        } finally {
            jedis.close();
        }
        return mav;
    }
}
