package com.eternity.storage.util;

/**
 * Created by Administrator on 2016/9/28.
 */
public class Assert {


    public static void assertNull(Object obj){
        if(obj == null)
            throw new IllegalArgumentException("request paramater must not be null");
    }


}
