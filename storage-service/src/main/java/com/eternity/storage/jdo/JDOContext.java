package com.eternity.storage.jdo;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.metadata.JDOMetadata;

/**
 * Created by Administrator on 2016/12/22.
 */
public class JDOContext {


    /**
     * 获取managerFactory
     * @return
     */
    public PersistenceManagerFactory getPersistenceManagerFactory(){
        PersistenceManagerFactory tutorial = JDOHelper.getPersistenceManagerFactory("Tutorial");
        return tutorial;
    }

    /**
     * 获取manager
     * @return
     */
    public PersistenceManager getPersistenceManager(){
        return getPersistenceManagerFactory().getPersistenceManager();
    }


    public JDOMetadata getJDOMetadata(){
        return null;
    }
}
