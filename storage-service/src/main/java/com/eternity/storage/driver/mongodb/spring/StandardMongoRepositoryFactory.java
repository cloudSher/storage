package com.eternity.storage.driver.mongodb.spring;

import com.eternity.storage.config.Configuration;
import com.eternity.storage.config.mongodb.MongoConfiguration;
import com.eternity.storage.repository.Repository;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;

/**
 * Created by cloudsher on 2016/8/4.
 */
public class StandardMongoRepositoryFactory {

    private static Configuration configuration;
    private static StandardMongoTemplate template;
    private static MongoRepositoryFactory factory;

    static{
        if(configuration == null){
            configuration = new MongoConfiguration();
        }
        MongoConfiguration mongoConfig = (MongoConfiguration) configuration;
        if(template == null){
            template = new StandardMongoTemplate(mongoConfig.getMongo(),mongoConfig.getDbName());
        }
        if(factory == null){
            factory = new MongoRepositoryFactory(template);
        }
    }

//    public StandardMongoRepositoryFactory() {
//        if(configuration == null){
//            configuration = new MongoConfiguration();
//        }
//        MongoConfiguration mongoConfig = (MongoConfiguration) configuration;
//        if(template == null){
//            template = new StandardMongoTemplate(mongoConfig.getMongo(),mongoConfig.getDbName());
//        }
//        if(factory == null){
//            factory = new MongoRepositoryFactory(template);
//        }
//    }


    public static <T extends Repository> T getRepository(Class<T> clazz){
        return  factory.getRepository(clazz);
    }


    public static StandardMongoTemplate getTemplate(){
        return template;
    }






}
