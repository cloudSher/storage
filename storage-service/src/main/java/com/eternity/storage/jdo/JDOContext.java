package com.eternity.storage.jdo;

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

    JDOContext(){
        factory = getPersistenceManagerFactory();
        manager =  factory.getPersistenceManager();
    }

    /**
     * 获取managerFactory
     * @return
     */
    public PersistenceManagerFactory getPersistenceManagerFactory(){
        Properties prop = new Properties();
        prop.setProperty("javax.jdo.PersistenceManagerFactoryClass","jdo");
        prop.setProperty("javax.jdo.option.ConnectionDriverName","com.mysql.jdbc.Driver");
        prop.setProperty("javax.jdo.option.ConnectionURL","jdbc:mysql://localhost/test");
        prop.setProperty("javax.jdo.option.ConnectionUserName","root");
        prop.setProperty("javax.jdo.option.ConnectionPassword","root");
        PersistenceManagerFactory factory = JDOHelper.getPersistenceManagerFactory(prop);
        return factory;
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
        cmd.setTable("CLIENT").setDetachable(true).setIdentityType(IdentityType.DATASTORE);
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
}
