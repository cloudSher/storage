package com.eternity.storage.test;

import com.eternity.storage.config.mongodb.MongoConfiguration;
import com.eternity.storage.test.config.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import static java.lang.System.out;

/**
 * Created by Administrator on 2016/8/11.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
public class MongoConfigurationTest {

    @Resource
    MongoConfiguration mongoConfig;

    @Test
    public void test_mongoConfig(){
        out.println(mongoConfig.getMongo());
    }
}
