package com.eternity.storage.core.query;

import java.util.List;

/**
 * Created by Administrator on 2016/12/21.
 */
public interface Operator {

    /**
     * get operator
     * @param var
     * @param <T>
     * @return
     */
    <T> T get(Class<? extends T> var);


    <T> List<T> find(Query query, Class<? extends T> t);

    <T> List<T> find(String sql, Class<? extends T> t);

}
