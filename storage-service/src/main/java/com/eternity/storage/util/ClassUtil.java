package com.eternity.storage.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/18.
 */
public class ClassUtil {


    public static Method[] getMethods(Class clazz){
        if(clazz == null){
            return null;
        }
        Method[] methods = clazz.getDeclaredMethods();
        return methods;
    }


    public static Field[] getFields(Class clazz){
        if(clazz == null){
            return null;
        }
        Field[] fields = clazz.getDeclaredFields();
        return fields;
    }

    public static Map<String,Object> getAvailableFieldValues(Object obj){
        if(obj == null){
            return null;
        }
        Class var = obj.getClass();
        Map<String,Object> result = null;
        Field[] fields = getFields(var);
        if(fields != null){
            result = new HashMap<>(fields.length);
            for (Field field : fields){
                if(field != null){
                    String fieldName = field.getName();
                    field.setAccessible(true);
                    try {
                        Object o = field.get(obj);
                        if(o != null){
                            result.put(fieldName,o);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    public static void main(String args[]){
        User user = new User();
        System.out.println(user.getA());
    }

    static class User{
        private float a;

        public float getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }



}
