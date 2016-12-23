package com.eternity.storage.core.query;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/14.
 */
public class Criteria {

    private List<Criteria> orderCriteria;
    private Criterion criterion;
    private Operator type;
    private boolean isComposite = false;

    private Criteria(){
    }

    public Criterion getCriterion() {
        return criterion;
    }

    public Operator getType() {
        return type;
    }

    public boolean isComposite() {
        return isComposite;
    }

    public List<Criteria> getOrderCriteria(){
        return orderCriteria;
    }

    public static Criteria newCriteria(){
        return new Criteria();
    }


    public static Criteria and(Criteria... criterions){
        return handler("and",criterions);
    }


    public static Criteria or(Criteria... criterions){
        return handler("or",criterions);
    }

    public static Criterion newCriterion(String key,Operator type,Object value){
        return new Criterion(key,type,value,null);
    }

    public static Criterion newCriterion(String key,Operator type,Object value,Object secondVal){
        return new Criterion(key,type,value,secondVal);
    }

    public static Criteria addCriterion(Criterion criterion){
        Criteria criteria = newCriteria();
        criteria.criterion = criterion;
        return criteria;
    }

    public static Criteria eq(String key,Object value){
        notNull(key,value);
        Criterion criterion = newCriterion(key, Operator.EQ, value);
        return addCriterion(criterion);
    }

    public static Criteria ne(String name,Object val){
        notNull(name,val);
        return addCriterion(newCriterion(name, Operator.NE,val));
    }

    public static Criteria lt(String name,Object val){
        notNull(name,val);
        return addCriterion(newCriterion(name, Operator.LT,val));
    }

    public static Criteria lte(String name,Object val){
        notNull(name,val);
        return addCriterion(newCriterion(name, Operator.LTE,val));
    }

    public static Criteria gt(String name,Object val){
        notNull(name,val);
        return addCriterion(newCriterion(name, Operator.GT,val));
    }

    public static Criteria gte(String name,Object val){
        notNull(name,val);
        return addCriterion(newCriterion(name, Operator.GTE,val));
    }

    public static Criteria in(String name,Object ... val){
        notNull(name,val);
        return addCriterion(newCriterion(name, Operator.IN,val));
    }

    public static Criteria nin(String name,Object... val){
        notNull(name,val);
        return addCriterion(newCriterion(name, Operator.NE,val));
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



    public static Criteria handler(String cmd,Criteria ... criterias){
        if(criterias.length <= 1){
            throw new IllegalArgumentException("参数个数最少大于1");
        }
        Criteria c = newCriteria();
        c.orderCriteria = Arrays.asList(criterias);
        c.isComposite = true;
        if("and".equals(cmd)){
            c.type = Operator.AND;
        }else if("or".equals(cmd)){
            c.type = Operator.OR;
        }
        return c;

    }




    static class Criterion {
        private String condition;
        private Object value;
        private Object secondVal;
        private Operator operator;

        public Criterion(String condition,Operator operator, Object value,Object secondVal) {
            this.condition = condition;
            this.value = value;
            this.operator = operator;
            this.secondVal = secondVal;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Operator getOperator() {
            return operator;
        }

        public Object getSecondVal() {
            return secondVal;
        }

        public void setOperator(Operator operator) {
            this.operator = operator;
        }
    }


    enum Operator{
        AND("and","and","and"),
        OR("or","or","or"),
        EQ("eq","=","EqualTo"),
        NE("nq","<>","NotEqualTo"),         //NotEqual
        LT("lt","<","LessThan"),         //小于
        LTE("lte","<=","LessThanOrEqualTo"),        //小于等于
        GT("gt",">","GreaterThan"),         //大于
        GTE("gte",">=","GreaterThanOrEqualTo"),        //大于等于
        LIKE("like","like","Like"),        //like
        NLIKE("nlike","not like","NotLike"),
        IN("in","in","In"),
        NIN("nin","not in","NotIn"),
        BT("bt","between","Between"),
        NBT("nbt","not between","NotBetween"),
        IS("is","is null","IsNull"),
        NIS("nis","not is null","IsNotNull");

        private String key;
        private String tag;
        private String value;
        private static Map<String,String> map = new HashMap<>();
        Operator(String key,String tag,String value){
            this.key = key;
            this.tag = tag;
            this.value = value;
        }

        public static String getValue(String key){
            if(key != null && key.length() != 0){
                return map.get(key);
            }
            return null;
        }

        public String getTag(){
            return this.tag;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        static{
            Operator[] values = Operator.values();
            for(Operator operator : values){
                map.put(operator.key,operator.value);
            }
        }



    }
}
