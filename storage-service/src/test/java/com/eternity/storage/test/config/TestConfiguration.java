package com.eternity.storage.test.config;

import com.eternity.storage.config.mongodb.MongoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2016/8/11.
 */
@Configuration
public class TestConfiguration {

    @Bean
    public MongoConfiguration getMongoConfiguration(){
        return new MongoConfiguration();
    }
}
