package com.eternity.storage.repositories.impl.travel;

import com.eternity.storage.driver.mongodb.spring.StandardMongoRepositoryFactory;
import com.eternity.storage.repository.SimpleRepository;
import com.eternity.workbench.api.model.Series;
import org.springframework.stereotype.Repository;

/**
 * Created by cloudsher on 2016/8/10.
 */
@Repository
public class SeriesRepositoryImpl extends SimpleRepository<Series,String> implements SeriesRepository<Series>{

   public SeriesRepositoryImpl() {
        super(StandardMongoRepositoryFactory.getTemplate());
    }


}
