//package com.eternity.storage.repository.impl;
//
//import com.eternity.storage.api.dto.QUser;
//import com.eternity.storage.api.dto.User;
//import com.eternity.storage.api.repositories.StorageRepository;
//import com.eternity.storage.api.repositories.UserRepository;
//import com.mongodb.Mongo;
//import com.querydsl.core.types.OrderSpecifier;
//import com.querydsl.core.types.Predicate;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactory;
//import org.springframework.data.mongodb.core.MongoOperations;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.core.support.RepositoryFactorySupport;
//
//import java.net.InetAddress;
//import java.util.List;
//
//
///**
// * Created by cloudsher on 2016/8/2.
// */
//
//public class UserRepositoryImpl{
//
//    private static MongoOperations template = null;
//    private static ElasticsearchOperations elasticTempldate = null;
//
//    static {
//        try {
//            template = new MongoTemplate(new Mongo(),"customer");
//            elasticTempldate = new ElasticsearchTemplate(TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.1.34.24"), 9300)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void user(){
//        RepositoryFactorySupport factory = new MongoRepositoryFactory(template);
//        StorageRepository repository = factory.getRepository(UserRepository.class);
//        QUser user = new QUser("User");
//        Predicate predicate = user.name.eq("lisi");
//        Iterable<User> users = repository.findAll(predicate);
//        System.out.println(users.iterator().next());
//
////        List<User> list = repository.findAll();
////        System.out.println(list);
//    }
//
//    public static void elastic(){
//        RepositoryFactorySupport factory = new ElasticsearchRepositoryFactory(elasticTempldate);
//        UserRepository repository = factory.getRepository(UserRepository.class);
//        QUser user = new QUser("User");
//        Predicate predicate = user.name.eq("lisi");
//        Iterable<User> all = repository.findAll(predicate);
//        System.out.println(all.iterator());
////        repository.save(new User("111","擎天柱"));
//    }
//
//    public static void main(String args[]){
//        user();
////        elastic();
//    }
//}