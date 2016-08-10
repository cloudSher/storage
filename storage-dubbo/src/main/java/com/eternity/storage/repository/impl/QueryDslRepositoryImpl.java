package com.eternity.storage.repository.impl;

import com.eternity.storage.repository.QueryDslRepository;
import com.mongodb.Mongo;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.mongodb.morphia.MorphiaQuery;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.awt.print.Pageable;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by cloudsher on 2016/8/2.
 */
public class QueryDslRepositoryImpl implements QueryDslRepository {

    private static  Datastore ds;
    private static Morphia mor;

    @Override
    public Object findOne(Predicate var) {
        return null;
    }

    @Override
    public Iterable findAll(Predicate var) {
        return null;
    }

    @Override
    public Iterable findAll(com.querydsl.core.types.Predicate var1, OrderSpecifier[] var2) {
        return null;
    }

    @Override
    public Iterable findAll(OrderSpecifier[] var1) {
        return null;
    }

    @Override
    public Object findAll(com.querydsl.core.types.Predicate var1, Pageable var2) {
        return null;
    }

    @Override
    public long count(com.querydsl.core.types.Predicate var1) {
        return 0;
    }

    @Override
    public boolean exists(com.querydsl.core.types.Predicate var1) {
        return false;
    }

    static {
        try {
            Mongo mongo = new Mongo();
            mor = new Morphia();
            ds = mor.createDatastore(mongo,"customer");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String args[]){
////        ds.save(new User("123","zhangsan"));
////        ds.save(new User("124","lisi"));
//        QUser user = new QUser("user");
//        MorphiaQuery query = new MorphiaQuery<User>(mor, ds, user);
//        System.out.println(query.where(user.name.eq("zhangsan")).toString());
//        List<User> users = query.where(user.name.eq("zhangsan")).fetch();
//        System.out.println(users.get(0).getName());
//    }
}
