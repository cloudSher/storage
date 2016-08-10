package com.eternity.storage.repositories.impl;

import com.eternity.storage.api.dto.User;
import com.eternity.storage.driver.mongodb.spring.StandardMongoTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by cloudsher on 2016/8/8.
 */

public class UserRepositoryImpl {


    StandardMongoTemplate mongoTemplate;


    List<User> findAll(){
//        mongoTemplate.findAll();
        return null;
    }

}
