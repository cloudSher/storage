package com.eternity.storage.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by cloudsher on 2016/8/10.
 */
public class PropertiesProvider implements ConfigurationProvider {

    private String path;
    private Map<String,Object> propMap = new HashMap<>();
    private Map<String,Object> pathMap = new HashMap<>();
    private Map<String,Object> configMap = new HashMap<>();

    public void loadConfig(String path) throws IOException {
        if(path == null || path.isEmpty()){
            path = "";
        }
        ClassLoader loader = classLoader();
        InputStream stream = loader.getResourceAsStream(path);
        Properties prop = new Properties();
        prop.load(stream);
        propMap.put(path,prop);
    }


    public ClassLoader classLoader(){
        return Thread.currentThread().getContextClassLoader();
    }


    /**
     * 加载目录方法实现
     * @param path
     */
    public void loadFile(String path){

    }


    public Map getConfigMap(String path){
        try {
            loadConfig(path);
            Properties prop = (Properties) propMap.get(path);
            if(prop != null){
                prop2Map(prop);
                configMap.put(path,pathMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (Map) configMap.get(path);
    }

    public void prop2Map(Properties prop){
        Set<String> names = prop.stringPropertyNames();
        Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()){
            String name = iterator.next();
            pathMap.put(name,prop.getProperty(name));
        }
    }







}
