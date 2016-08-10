package com.eternity.storage.repository;

import java.util.List;

/**
 * Created by cloudsher on 2016/7/29.
 */
public interface StandardCrudRepository extends Repository {

    List findAll();

    List find(String query,String filter,String optional);

    int insert(String document,String optional);

    int update(String query,String update,String optional);

    int remove(String query,String optional);

}
