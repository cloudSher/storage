package com.eternity.storage.config.sql;

import com.eternity.storage.config.ConfigurationContext;
import com.jolbox.bonecp.BoneCPDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Created by cloudsher on 2016/8/2.
 */
@Configuration
@PropertySource({"classpath:properties/jdbc.properties"})
public class JdbcConfiguration implements com.eternity.storage.config.Configuration {

   @Autowired Environment env;

  @Bean
   public DataSource getDataSource(){
     BoneCPDataSource dataSource = new BoneCPDataSource();
     dataSource.setDriverClass(env.getProperty("jdbc.driver"));
     dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
     dataSource.setUser(env.getProperty("jdbc.user"));
     dataSource.setPassword(env.getProperty("jdbc.password"));
     return dataSource;
  }


    @Override
    public ConfigurationContext getConfigurationContext() {
        return null;
    }
}
