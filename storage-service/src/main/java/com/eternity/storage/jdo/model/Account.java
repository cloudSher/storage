package com.eternity.storage.jdo.model;

import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.PersistenceCapable;

/**
 * Created by Administrator on 2016/8/29.
 */
//@PersistenceCapable
public class Account {

    private String id;
    private String name;

//    @Embedded
    private Loggin loggin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Loggin getLoggin() {
        return loggin;
    }

    public void setLoggin(Loggin loggin) {
        this.loggin = loggin;
    }
}
