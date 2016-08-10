package com.eternity.storage.repository;

import com.eternity.storage.driver.Driver;

import java.io.Serializable;

/**
 * Created by cloudsher on 2016/8/10.
 */
public abstract class AbstractStorageRepository<T,ID extends Serializable> implements StorageRepository<T,ID> {

    private Driver driver;


}
