package com.eternity.storage.jdo.model;

import javax.jdo.annotations.PersistenceCapable;

/**
 * Created by Administrator on 2016/8/29.
 */
//@PersistenceCapable
public class Loggin {

    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
