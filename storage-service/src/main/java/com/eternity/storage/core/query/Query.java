package com.eternity.storage.core.query;

import com.eternity.storage.core.query.Criteria.OperateType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/18.
 */
public class Query {

    private Map<String,Criteria> conditions = new HashMap<>();
    private Criteria criteria;

    public Query(){
    }

    public Query(Criteria criteria){
        this.criteria = criteria;
    }



    /**
     * 获取spring对应的query
     * @param
     * @return
     */
    public org.springframework.data.mongodb.core.query.Query getQueryMapping(){
        return org.springframework.data.mongodb.core.query.Query.query(getQueryCriteria());
    }

    public org.springframework.data.mongodb.core.query.Criteria getQueryCriteria(){
        if(this.criteria == null){
            throw new NullPointerException("when query entity, the criteria must not be null");
        }
        return transformSimpleCriteriaType(criteria);
    }

    private org.springframework.data.mongodb.core.query.Criteria getCriteria(String key){
        return new org.springframework.data.mongodb.core.query.Criteria(key);
    }

    public org.springframework.data.mongodb.core.query.Criteria transformSimpleCriteriaType(Criteria ca){
        Object[] obj = ca.getObj();
        Object value = ca.getValue();
        String key = ca.getKey();
        OperateType type = ca.getType();
        org.springframework.data.mongodb.core.query.Criteria criteria = null;
        switch (type){
            case EQ :
                criteria = getCriteria(key).is(value);
                break;
            case NE:
                criteria = getCriteria(key).ne(value);
                break;
            case LT:
                criteria = getCriteria(key).lt(value);
                break;
            case LTE:
                criteria = getCriteria(key).lte(value);
                break;
            case GT:
                criteria = getCriteria(key).gt(value);
                break;
            case GTE:
                criteria = getCriteria(key).gte(value);
                break;
            case IN:
                criteria = getCriteria(key).in(obj);
                break;
            case NIN:
                criteria = getCriteria(key).nin(obj);
                break;
            case AND:
                criteria = transformOperatorCriteriaType(ca);
                break;
            case OR:
                criteria = transformOperatorCriteriaType(ca);
                break;
            default:
                criteria = new org.springframework.data.mongodb.core.query.Criteria();
        }
        return criteria;
    }

    public org.springframework.data.mongodb.core.query.Criteria transformOperatorCriteriaType(Criteria ca){
        List<Criteria> criteriaList = ca.getCriteriaChain();
        OperateType type = ca.getType();
        org.springframework.data.mongodb.core.query.Criteria criteria = new org.springframework.data.mongodb.core.query.Criteria();
        int size = criteriaList.size();
        int i = 0;
        if(criteriaList!=null && size>0){
            org.springframework.data.mongodb.core.query.Criteria[] criterias = new org.springframework.data.mongodb.core.query.Criteria[size];
            for (Criteria c : criteriaList){
                if( c != null){
                    criterias[i++] = transformSimpleCriteriaType(c);
                }
            }
            if(type == OperateType.AND){
                criteria.andOperator(criterias);
            }else if(type == OperateType.OR){
                criteria.orOperator(criterias);
            }
        }
        return criteria;
    }

    public static Query query(){
        return new Query();
    }

    public Query where(Criteria criteria){
        this.criteria = criteria;
        return this;
    }

    public Criteria getCriteria(){
        return this.criteria;
    }












}
