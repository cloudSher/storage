package com.eternity.storage.repository.impl;

import com.eternity.storage.driver.mongodb.MongoConfig;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by Administrator on 2016/9/20.
 */
public class MongoDBTemplate {

    public MongoClient mongo;
    private String databaseName;

    public MongoClient getMongoClient(){
        return this.mongo;
    }

    public MongoDBTemplate(String databaseName){
        this.databaseName = databaseName;
        this.mongo = MongoConfig.getMongo();
    }


    public MongoCollection getDBCollection(String collectionName){
        MongoDatabase database = mongo.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);
        return collection;
    }

    public <T> List<T> find(String collectionName){
        MongoCollection collection = getDBCollection(collectionName);
        FindIterable iterable = collection.find();
        MongoCursor iterator = iterable.iterator();
        List<Object> list = new ArrayList<>();
        while (iterator.hasNext()){
            Object o = iterator.next();
            list.add(o);
        }
        return (List<T>) list;
    }

    public <T> T insert(T t,String collectionName){
        MongoCollection collection = getDBCollection(collectionName);
        collection.insertOne(t);
        return t;
    }

    public <T> T findOne(T t,String collectionName){
        MongoCollection collection = getDBCollection(collectionName);
        FindIterable iterable = collection.find(and(gt("id", 100), eq("name", "zhangsan")));
        MongoCursor iterator = iterable.iterator();
        T next = null;
        while(iterator.hasNext()){
            next = (T) iterator.next();
        }

        return next;
    }













}
