package com.eternity.storage.repository;

import com.eternity.storage.core.query.Query;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public interface StorageRepository<T,ID extends Serializable>{

    <S extends T> S insert(S s);


    T findOne(T t);

    T findById(ID id, Class<T> entityClass);


    List<T> findAll(Class<T> entityClass);


    boolean delete(T t);

    <S extends T> S save(S s);

    public <S extends T> S update(Query query, S s);

    <T>List<T> findByQuery(Query query, Class<T> var);

    List<T> findByJsonQuery(String query, Class<T> var);
}
