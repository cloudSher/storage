package com.eternity.storage.jdo.model;

/**
 * Created by Administrator on 2016/8/29.
 */
//@PersistenceCapable
public class Account {

    private String id;
    private String name;

//    @Embedded
    private Login login;

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

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
