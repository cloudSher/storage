package com.eternity.storage.repositories.impl.travel;

/**
 * Created by cloudsher on 2016/8/10.
 */
public interface SeriesRepository<T> {


    <S extends T> S save(S s);

}
