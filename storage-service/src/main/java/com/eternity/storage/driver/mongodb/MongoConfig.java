package com.eternity.storage.driver.mongodb;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import java.util.Collections;

/**
 * Created by cloudsher on 2016/7/29.
 */
public class MongoConfig{


    protected String getDatabaseName() {
        return "";
    }

    public Mongo mongo() throws Exception {
        return new MongoClient(Collections.singletonList(new ServerAddress("127.0.0.1", 27017)));
    }


    public static Mongo getMongo(){
        try {
            return new MongoConfig().mongo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

