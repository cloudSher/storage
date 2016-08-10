package com.eternity.storage.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by cloudsher on 2016/8/3.
 */
public class ConfigurationContext {

    private final Map<String,Object> context = new HashMap<>();
    private ConfigurationProvider provieder;


    public ConfigurationContext(){

    }

    public void setConfigMap(Map map){
        if(map != null){
            Set keySet = map.keySet();
            Iterator iterator = keySet.iterator();
            while (iterator.hasNext()){
                String name = String.valueOf(iterator.next());
                context.put(name,map.get(name));
            }
        }
    }


    public String getString(String name){
        if(name !=null && !name.isEmpty()){
            return String.valueOf(context.get(name));
        }
        return null;
    }


    public void setAttribute(String name,Object value){
        if(value != null){
            context.put(name,value);
        }
    }

    public void setProvieder(ConfigurationProvider provieder){
        this.provieder = provieder;
    }

    public ConfigurationProvider getProvieder() {
        return provieder;
    }
}
