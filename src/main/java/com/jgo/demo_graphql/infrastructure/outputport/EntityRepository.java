package com.jgo.demo_graphql.infrastructure.outputport;

import com.jgo.demo_graphql.domain.model.BaseEntity;

import java.util.List;
import java.util.UUID;

public interface EntityRepository {

  <T> T createEntity(T entity);

  <T> T updateEntity(T entity);

  <T> T getEntityById(Long id, Class<T> clazz);

  <T> T getEntityByUuid(UUID id, Class<T> clazz);

  <T> List<T> getEntityByEmails(String email, Class<T> clazz);
  <T> T getEntityByEmail(String email, Class<T> clazz);

  <T> T deleteEntityById(UUID id, Class<T> clazz);

  <T> T deleteEntityById(Long id, Class<T> clazz);

  <T extends BaseEntity> T deleteEntityByEmail(String email, Class<T> clazz);

  <T> List<T> getAllEntities(Class<T> clazz);

  <T> List<T> getAllEntities(Class<T> clazz, List<String> fetchRelations);
  // <T, R> List<R> getAllEntities(Class<T> clazz, List<String> fetchRelations);

  }
