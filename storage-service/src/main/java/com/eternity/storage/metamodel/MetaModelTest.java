package com.eternity.storage.metamodel;

import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.pojo.ObjectTableDataProvider;
import org.apache.metamodel.pojo.PojoDataContext;
import org.apache.metamodel.pojo.TableDataProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
public class MetaModelTest {


    public static  void main(String args[]){
        doPojo();
    }

    public static void doExcel(String path){
        File file = new File(path);
        if(file.exists()){

        }
    }

    public static void doPojo(){
        PojoDataContext context = new PojoDataContext(Arrays.asList(provider()));
        DataSet dataSet = context.query().from("persons").select("name").where("name").eq("zhangsan").execute();
        while (dataSet.next()){
            Row row = dataSet.getRow();
            String name = (String) row.getValue(0);
            System.out.println(name);
        }
        dataSet.close();
    }

    public static TableDataProvider<Person> provider(){
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("zhangsan",20));
        persons.add(new Person("zhangsan",10));
        TableDataProvider<Person> provider = new ObjectTableDataProvider("persons",Person.class,persons);
        return provider;
    }


   public static class Person{
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
