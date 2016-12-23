package com.eternity.storage.jdo;

import com.eternity.storage.jdo.model.Account;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.annotations.*;
import javax.jdo.metadata.*;
import java.util.Properties;

/**
 * Created by Administrator on 2016/12/22.
 */
public class JDOContext {


    private PersistenceManager manager;
    private PersistenceManagerFactory factory;


    /**
     * 获取managerFactory
     * @return
     */
    public PersistenceManagerFactory getPersistenceManagerFactory(String unit){
        if(unit != null)
            this.factory = JDOHelper.getPersistenceManagerFactory(unit);
        else{
            this.factory = JDOHelper.getPersistenceManagerFactory(buildProp());
        }
        return factory;
    }

    public Properties buildProp(){
        Properties prop = new Properties();
//        prop.setProperty("javax.jdo.PersistenceManagerFactoryClass","org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
        prop.setProperty("javax.jdo.option.ConnectionDriverName","com.mysql.jdbc.Driver");
        prop.setProperty("javax.jdo.option.ConnectionURL","jdbc:mysql://localhost:3306/test");
        prop.setProperty("javax.jdo.option.ConnectionUserName","root");
        prop.setProperty("javax.jdo.option.ConnectionPassword","root");
        prop.setProperty("javax.jdo.option.Mapping","mysql");
        prop.setProperty("datanucleus.autoCreateSchema","true");
        return prop;
    }


    /**
     * 获取manager
     * @return
     */
    public PersistenceManager getPersistenceManager(){
        return factory.getPersistenceManager();
    }


    public JDOMetadata getJDOMetadata(){
        return factory.newMetadata();
    }

    /**
     * 构建元数据信息
     */
    public void buildMetadata(Class clazz){
        JDOMetadata md = getJDOMetadata();
        ClassMetadata cmd = md.newClassMetadata(clazz);
        cmd.setTable("account").setDetachable(true).setIdentityType(IdentityType.DATASTORE);
        cmd.setPersistenceModifier(ClassPersistenceModifier.PERSISTENCE_CAPABLE);

        InheritanceMetadata inhmd = cmd.newInheritanceMetadata();
        inhmd.setStrategy(InheritanceStrategy.NEW_TABLE);
        DiscriminatorMetadata dmd = inhmd.newDiscriminatorMetadata();
        dmd.setColumn("disc").setValue("Client");
        dmd.setStrategy(DiscriminatorStrategy.VALUE_MAP).setIndexed(Indexed.TRUE);

        VersionMetadata vermd = cmd.newVersionMetadata();
        vermd.setStrategy(VersionStrategy.VERSION_NUMBER);
        vermd.setColumn("version").setIndexed(Indexed.TRUE);

        FieldMetadata fmd = cmd.newFieldMetadata("name");
        fmd.setNullValue(NullValue.DEFAULT).setColumn("client_name");
        fmd.setIndexed(true).setUnique(true);
        factory.registerMetadata(md);
    }


    public static void main(String args[]){
        JDOContext ctx = new JDOContext();
        ctx.getPersistenceManagerFactory(null);
        ctx.buildMetadata(Account.class);
        PersistenceManager pm = ctx.getPersistenceManager();
        Account account = new Account();
        account.setId("1");
        account.setName("zhangsan");
        pm.makePersistent(account);
    }
}
