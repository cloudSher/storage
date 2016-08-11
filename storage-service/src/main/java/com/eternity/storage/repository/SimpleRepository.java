package com.eternity.storage.repository;

import com.eternity.storage.driver.Driver;
import com.eternity.storage.driver.mongodb.spring.StandardMongoTemplate;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cloudsher on 2016/8/10.
 */

public class SimpleRepository<T, ID extends Serializable> extends AbstractStorageRepository<T,ID> {

    private Driver driver;

    public SimpleRepository(Driver driver){
        Assert.notNull(driver);
        this.driver = driver;
    }

    @Override
    public <S extends T> S save(S s) {
        Assert.notNull(driver);
        StandardMongoTemplate template = (StandardMongoTemplate) driver;
        template.save(s);
        return s;
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> iterable) {
        return null;
    }

    @Override
    public T findOne(ID id) {
        return null;
    }

    @Override
    public boolean exists(ID id) {
        return false;
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(ID id) {

    }

    @Override
    public void delete(T t) {

    }

    @Override
    public void delete(Iterable<? extends T> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public T findOne(Predicate predicate) {
        return null;
    }

    @Override
    public Iterable<T> findAll(Predicate predicate) {
        return null;
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    @Override
    public Iterable<T> findAll(OrderSpecifier<?>... orderSpecifiers) {
        return null;
    }

    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        return null;
    }

    @Override
    public long count(Predicate predicate) {
        return 0;
    }

    @Override
    public boolean exists(Predicate predicate) {
        return false;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public List find(String query, String filter, String optional) {
        return null;
    }

    @Override
    public int insert(String document, String optional) {
        return 0;
    }

    @Override
    public int update(String query, String update, String optional) {
        return 0;
    }

    @Override
    public int remove(String query, String optional) {
        return 0;
    }
}
