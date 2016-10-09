package com.eternity.storage.transaction;

import bitronix.tm.recovery.IncrementalRecoverer;
import bitronix.tm.recovery.Recoverer;
import bitronix.tm.recovery.RecoveryException;
import bitronix.tm.resource.jdbc.PoolingDataSource;

/**
 * Created by Administrator on 2016/9/28.
 *
 *  基于btm的poolingDataSource 实现多种数据源
 */
public class DataSource {

    private PoolingDataSource pds;
    private String url = "jdbc:mysql://127.0.0.1/test";
    private String user;
    private String passwd;
    private String serverName = "127.0.0.1";
    private String databaseName;
    private String port;
    private String uniqueName;



    public DataSource(String uniqueName){
        this.uniqueName = uniqueName;
    }

    public void init(){
        if(pds != null)
            return ;
        pds = new PoolingDataSource();
        pds.setAllowLocalTransactions(true);
        mysqlSetUp();
        pds.setMinPoolSize(2);
        pds.setMaxPoolSize(32);
        pds.setConnectionTestTimeout(30);
        pds.init();
        try {
            IncrementalRecoverer.recover(pds);
        } catch (RecoveryException e) {
            e.printStackTrace();
        }
        Recoverer recoverer = new Recoverer();
        recoverer.run();
    }


    public PoolingDataSource getDataSource(){
        return pds;
    }

    public String driverName(String type){
        String name = "";
        switch (type){
            case "mysql":
                name = "com.mysql.jdbc.jdbc2.optional.MysqlXADataSource";
                break;
            case "oracle":
                name = "";
        }
        return name;
    }


    /**
     *  mysql configuration
     *
     * <bean id="dataSource" class="bitronix.tm.resource.jdbc.PoolingDataSource" init-method="init" destroy-method="close">
     <property name="className" value="com.mysql.jdbc.jdbc2.optional.MysqlXADataSource"/>
     <property name="uniqueName" value="jdbc/ds" />
     <property name="minPoolSize" value="2" />
     <property name="maxPoolSize" value="32" />
     <property name="acquisitionInterval" value="1" />
     <property name="acquisitionTimeout" value="15" />
     <property name="maxIdleTime" value="15" />
     <property name="useTmJoin" value="true" />
     <property name="deferConnectionRelease" value="true" />
     <property name="automaticEnlistingEnabled" value="true"/>
     <property name="allowLocalTransactions" value="true"/>
     <property name="testQuery" value="SELECT current_timestamp" />
     <property name="driverProperties">
     <props>
     <prop key="user">MyUser</prop>
     <prop key="password">password</prop>
     <prop key="databaseName">MyDB</prop>
     <prop key="serverName">127.0.0.1</prop>
     <prop key="portNumber">3306</prop>
     <prop key="url">jdbc:mysql://127.0.0.1/MyDB?autoReconnect=true</prop>
     </props>
     </property>
     </bean>
     *
     */
    public void mysqlSetUp(){
        if(pds != null){
            pds.setClassName(driverName("mysql"));
            pds.setUniqueName(uniqueName);
            pds.getDriverProperties().setProperty("user",user);
            pds.getDriverProperties().setProperty("password",passwd);
            pds.getDriverProperties().setProperty("databaseName",databaseName);
            pds.getDriverProperties().setProperty("serverName",serverName);
            pds.getDriverProperties().setProperty("portNumber",port);
            pds.getDriverProperties().setProperty("url",url);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
