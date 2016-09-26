package com.eternity.storage.driver.mongodb;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

/**
 * Created by cloudsher on 2016/7/29.
 */
public class MongoConfig{

    private static Logger logger = LoggerFactory.getLogger(MongoConfig.class);
    private String databaseName;
    private String host;
    private int port;

    protected String getDatabaseName() {
        return this.databaseName;
    }

    public MongoConfig(){
        this("127.0.0.1",27017);
    }

    public MongoConfig(String host,int port){
        this.host = host;
        this.port = port;
    }

    public MongoClient mongo() throws Exception {
        return new MongoClient(Collections.singletonList(new ServerAddress(host, port)));
    }


    public static MongoClient getMongo(String host,int port){
        try {
            return new MongoConfig(host,port).mongo();
        } catch (Exception e) {
            logger.error("getMongo({},{}) is error , reason is {}",new String[]{host,port+"",e.toString()},e);
            e.printStackTrace();
        }
        return null;
    }

    public static MongoClient getMongo(){
        try {
            return new MongoConfig().mongo();
        } catch (Exception e) {
            logger.error("getMongo() is error , reason is {}",new String[]{e.toString()},e);
            e.printStackTrace();
        }
        return null;
    }




}

