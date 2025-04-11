package com.jgo.demo_graphql.infrastructure.outputadapter.mongodb;

import com.google.common.base.Preconditions;
import com.jgo.demo_graphql.domain.model.BaseEntity;
import com.jgo.demo_graphql.domain.model.Customer;
import com.jgo.demo_graphql.domain.port.CustomerRepository;
import com.jgo.demo_graphql.domain.repository.mongo.CustomerRepositoryMongo;
import com.jgo.demo_graphql.infrastructure.outputport.EntityRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static com.jgo.demo_graphql.infrastructure.mapper.CustomerMapper.CUSTOMER_MAPPER;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

//-- enable / uncomment the annotation to use Mongo repo:
// @Component("mongodbRepository")
public class MongodbRepositoryImpl implements EntityRepository {

  @Autowired
  @Qualifier("customerMongoRepository")
  MongoRepository mongoRepository;

  @Autowired
  CustomerRepository customerRepository;

  private EntityManager entityManager;

  CustomerRepositoryMongo customerRepositoryMongo;

  public MongodbRepositoryImpl(
          EntityManager entityManager,
          @Qualifier("customerMongoRepository") MongoRepository mongoRepository,
          CustomerRepositoryMongo customerRepositoryMongo) {
    this.entityManager = entityManager;
    this.mongoRepository = mongoRepository;
    this.customerRepositoryMongo = customerRepositoryMongo;
  }

  @Override
  public <T> T createEntity(T entity) {
    // return (T) mongoRepository.save(entity);

    CustomerMongo customerMongo = CUSTOMER_MAPPER.toCustomerMongo((Customer) entity);
    return (T) entityManager.merge(customerMongo);
  }

  @Override
  public <T> T updateEntity(T entity) {
    // return (T) mongoRepository.save(entity);

    CustomerMongo customerMongo = CUSTOMER_MAPPER.toCustomerMongo((Customer) entity);
    return (T) entityManager.merge(customerMongo);

  }

  @Override
  public <T> T getEntityById(Long id, Class<T> clazz) {
    // if (mongoRepository.findById(id).isPresent()) {
    //   return (T) mongoRepository.findById(id).get();
    // }
    // return null;

    if (!isEmpty(entityManager.find(Long.class, id))) {
      return (T) entityManager.find(Long.class,id);
    }
    return null;
  }

  @Override
  public <T> T getEntityByUuid(UUID uuid, Class<T> clazz) {
    if (mongoRepository.findById(uuid).isPresent()) {
      return (T) mongoRepository.findById(uuid).get();
    }
    return null;
  }

  @Override
  public <T> List<T> getEntityByEmails(String email, Class<T> clazz) {
    if (customerRepositoryMongo.findByEmail(email).isPresent()) {
      return (List<T>) customerRepositoryMongo.findByEmail(email).get();
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  @Transactional
  public <T> T getEntityByEmail(String email, Class<T> clazz) {
    return null;
  }

    @Override
  public <T> T deleteEntityById(UUID uuid, Class<T> clazz) {
    final T entity = (T) mongoRepository.findById(uuid).get();
    Preconditions.checkState(entity != null);
    mongoRepository.delete(entity);
    return entity;
  }

  @Override
  public <T> T deleteEntityById(Long id, Class<T> clazz) {
    final T entity = (T) mongoRepository.findById(id).get();
    Preconditions.checkState(entity != null);
    mongoRepository.delete(entity);
    return entity;
  }

  @Override
  public <T extends BaseEntity> T deleteEntityByEmail(String email, Class<T> clazz) {
    return null;
  }
  // @Override
  // public <T> T deleteEntityByEmail(String email, Class<T> clazz) {
  //   return null;
  // }

  @Override
  public <T> List<T> getAllEntities(Class<T> clazz) {
    return mongoRepository.findAll();
  }

  @Override
  public <T> List<T> getAllEntities(Class<T> clazz, List<String> fetchRelations) {
  // public <T, R> List<R> getAllEntities(Class<T> clazz, List<String> fetchRelations) {
    return null;
  }

}
