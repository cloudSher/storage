package com.eternity.storage.core.query;

import java.util.HashMap;
import java.util.Map;

import static com.eternity.storage.core.query.Criteria.and;
import static com.eternity.storage.core.query.Criteria.eq;

/**
 * Created by Administrator on 2016/12/14.
 */
public class Query {

    private static Map<String,Object> conditions = new HashMap<String, Object>();
    private Criteria criteria;
    private boolean not_set = true;
    private int start;
    private int len;
    private Sort sort;
    private Select select;
    private String domain;


    private Query(){}

    public static Query newQuery(){
        return new Query();
    }

    public static Query where(Criteria criteria){
        Query query = newQuery();
        query.criteria = criteria;
        query.not_set = false;
        return query;
    }

    public  Query from(String domain){
        this.domain = domain;
        return this;
    }

    public Query limit(int start,int len){
        this.start = start;
        this.len = len;
        return this;
    }

    public Query order(Sort sort){
        this.sort = sort;
        return this;
    }

    public Query select(Select select){
        this.select = select;
        return this;
    }


    public static Map<String, Object> getConditions() {
        return conditions;
    }

    public static void setConditions(Map<String, Object> conditions) {
        Query.conditions = conditions;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public boolean isNot_set() {
        return not_set;
    }

    public void setNot_set(boolean not_set) {
        this.not_set = not_set;
    }

    public int getStart() {
        return start;
    }

    public int getLen() {
        return len;
    }

    public Sort getSort() {
        return sort;
    }

    public Select getSelect() {
        return select;
    }

    public String getDomain() {
        return domain;
    }

    public static void main(String args[]){
        Query.where(and(eq("id","123"))).from("");
    }





}
