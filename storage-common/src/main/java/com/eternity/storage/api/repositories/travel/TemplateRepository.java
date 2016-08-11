package com.eternity.storage.api.repositories.travel;

import com.eternity.workbench.api.model.Template;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by Administrator on 2016/8/11.
 */
public interface TemplateRepository extends MongoRepository<Template,String>,QueryDslPredicateExecutor<Template> {
}
