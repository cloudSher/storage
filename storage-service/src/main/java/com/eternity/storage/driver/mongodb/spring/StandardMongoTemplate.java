package com.eternity.storage.driver.mongodb.spring;

import com.eternity.storage.config.Configuration;
import com.eternity.storage.config.mongodb.MongoConfiguration;
import com.eternity.storage.driver.Driver;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by cloudsher on 2016/8/3.
 */
public class StandardMongoTemplate extends MongoTemplate implements Driver{

    @Autowired
    private Configuration configuration;

    public StandardMongoTemplate(Mongo mongo, String databaseName) {
        super(mongo,databaseName);
    }






}
