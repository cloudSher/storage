package com.eternity.storage.api.repositories.travel;

import java.io.Serializable;

/**
 * Created by cloudsher on 2016/8/10.
 */
public interface SeriesRepository<T,K extends Serializable> extends  org.springframework.data.repository.CrudRepository<T, K>, org.springframework.data.querydsl.QueryDslPredicateExecutor<T> {


}
