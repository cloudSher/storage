package com.eternity.storage.core.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/19.
 */
public class Select {


    private List<String> items  = new ArrayList<>();
    private SelectModel model;
    private String key;

    private Select(){};

    public void addItem(String item){
        this.items.add(item);
    }

    public Select setModel(SelectModel model){
        this.model = model;
        return this;
    }

    public Select setKey(String key){
        this.key = key;
        return this;
    }

    public SelectModel getModel() {
        return model;
    }

    public String getKey() {
        return key;
    }

    public static Select select(String... items){
        if(items.length == 0){
            throw new IllegalArgumentException("参数不能为空");
        }
        Select select = new Select();
        select.setModel(SelectModel.SIMPLE);
        for(String item : items){
            select.addItem(item);
        }
        return select;
    }

    public static Select select(SelectModel model,String key){
        if(key == null || key.trim().length() == 0){
            throw  new IllegalArgumentException("参数不能为空");
        }
        return new Select().setModel(model).setKey(key);
    }

    public List<String> getItems() {
        return items;
    }


    enum SelectModel{
        SIMPLE,
        AVG,
        COUNT,
        MIN,
        MAX,
        SUM;
    }
}
