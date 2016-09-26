package com.eternity.storage.repository.impl;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 *  jdo template
 */
public class StorageTemplate<T> {

    private PersistenceManagerFactory factory;

    private PersistenceManager manager;

    public StorageTemplate(PersistenceManagerFactory factory){
        this.factory = factory;
        getManager();
    }

    private PersistenceManager getManager(){
        if(factory == null){
            throw new NullPointerException("factory is not null");
        }
        manager = factory.getPersistenceManager();
        if(manager == null){
            throw new NullPointerException("manager is not null");
        }
        return manager;
    }


    public T save(T t){
        Transaction transaction = manager.currentTransaction();
        try{
            transaction.begin();
            manager.makePersistent(t);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }

        return t;
    }

    public List<T> findByQuery(String sql){
        PersistenceManager pm = getManager();
        List<T> list = null;
        try{
            Query query = pm.newQuery(sql);
            list = (List<T>) query.execute();
        }finally {
            pm.close();
        }
        return list;
    }

    public T delete(T t){
        PersistenceManager manager = getManager();
        try{
            Query<?> query = manager.newQuery(t.getClass());
            query.deletePersistentAll();
        }
        finally{
            manager.close();
        }
        return t;
    }





}
