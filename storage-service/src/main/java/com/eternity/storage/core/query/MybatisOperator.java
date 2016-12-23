package com.eternity.storage.core.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/20.
 */
@Service("Mybatis")
public class MybatisOperator implements Operator {
//
//    @Autowired
//    SqlSessionFactory sqlSessionFactory;

    @Autowired
    ApplicationContext ctx;

    private Map<String,Object> map = new HashMap<>();

    public <T> List<T>  find(String sql,Class<? extends T> t){
//        SqlSession sqlSession = getSqlSession();
//        List<T> objects = sqlSession.selectList("executeSql",sql);
        return null;
    }
//
//    public SqlSession getSqlSession(){
//        return sqlSessionFactory.openSession();
//    }


    public <T> T get(Class<? extends T> t){
        T bean = ctx.getBean(t);
        return bean;
    }

    @Override
    public <T> List<T> find(Query query,Class<? extends T> t) {
        if(query == null){
            throw new IllegalArgumentException("参数不能为空");
        }
        Criteria criteria = query.getCriteria();
        if(criteria == null){
            throw new IllegalArgumentException("查询参数不能为空");
        }
        Select select = query.getSelect();
        StringBuilder builder = doSelect(select);
        doFrom(query.getDomain(),builder);
        doWhere(query.getCriteria(),builder);
        doSort(query.getSort(),builder);
        doPage(query.getStart(),query.getLen(),builder);
        return doExecute(builder,t);
    }

    public StringBuilder  doSelect(Select select){
        StringBuilder builder = new StringBuilder("select ");
        if(select != null){
            Select.SelectModel model = select.getModel();
            if(model.equals(Select.SelectModel.SIMPLE)){
                List<String> items = select.getItems();
                if(items == null || items.size() == 0){
                    builder.append("*");
                }else{
                    for(int i = 0 ;i < items.size(); i++){
                        builder.append(items);
                        if(i < items.size() -1){
                            builder.append(",");
                        }
                    }
                }
            }else{
                switch (model){
                    case AVG:
                        builder.append(" avg(").append(select.getKey()).append(")");
                        break;
                    case MAX:
                        builder.append(" max(").append(select.getKey()).append(")");
                        break;
                    case SUM:
                        builder.append(" sum(").append(select.getKey()).append(")");
                        break;
                    case MIN:
                        builder.append(" min(").append(select.getKey()).append(") ");
                        break;
                    case COUNT:
                        builder.append(" count(").append(select.getKey()).append(") ");
                        break;
                }
            }
        }
        return builder;
    }


    public StringBuilder doFrom(String domain,StringBuilder builder){
        return builder.append(" from ").append(domain);
    }

    public StringBuilder doWhere(Criteria criteria,StringBuilder builder){
        if(criteria != null){
            builder.append(" where ");
            builder.append(doCriteria(criteria));
        }
        return builder;
    }

    public StringBuilder doCriterion(Criteria.Criterion criterion){
        StringBuilder builder = new StringBuilder();
        Criteria.Operator operator = criterion.getOperator();
        Object value = criterion.getValue();
        String key = criterion.getCondition();
        Object secondVal = criterion.getSecondVal();
        switch (operator){
            case IN:
                builder.append(" ").append(key).append(" in ").append(" (");
                if(value instanceof List){
                    List values = (List) value;
                    int i = 0;
                    for(Object obj:values){
                        builder.append(obj);
                        if(++i < values.size()){
                            builder.append(",");
                        }
                    }
                    builder.append(") ");
                }
                break;
            case BT:
                builder.append(" ").append(key).append(" between").append(value).append(" and ").append(secondVal);
                break;
            case EQ:
            case NE:
            case LT:
            case LTE:
            case LIKE:
            case GT:
            case GTE:
            case IS:
            case NIS:
            default:
                builder.append(" ").append(key).append(operator.getTag()).append(value);
        }
        return builder;
    }

    public StringBuilder doCriteria(Criteria criteria){
        List<Criteria> orderCriteria = criteria.getOrderCriteria();
        if(orderCriteria == null || orderCriteria.size() == 0){
            Criteria.Criterion criterion = criteria.getCriterion();
            if(criterion == null){
                throw new IllegalArgumentException("查询参数不能为空");
            }
            return doCriterion(criterion);
        }else{
            Criteria.Operator type = criteria.getType();
            StringBuilder ss = new StringBuilder(" (");
            int i = 0;
            for(Criteria c : orderCriteria){
                if(c != null){
                    ss.append(doCriteria(c));
                    if(++i < orderCriteria.size() -1){
                        ss.append(type.getTag());
                    }
                }
            }
            ss.append(") ");
            return ss;
        }
    }

    public StringBuilder doPage(int start, int len,StringBuilder builder){
        if(start == 0 && len == 0){
            return builder;
        }else{
            builder.append(" limit ").append(start).append(",").append(len);
        }
        return builder;
    }

    public StringBuilder doSort(Sort sort,StringBuilder builder){
        if(sort != null){
            List<Sort.Order> orders = sort.getOrders();
            if(orders != null){
                builder.append(" order by ");
                int i = 0;
                for(Sort.Order order : orders){
                    builder.append(order.getProperty()).append(order.getDescribe().getKey());
                    if(++i < orders.size() -1){
                        builder.append(",");
                    }
                }
            }
        }
        return builder;
    }

    public <T> List<T> doExecute(StringBuilder builder,Class<? extends T> t){
        List<T> list = find(builder.toString(), t);
        return list;
    }

}
