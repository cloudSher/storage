package com.eternity.storage.core.query;

/**
 * Created by Administrator on 2016/12/16.
 */
public interface Example {


    /**
     *
     * @param t
     * @param <T>
     * @return
     */
    <T> T create(Class<T> t) throws Exception;

}
