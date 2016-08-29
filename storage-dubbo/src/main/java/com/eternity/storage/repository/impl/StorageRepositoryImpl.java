package com.eternity.storage.repository.impl;

import com.eternity.storage.driver.Driver;
import com.eternity.storage.driver.DriverManager;
import com.eternity.storage.driver.mongodb.MongoDriver;
import com.eternity.storage.driver.mongodb.spring.StandardMongoRepositoryFactory;
import com.eternity.storage.repository.Repository;
import com.mongodb.*;
import org.bson.BSON;
import org.bson.BSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloudsher on 2016/7/29.
 */
public class StorageRepositoryImpl {


    private Driver driver = new MongoDriver();
    private StandardMongoRepositoryFactory factory;

//    public List findAll() {
////        Mongo mongo = ((MongoDriver)driver).getDriver("");
//        DB db = mongo.getDB("customer");
//        DBCollection collection = db.getCollection("customer");
//        DBCursor cursor = collection.find();
//        List list = new ArrayList<>(cursor.size());
//        while (cursor.hasNext()){
//            list.add(cursor.next());
//        }
//        return list;
//    }

    public <T> Repository getRepository(Class<T> clazz){
        if(clazz != null){
//            T repository = factory.getRepository(clazz);
//            repository.
        }
        return null;
    }



}
