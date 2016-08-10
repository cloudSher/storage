package com.eternity.storage.repository;

import com.querydsl.core.types.OrderSpecifier;

import java.awt.print.Pageable;
import java.util.function.Predicate;

/**
 * Created by cloudsher on 2016/8/2.
 */

public interface QueryDslRepository<T> {

    T findOne(Predicate var);

    Iterable<T> findAll(Predicate var);

    Iterable<T> findAll(com.querydsl.core.types.Predicate var1, OrderSpecifier... var2);

    Iterable<T> findAll(OrderSpecifier... var1);

    T findAll(com.querydsl.core.types.Predicate var1, Pageable var2);

    long count(com.querydsl.core.types.Predicate var1);

    boolean exists(com.querydsl.core.types.Predicate var1);

}
