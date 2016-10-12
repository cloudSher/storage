package com.eternity.storage.transaction;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import bitronix.tm.recovery.IncrementalRecoverer;
import bitronix.tm.recovery.Recoverer;
import bitronix.tm.recovery.RecoveryException;
import bitronix.tm.resource.jdbc.PoolingDataSource;
import bitronix.tm.utils.DefaultExceptionAnalyzer;
import com.eternity.storage.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Administrator on 2016/9/28.
 */
public class TransactionManager {

    private static Logger log = LoggerFactory.getLogger(TransactionManager.class);
    private static BitronixTransactionManager btm;
    private static final String serverPwd = "Root123456!";

    public static void setUp(){
        TransactionManagerServices.getConfiguration().setGracefulShutdownInterval(1);
        TransactionManagerServices.getConfiguration().setExceptionAnalyzer(DefaultExceptionAnalyzer.class.getName());
        btm = TransactionManagerServices.getTransactionManager();
    }


    public Transaction getTransaction() throws SystemException {
        Transaction transaction = btm.getTransaction();
        Assert.assertNull(transaction);
        return transaction;
    }

    public void start(Action func){
        try {
            btm.begin();
            func.action();
            btm.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                btm.rollback();
            } catch (SystemException e) {
                e.printStackTrace();
            }
        }
    }

    public void rollback(){
        try {
            btm.rollback();
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    interface Action{
        boolean action() throws SQLException;
    }

    /***
     * 分布式事务提交，多数据源测试
     */
    public static void transactionTest(){
        try {
            setUp();

            btm.begin();
            btm.setTransactionTimeout(1000);

            execute();

            btm.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                btm.rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        }
    }

    /***
     * btm commit
     */
    public static void execute(){
        try {
            datasourceTest("jdbc:mysql://123.57.213.106/test","root",serverPwd,"test","3306","jdbc/ds3","insert into user value (34,'lisi',20)");
            datasourceTest("jdbc:mysql://127.0.0.1/test","root","root","test","3306","jdbc/ds2","insert into user value(12,'zhangsan',10)");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("================execute sql error==============");
        }

    }

    public static PoolingDataSource datasourceTest(String url,String user,String pwd,String databaseName,String port,String uniqueName,String sql) throws SQLException {
        log.info("start create datasource ... execute sql task, if return successful,close connection");
        DataSource dataSource = new DataSource(uniqueName);
        dataSource.setDatabaseName(databaseName);
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPasswd(pwd);
        dataSource.setPort(port);
        dataSource.init();
        PoolingDataSource pds = dataSource.getDataSource();
        Connection connection = pds.getConnection();
        Statement statement = null;
        try{
            statement = connection.createStatement();
            statement.execute(sql);
        }finally {
            if(statement != null)
                statement.close();
        }
        return pds;
    }


    public static void main(String args[]){
        transactionTest();
    }







}
