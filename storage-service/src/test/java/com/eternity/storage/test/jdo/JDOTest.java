package com.eternity.storage.test.jdo;

import com.eternity.storage.jdo.model.Account;
import com.eternity.storage.jdo.model.Login;
import org.junit.Test;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.metadata.JDOMetadata;

/**
 * Created by Administrator on 2016/12/22.
 */
public class JDOTest {


    @Test
    public void test(){
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("Tutorial");
        PersistenceManager pm = pmf.getPersistenceManager();

        JDOMetadata jdoMetadata = pmf.newMetadata();

        Account account = new Account();
        account.setId("111");
        account.setName("account");
        Login login = new Login();
        login.setLogin("222");
        login.setPassword("333");
        account.setLogin(login);
        pm.makePersistent(account);

        Query<Account> query = pm.newQuery(Account.class);
        query.executeList();
    }


}
