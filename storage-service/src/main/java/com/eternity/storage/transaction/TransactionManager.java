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
    private BitronixTransactionManager btm;
    private final String serverPwd = "Root123456!";

    public TransactionManager setUp(){
        TransactionManagerServices.getConfiguration().setGracefulShutdownInterval(1);
        TransactionManagerServices.getConfiguration().setExceptionAnalyzer(DefaultExceptionAnalyzer.class.getName());
        btm = TransactionManagerServices.getTransactionManager();
        return this;
    }


    public Transaction getTransaction() throws SystemException {
        Transaction transaction = btm.getTransaction();
        Assert.assertNull(transaction);
        return transaction;
    }

    public void start(Action func){
        try {
            btm.begin();
            if(func.action()){
                btm.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("================ transaction error==============");
            try {
                btm.rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
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
    public static TransactionManager transactionTest(){
        TransactionManager tm = new TransactionManager();
        tm.setUp();
//        tm.start(()->{
//            System.out.println(tm);
//            String serverPwd = "Root123456!";
//            tm.datasourceTest("jdbc:mysql://123.57.213.106/test","root",serverPwd,"test","3306","jdbc/ds3","insert into user value (18,'lisi',20)");
////            crash(5 * 1000);
//            tm.datasourceTest("jdbc:mysql://127.0.0.1/test","root","root","test","3306","jdbc/ds2","insert into user value(12,'zhangsan',10)");
//            return true;
//        });
        tm.start();
//        tm.execute();
        return tm;
    }

    /***
     * btm commit
     */
    public void start(){
        String serverPwd = "Root123456!";
        UserTransaction ut = btm;
        try {
            ut.begin();
            datasourceTest("jdbc:mysql://123.57.213.106/test","root",serverPwd,"test","3306","jdbc/ds3","insert into user value (28,'lisi',20)");
            datasourceTest("jdbc:mysql://127.0.0.1/test","root","root","test","3306","jdbc/ds2","insert into user value(12,'zhangsan',10)");
            ut.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("================ transaction error==============");
            try {
                ut.rollback();
            } catch (SystemException e1) {
                e1.printStackTrace();
            }
        }

    }


    /**
     * 连接提交tx
     */
    public void execute(){
        try {
            datasourceTest("jdbc:mysql://123.57.213.106/test","root",serverPwd,"test","3306","jdbc/ds3","insert into user value (27,'lisi',20)");
            datasourceTest("jdbc:mysql://127.0.0.1/test","root","root","test","3306","jdbc/ds2","insert into user value(12,'zhangsan',10)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * jndi 查找datasource 需要配置参数 tomcat配置的数据源
     *
     */
//    public static void test(){
//        try {
//            Context ctx = new InitialContext();
//            javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup("jdbc/ds1");
//            ds.getConnection().commit();
//        } catch (NamingException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }


    public static void main(String args[]){
       transactionTest();
    }

    public static void crash(long time){
         log.info("sleep before");
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("sleep after");
    }

    public PoolingDataSource datasourceTest(String url,String user,String pwd,String databaseName,String port,String uniqueName,String sql) throws SQLException {
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
        connection.setAutoCommit(false);
        try{
            Statement statement = connection.createStatement();
            statement.execute(sql);
//            connection.commit();
        }finally {
            connection.close();
            log.info("connection close...");
        }
        return pds;
    }






}
