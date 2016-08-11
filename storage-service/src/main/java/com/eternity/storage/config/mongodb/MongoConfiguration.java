package com.eternity.storage.config.mongodb;

import com.eternity.storage.config.ConfigurationContext;
import com.eternity.storage.config.ConfigurationProvider;
import com.eternity.storage.config.PropertiesProvider;
import com.mongodb.Mongo;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.search.suggest.context.ContextMapping;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.Provider;

/**
 * Created by cloudsher on 2016/8/3.
 */
public class MongoConfiguration implements com.eternity.storage.config.Configuration{

    private ConfigurationContext context;
    private Mongo mongo;
    private Morphia morphia;
    private String dbName;

    private final static String mongoConfigPath = "properties/mongodb.properties";

    public void setDBName(String dbName){
        this.dbName = dbName;
    }

    public MongoConfiguration(){
        initContext();
        init();
        this.dbName = "admin";
    }

    public void init(){
        String host = context.getString("mongo.db.host");
        System.out.println("======="+host);
        try {
            mongo = new Mongo(host);
            morphia = new Morphia();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void initContext(){
        if(context==null)
            context = new ConfigurationContext();
        ConfigurationProvider provider = context.getProvieder();
        if(provider == null){
            provider = new PropertiesProvider();
        }
        context.setConfigMap(provider.getConfigMap(mongoConfigPath));
    }


    public Datastore getDataStore(){
        if(mongo == null){
            throw new NullPointerException("mongo is not null");
        }
        if(dbName == null){
            throw new NullPointerException("mongodb dbname is not null");
        }
        Datastore datastore = morphia.createDatastore(mongo, dbName);
        return datastore;
    }

    public Mongo getMongo(){
        return this.mongo;
    }

    public String getDbName() {
        return this.dbName;
    }

    public Morphia getMorphia(){
        return this.morphia;
    }


    @Override
    public ConfigurationContext getConfigurationContext() {
        return context;
    }

}
