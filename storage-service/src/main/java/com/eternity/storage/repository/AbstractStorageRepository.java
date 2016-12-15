package com.eternity.storage.repository;


import com.eternity.storage.core.query.Query;
import com.eternity.storage.util.ClassUtil;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Created by Administrator on 2016/8/26.
 */
public abstract class AbstractStorageRepository<T,ID extends Serializable> implements StorageRepository<T,ID>{

    public final static String ID = "id";

    @Resource
    public
    MongoTemplate mongoTemplate;

    @Resource
    public Mongo mongo;

    public <S extends T> S insert(S s){
        mongoTemplate.insert(s);
        return s;
    }


    public String getPrimaryId(){
        return "";
    }


    public T findById(ID id,Class<T> entityClass) {
        return mongoTemplate.findById(query(where(ID).is(id)),entityClass);
    }

    public List<T> findAll(Class<T> entityClass) {
        return mongoTemplate.findAll(entityClass);
    }

    public boolean delete(T t) {
        WriteResult result = mongoTemplate.remove(t);
        return result.getN() == 1;
    }

    public <S extends T> S save(S s) {
        mongoTemplate.save(s);
        return s;
    }

    public <S extends T> S update(Query query, S s){
        Map<String, Object> values = ClassUtil.getAvailableFieldValues(s);
        Update update = new Update();
        for(Map.Entry<String,Object> entry : values.entrySet()){
            if(entry != null){
                update.set(entry.getKey(),entry.getValue());
            }
        }
        WriteResult writeResult = mongoTemplate.updateFirst(query.getQueryMapping(), update, s.getClass());
        if(writeResult == null){
            return null;
        }
        return s;
    }

    /**
     *  Query query = Query.query().where(and(eq("name","zhangsan"),eq("id","111113")));
     * @param query
     * @param var
     * @param <T>
     * @return
     */
    public <T> List<T> findByQuery(Query query, Class<T> var){
        return  mongoTemplate.find(query.getQueryMapping(),var);
    }

    public List<T> findByJsonQuery(String query,Class<T> var){
        BasicQuery bq = new BasicQuery(query);
        return mongoTemplate.find(bq,var);
    }


    public Map<String,Object> getAvailableFieldValues(T t){
        Map<String, Object> values = ClassUtil.getAvailableFieldValues(t);
        return values;
    }

    public org.springframework.data.mongodb.core.query.Query getEntityQuery(T t){
        Map<String, Object> values = getAvailableFieldValues(t);
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        if(values != null){
            int index = 0;
            Criteria criteria = null;
            for(Map.Entry<String,Object> entry : values.entrySet()){
                if(index == 0){
                    criteria = Criteria.where(entry.getKey()).is(entry.getValue());
                    index++;
                    continue;
                }
                if(criteria == null){
                    return null;
                }
                criteria.and(entry.getKey()).is(entry.getValue());
                index++;
            }
            query.addCriteria(criteria);
        }
        return query;
    }


    public T findOne(T t){
        return  mongoTemplate.findOne(getEntityQuery(t), (Class<T>) t.getClass());
    }

}
