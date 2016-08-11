package com.eternity.storage.api.repositories.travel;

import com.eternity.workbench.api.model.Series;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by cloudsher on 2016/8/10.
 */
public interface SeriesRepository extends MongoRepository<Series,String>,QueryDslPredicateExecutor<Series> {

}
