package com.eternity.storage.core.query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
public class Criteria {

    private final Object NOT_SET = new Object();
    private List<Criteria> criteriaChain;
    private String key;
    private Object value;
    private Object[] obj;
    private OperateType type;


    public Criteria(){
        this.value = NOT_SET;
        this.criteriaChain = new ArrayList<>();
    }

    public Criteria(String key,Object value){
        this.value = value;
        this.key = key;
    }


    public Criteria(String key,Object... value){
        this.obj = value;
        this.key = key;
    }

    public Criteria(List<Criteria> criterias){
        if(criterias == null || criterias.size() == 0){
            throw new IllegalArgumentException("when criteria init,the criteria chain  must not be null");
        }
        this.criteriaChain = criterias;
    }

    public static Criteria eq(String field, Object value){
        notNull(field,value);
        return new Criteria(field,value).setType(OperateType.EQ);
    }

    public static Criteria ne(String name, Object val){
        notNull(name,val);
        return new Criteria(name,val).setType(OperateType.NE);
    }

    public static Criteria lt(String fileName, Object val){
        notNull(fileName,val);
        return new Criteria(fileName,val).setType(OperateType.LT);
    }

    public static Criteria lte(String name, Object val){
        notNull(name,val);
        return new Criteria(name,val).setType(OperateType.LTE);
    }

    public static Criteria gt(String name, Object obj){
        notNull(name,obj);
        return new Criteria(name,obj).setType(OperateType.GT);
    }

    public static Criteria gte(String name, Object val){
        notNull(name,val);
        return new Criteria(name,val).setType(OperateType.GTE);
    }

    public static Criteria in(String name, Object ... val){
        notNull(name,val);
        return new Criteria(name,val).setType(OperateType.IN);
    }

    public static Criteria nin(String name, Object... val){
        notNull(name,val);
        return new Criteria(name,val).setType(OperateType.NIN);
    }


    private static void notNull(Object ... obj){
        if(obj == null){
            throw new IllegalArgumentException("request parameter must not null");
        }
        for(Object o : obj){
            if(o == null){
                throw new IllegalArgumentException("request parameter is null");
            }
            if(o instanceof String){
                if(((String) o).isEmpty()){
                    throw new IllegalArgumentException("request String parameter must not be null");
                }
            }
        }
    }

    public static Criteria and(Criteria... criterias){
        return conditional("and",criterias);
    }

    public static Criteria or(Criteria... criterias){
        return conditional("or",criterias);
    }

    public static Criteria conditional(String cmd, Criteria... criterias){
        OperateType type = null;
        if("and".equals(cmd)){
            type = OperateType.AND;
        }else if("or".equals(cmd)){
            type = OperateType.OR;
        }
        List<Criteria> criteriaList = new ArrayList<>(criterias.length);
        for(Criteria criteria : criterias){
            criteriaList.add(criteria);
        }
        Criteria criteria = new Criteria(criteriaList);
        criteria.setType(type);
        return criteria;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return this.value;
    }

    public Object[] getObj() {
        return obj;
    }

    public OperateType getType() {
        return type;
    }

    public Criteria setType(OperateType type){
        this.type = type;
        return this;
    }

    public List<Criteria> getCriteriaChain(){
        return this.criteriaChain;
    }


    enum OperateType{
        AND,
        OR,
        EQ,
        NE,         //NotEqual
        LT,         //小于
        LTE,        //小于等于
        GT,         //大于
        GTE,        //大于等于
        LIKE,        //like
        IN,
        NIN
    }

}
