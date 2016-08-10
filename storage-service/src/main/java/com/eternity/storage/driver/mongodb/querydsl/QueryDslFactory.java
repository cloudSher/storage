package com.eternity.storage.driver.mongodb.querydsl;

import com.eternity.storage.config.Configuration;
import com.eternity.storage.config.mongodb.MongoConfiguration;
import com.querydsl.core.types.EntityPath;
import com.querydsl.mongodb.morphia.MorphiaQuery;
import org.mongodb.morphia.Datastore;

/**
 * Created by cloudsher on 2016/8/5.
 */
public class QueryDslFactory {

    private Configuration configuration;
    private Datastore ds;           //

    public QueryDslFactory(){
        if(configuration == null){
            configuration = new MongoConfiguration();
        }
    }
    /**
     * 获取mongo数据存储对象
     * @return
     */
    public Datastore getDatastore(){
        if(configuration == null){
            configuration = new MongoConfiguration();
        }
        MongoConfiguration mongoConfig = (MongoConfiguration) configuration;
        this.ds = mongoConfig.getDataStore();
        return ds;
    }


    public MorphiaQuery query(EntityPath path){
        MongoConfiguration mongoConfig = (MongoConfiguration) configuration;
        return new MorphiaQuery(mongoConfig.getMorphia(),ds,path);
    }





}
