package com.eternity.storage.jdo;

import com.eternity.storage.jdo.model.Account;
import com.eternity.storage.jdo.model.Loggin;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * Created by Administrator on 2016/8/29.
 */
public class Main {


    public static void main(String args[]){
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Tutorial");
        PersistenceManager pm = pmf.getPersistenceManager();

        Account  account = new Account();
        account.setId("111");
        account.setName("account");
        Loggin loggin = new Loggin();
        loggin.setLogin("222");
        loggin.setPassword("333");
        account.setLoggin(loggin);
        pm.makePersistent(account);
    }
}
