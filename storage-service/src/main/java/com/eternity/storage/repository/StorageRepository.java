package com.eternity.storage.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

/**
 * Created by cloudsher on 2016/7/29.
 */
public interface StorageRepository<T,K extends Serializable> extends PageRepository , CrudRepository<T,K>,QueryDslPredicateExecutor<T> {



}
