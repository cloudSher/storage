package com.eternity.storage.transaction;

import com.atomikos.icatch.*;
import com.atomikos.icatch.config.UserTransactionService;
import com.atomikos.icatch.config.UserTransactionServiceImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.icatch.jta.UserTransactionServer;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jdbc.AtomikosSQLException;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.UserTransaction;
import javax.transaction.xa.XAResource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016/10/11.
 */
public class AtomikosTransaction {

    private final static String mysqlClassName = "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource";
    private final static String serverPwd = "Root123456!";

    public static AtomikosDataSourceBean dataSourceBean(String user,String pwd,String url,String uniqueResourceName){
        AtomikosDataSourceBean dsb = new AtomikosDataSourceBean();
        dsb.setMaxPoolSize(32);
        dsb.setMinPoolSize(2);
        dsb.setXaDataSourceClassName(mysqlClassName);
        dsb.getXaProperties().setProperty("user",user);
        dsb.getXaProperties().setProperty("password",pwd);
        dsb.getXaProperties().setProperty("url",url);
        dsb.setUniqueResourceName(uniqueResourceName);
        dsb.setMaxIdleTime(2);
        try {
            dsb.setLoginTimeout(100);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            dsb.init();
        } catch (AtomikosSQLException e) {
            e.printStackTrace();
        }
        return dsb;
    }


    /****
     * 执行单个transaction
     */
    public static void transaction(){
        UserTransactionManager utx = new UserTransactionManager();
        try {
            utx.setTransactionTimeout(3000);
            utx.init();
        } catch (SystemException e) {
            e.printStackTrace();
        }
        boolean rollback = false;
        try {
            utx.begin();
//            Transaction tx = utx.getTransaction();

//            executeDataSource();
//            executeXADataSource(utx);

        } catch (Exception e) {
            e.printStackTrace();
            rollback = true;
        }finally {
            try {
                if(rollback){
                    utx.rollback();
                }else{
                    utx.commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("===========  utx rollback ============");
            }
        }

    }

    public static void executeDataSource(){
        executeSQL(getConnection("root", serverPwd, "jdbc:mysql://123.57.213.106/test", "jdbc1"),"insert into user value (28,'lisi',20)");

        executeSQL(getConnection("root", "root", "jdbc:mysql://127.0.0.1/test", "jdbc2"),"insert into user value(12,'zhangsan',10)");
    }


    public static void compositeionTm(UserTransaction utx){
        CompositeTransactionManager ctm = getCompositeTransactionManager();
        CompositeTransaction ct = ctm.getCompositeTransaction(utx.toString());
        if(ct == null){
            System.out.println("======composite tx is null");
        }
        boolean error = false;
        //重新启动事务
        ctm.resume(ct);
        Connection cons = null;
        try {
            cons = executeSQLNoClose();


            //开启另外数据源事务
            withAdd();

        } catch (SQLException e) {
            error = true;
            e.printStackTrace();
        }finally {
            try {
                closeConnection(cons,error);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    utx.setRollbackOnly();
                } catch (SystemException e1) {
                    e1.printStackTrace();
                }
            }finally {
                ctm.suspend();
            }
        }


    }


    public static Connection executeSQLNoClose() throws SQLException {
        Connection con1 = getConnection("root", serverPwd, "jdbc:mysql://123.57.213.106/test", "jdbc1");
        con1.createStatement().execute("insert into user value (28,'lisi',20)");

        return con1;
    }

    public static void withAdd() throws SQLException {
        Connection con2 = getConnection("root", "root", "jdbc:mysql://127.0.0.1/test", "jdbc2");
        con2.createStatement().execute("insert into user value(12,'zhangsan',10)");
    }

    public static CompositeTransactionManager getCompositeTransactionManager(){
        UserTransactionService uts = new UserTransactionServiceImp();
        uts.init();
        return uts.getCompositeTransactionManager();
    }

    /**
     * xa数据源
     * @param utx
     */
    public static void executeXADataSource(UserTransactionManager utx){
        XAConnection xaConnection1 = xaConnection("root", serverPwd, "jdbc:mysql://123.57.213.106/test", "jdbc1");
        XAConnection xaConnection2 = xaConnection("root", "root", "jdbc:mysql://127.0.0.1/test", "jdbc2");
        Transaction tx;
        try {
            tx = utx.getTransaction();
            //加载xaResource
            tx.enlistResource(xaConnection2.getXAResource());
            tx.enlistResource(xaConnection1.getXAResource());

            executeSQL(xaConnection1.getConnection(),"insert into user value (33,'lisi',20)");

            executeSQL(xaConnection2.getConnection(),"insert into user value(12,'zhangsan',10)");


            tx.delistResource(xaConnection2.getXAResource(), XAResource.TMSUCCESS);
            tx.delistResource(xaConnection1.getXAResource(),XAResource.TMSUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(String user,String pwd,String url,String unique){
        AtomikosDataSourceBean sourceBean = dataSourceBean(user,pwd,url,unique);
        Connection con = null;
        try {
            con = sourceBean.getConnection();
            UserTransactionManager utx = new UserTransactionManager();
            utx.begin();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return con;

    }

    /**
     * xa
     * @param user
     * @param pwd
     * @param url
     * @param unique
     * @return
     */
    public static XAConnection xaConnection(String user,String pwd,String url,String unique){
        AtomikosDataSourceBean sourceBean = dataSourceBean(user,pwd,url,unique);
        XADataSource xaDataSource = sourceBean.getXaDataSource();
        XAConnection xaConnection = null;
        try {
             xaConnection = xaDataSource.getXAConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return xaConnection;
    }


    public static void executeSQL(Connection con,String sql){
        boolean error = false;
        try {
            con.createStatement().execute(sql);
            con.close();
        } catch (SQLException e) {
            error = true;
            e.printStackTrace();
        }finally {
            try {
                closeConnection(con,error);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static UserTransactionManager getTransaction(){
        return new UserTransactionManager();
    }

    public static void closeConnection(Connection con,boolean error) throws Exception {
        if(con != null) con.close();
        UserTransactionManager tm = getTransaction();
        Transaction tx = tm.getTransaction();
        if(tx != null){
            if (error){
                tx.rollback();
            }else{
                tx.commit();
            }
        }

    }



    public static void main(String args[]){
        transaction();
    }

}
