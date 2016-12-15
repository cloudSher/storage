package com.eternity.storage.driver.redis;

import com.eternity.storage.driver.Driver;
import redis.clients.jedis.Jedis;

/**
 * Created by Administrator on 2016/10/17.
 */
public class JedisDriver implements Driver{


    public Jedis getPersistence(){
        return new Jedis("127.0.0.1");
    }

    public static void  test(){
        JedisDriver driver = new JedisDriver();
        Jedis jedis = driver.getPersistence();
        jedis.set("test","hello");
        System.out.println(jedis.get("test"));
    }


    public static void main(String args[]){
        test();
    }


}
