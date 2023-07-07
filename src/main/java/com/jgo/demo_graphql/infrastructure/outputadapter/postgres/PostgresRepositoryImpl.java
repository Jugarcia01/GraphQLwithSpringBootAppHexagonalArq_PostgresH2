package com.jgo.demo_graphql.infrastructure.outputadapter.postgres;

import com.jgo.demo_graphql.domain.repository.postgres.CustomerRepositoryJpa;
import com.jgo.demo_graphql.domain.repository.postgres.OrderRepositoryJpa;
import com.jgo.demo_graphql.infrastructure.outputport.EntityRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//-- To Enable Postgres repo uncomment the anotation:
@Component("postgresRepository")
//--
public class PostgresRepositoryImpl implements EntityRepository {

  @Autowired
  @PersistenceUnit
  private EntityManagerFactory entityManagerFactory;

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  CustomerRepositoryJpa customerRepositoryJpa;

  @Autowired
  OrderRepositoryJpa orderRepositoryJpa;

  @Override
  @Transactional
  public <T> T createEntity(T entity) {
    // To Postgres use this sentence:
    // entityManager.merge(entity);

    // To H2 database use:
    entityManager.persist(entity);
    entityManager.flush(); // Force the generation of the id value
    return entity;
  }

  @Override
  @Transactional
  public <T> T updateEntity(T entity) {
    // To H2 database use:
    // entityManager.persist(entity);
    // To Postgres use this sentence:
    entityManager.merge(entity);
    return entity;
  }

  @Override
  public <T> T getEntityById(Long id, Class<T> clazz) {
    return entityManager.find(clazz, id);
  }

  @Override
  @Transactional
  public <T> T getEntityByUuid(UUID uuid, Class<T> clazz) {
    return entityManager.find(clazz, uuid);
  }

  @Override
  @Transactional
  public <T> List<T> getEntityByEmail(String email, Class<T> clazz) {

    if (customerRepositoryJpa.findByEmail(email).isPresent()) {
      return (List<T>) customerRepositoryJpa.findByEmail(email).get();
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  @Transactional
  public <T> T deleteEntityById(UUID uuid, Class<T> clazz) {
    T entity = entityManager.find(clazz, uuid);
    if (entity != null) {
      entityManager.remove(entity);
      return entity;
    } else {
      throw new EntityNotFoundException("Entity with Id: " + uuid + " not found");
    }
  }

  @Override
  @Transactional
  public <T> T deleteEntityById(Long id, Class<T> clazz) {
    T entity = entityManager.find(clazz, id);
    if (entity != null) {
      entityManager.remove(entity);
      return entity;
    } else {
      throw new EntityNotFoundException("Entity with Id: " + id + " not found");
    }
  }

  @Override
  @Transactional
  public <T> List<T> getAllEntities(Class<T> clazz) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> cq = cb.createQuery(clazz);
    Root<T> root = cq.from(clazz);
    cq.select(root);
    TypedQuery<T> query = entityManager.createQuery(cq);
    return query.getResultList();
  }
}
