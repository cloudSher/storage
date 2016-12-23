package com.eternity.storage.core.query;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/14.
 */
public class MybatisModelExample implements Example {

    private final static String PRE_METHOD ="add";
    private static Map param;
    private  Criteria criteria;
    private  Query query;

    MybatisModelExample(Query query){
        this.query = query;
        if(query != null){
            this.criteria = query.getCriteria();
        }
    }

    public static MybatisModelExample with(Query query){
        return new MybatisModelExample(query);
    }


    public  <T> T create(Class<T> clazz) throws Exception {
        if(clazz == null){
            throw new IllegalArgumentException("Class argument must not be null");
        }
        criteria = transform();
        List<ParamInfo> methodNames = getMethodName(criteria);
        T t = clazz.newInstance();
        if(methodNames != null &&methodNames.size()!=0){
            Object o = t;
            for(ParamInfo info : methodNames){
                String methodName = info.getMethod();
                String field = info.getField();
                Field field1 = clazz.getField(field);
                Class<?> type = field1.getType();
                Criteria.Operator operator = info.getType();
                Method method;
                if(operator.equals(Criteria.Operator.IS) || operator.equals(Criteria.Operator.NIS)) {
                    method = clazz.getMethod(methodName, null);
                    o = method.invoke(o, null);
                }else if(operator.equals(Criteria.Operator.BT) || operator.equals(Criteria.Operator.NBT)){
                    method = clazz.getMethod(methodName,type,type);
                    o = method.invoke(o,info.getValue(),info.getSecondVal());
                }else if(operator.equals(Criteria.Operator.IN) || operator.equals(Criteria.Operator.NIN)){
                    method = clazz.getMethod(methodName,List.class);
                    method.invoke(o,info.getValue());
                }else{
                    method = clazz.getMethod(methodName,type);
                    method.invoke(o,info.getValue());
                }
            }
            return (T) o;
        }
        return null;
    }



    public  Criteria transform(){
        if(param == null){
            return null;
        }


        return null;
    }



    public List<ParamInfo> getMethodName(Criteria criteria){
        List<Criteria> orderCriteria = criteria.getOrderCriteria();
        List<ParamInfo> methodNames = new ArrayList<>();
        if(orderCriteria == null || orderCriteria.size() ==0  && !criteria.isComposite()){
            Criteria.Criterion criterion = criteria.getCriterion();
            ParamInfo info = doMethodName(criterion);
            methodNames.add(info);
            return methodNames;
        }else{
            if(criteria.isComposite()){
                Criteria.Operator type = criteria.getType();
                if(type.equals(Criteria.Operator.AND))
                for(Criteria c : orderCriteria){
                    if(criteria != null){
                       getMethodName(c);
                    }
                }
            }
        }
        return null;
    }



    public ParamInfo doMethodName(Criteria.Criterion criterion){
        String condition = criterion.getCondition();
        String operator = criterion.getOperator().getValue();
        String methodName = PRE_METHOD + condition.substring(0,1).toUpperCase() + condition.substring(1) + operator;
        return new ParamInfo(methodName,condition,criterion.getOperator(),criterion.getValue(),criterion.getSecondVal());
    }


    class ParamInfo{
        private String method;
        private String field;
        private Criteria.Operator type;
        private Object value;
        private Object secondVal;

        public ParamInfo(String method, String field, Criteria.Operator type, Object value,Object val) {
            this.method = method;
            this.field = field;
            this.type = type;
            this.value = value;
            this.secondVal = val;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Criteria.Operator getType() {
            return type;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondVal() {
            return secondVal;
        }
    }


    public static void main(String args[]) throws NoSuchMethodException {
//        Class clazz = UserPOExample.Criteria.class;
//        Arrays.asList(clazz.getMethods()).forEach(System.out::println);
    }


}
