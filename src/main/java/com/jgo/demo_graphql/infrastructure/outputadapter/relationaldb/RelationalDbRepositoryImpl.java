package com.jgo.demo_graphql.infrastructure.outputadapter.relationaldb;

import com.jgo.demo_graphql.domain.model.BaseEntity;
import com.jgo.demo_graphql.domain.model.Customer;
import com.jgo.demo_graphql.domain.repository.relational.CustomerRepositoryJpa;
import com.jgo.demo_graphql.domain.repository.relational.OrderRepositoryJpa;
import com.jgo.demo_graphql.infrastructure.outputport.EntityRepository;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.jgo.demo_graphql.infrastructure.mapper.CustomerMapper.CUSTOMER_MAPPER;

//-- Enable for Postgres/H2 repo uncommenting the anotation:
@Component("r2dbRepository")
//--
public class RelationalDbRepositoryImpl implements EntityRepository {
  private static final Logger log = LoggerFactory.getLogger(RelationalDbRepositoryImpl.class);
  private static final String ENTITY_BY_EMAIL_NOT_FOUND_MSG = "Entity with email: %s not found";

  @PersistenceContext
  private EntityManager entityManager;

  CustomerRepositoryJpa customerRepositoryJpa;
  OrderRepositoryJpa orderRepositoryJpa;

  public RelationalDbRepositoryImpl(EntityManager entityManager,
                                    CustomerRepositoryJpa customerRepositoryJpa,
                                    OrderRepositoryJpa orderRepositoryJpa) {
    this.customerRepositoryJpa = customerRepositoryJpa;
    this.orderRepositoryJpa = orderRepositoryJpa;
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public <T> T createEntity(T entity) {
    CustomerR2db customerR2db = CUSTOMER_MAPPER.toCustomerPostgres((Customer) entity);

    // To Postgres use this sentence:
    entityManager.merge(customerR2db);

    // To H2 database use:
    // entityManager.persist(entity);
    entityManager.flush();
    return (T) entity;
  }

  @Override
  @Transactional
  public <T> T updateEntity(T entity) {
    CustomerR2db customerR2db = CUSTOMER_MAPPER.toCustomerPostgres((Customer) entity);
  
    // Fetch the existing customer with its orders to preserve the relationship
    CustomerR2db existingCustomer = entityManager.find(CustomerR2db.class, customerR2db.getUuid());
    if (existingCustomer != null) {
      // Preserve the existing orders to prevent orphan removal
      customerR2db.setOrders(existingCustomer.getOrders());
    }

    // To H2 database use:
    // entityManager.persist(entity);
    // To Postgres use this sentence:
    entityManager.merge(customerR2db);
    return (T) entity;
  }

  @Override
  public <T> T getEntityById(Long id, Class<T> clazz) {
    return entityManager.find(clazz, id);
  }

  @Override
  @Transactional
  public <T> T getEntityByUuid(UUID uuid, Class<T> clazz) {
    if (Customer.class.isAssignableFrom(clazz)) {
      clazz = (Class<T>) CustomerR2db.class;
    }
    T entity = entityManager.find(clazz, uuid);

    if (entity == null) {
      log.error("Entity with uuid: {} not found", uuid);
      return null;
    }
    return (T) CUSTOMER_MAPPER.toCustomer((CustomerR2db) entity);
  }

  @Override
  @Transactional
  public <T> List<T> getEntityByEmails(String email, Class<T> clazz) {

    Optional<List<CustomerR2db>> customerOptional = customerRepositoryJpa.findByEmail(email);
  
    if (customerOptional.isPresent() && !customerOptional.get().isEmpty()
            && (Customer.class.isAssignableFrom(clazz) || CustomerR2db.class.isAssignableFrom(clazz))) {
      return (List<T>) customerOptional.get()
              .stream()
              .map(CUSTOMER_MAPPER::toCustomer)
              .toList();
    }
    else {
      return Collections.emptyList();
    }
  }

  @Override
  @Transactional
  public <T> T getEntityByEmail(String email, Class<T> clazz) {
    return entityManager.find(clazz, email);
  }

  @Override
  @Transactional
  public <T> T deleteEntityById(UUID uuid, Class<T> clazz) {
    return deleteEntityByIdInternal(uuid, clazz);
  }

  @Override
  @Transactional
  public <T> T deleteEntityById(Long id, Class<T> clazz) {
    return deleteEntityByIdInternal(id, clazz);
  }

  /**
   * Internal method to handle entity deletion by ID
   * @param id The ID of the entity to delete (can be UUID or Long)
   * @param clazz The class of the entity
   * @return The deleted entity
   * @throws EntityNotFoundException if the entity is not found
   */
  // @Transactional
  private <T, ID> T deleteEntityByIdInternal(ID id, Class<T> clazz) {
    if (Customer.class.isAssignableFrom(clazz)) {
      clazz = (Class<T>) CustomerR2db.class;
    }

    T entity = entityManager.find(clazz, id);
    if (entity != null) {
      entityManager.remove(entity);

      // Handle mapping back to domain model if needed
      if (CustomerR2db.class.isAssignableFrom(clazz)) {
        Customer entityResult = CUSTOMER_MAPPER.toCustomer((CustomerR2db) entity);
        return (T) entityResult;
      }
      return entity;

    } else {
      throw new EntityNotFoundException("Entity with Id: " + id + " not found");
    }
  }

  /**
   * Deletes a customer by email address
   *
   * @param email The email address of the customer to delete
   * @param clazz The class type of the entity to return
   * @return The deleted entity
   * @throws IllegalArgumentException if email is null
   * @throws EntityNotFoundException if no customer is found with the given email
   */
  @Override
  @Transactional
  public <T extends BaseEntity> T deleteEntityByEmail(String email, Class<T> clazz) {
    // Validate input parameters
    Objects.requireNonNull(email, "Email must not be null");
    
    try {
      // Find the customer by email
      UUID customerUuid = findCustomerUuidByEmail(email);
      
      // Delete the customer using the UUID
      return this.<T>deleteEntityById(customerUuid, clazz);
    } catch (EntityNotFoundException e) {
      // Rethrow with a more specific message
      throw new EntityNotFoundException(String.format(ENTITY_BY_EMAIL_NOT_FOUND_MSG, email));
    } catch (Exception e) {
      // Log the unexpected error and throw a more generic exception
      log.error("Error deleting customer with email: {}", email, e);
      throw new ServiceException("Error deleting customer with email: " + email);
    }
  }
  
  /**
   * Helper method to find a customer's UUID by email
   *
   * @param email The email to search for
   * @return The UUID of the found customer
   * @throws EntityNotFoundException if no customer is found with the given email
   */
  private UUID findCustomerUuidByEmail(String email) {
    return customerRepositoryJpa.findByEmail(email)
        .stream()
        .findFirst()
        .map(customers -> customers.isEmpty() ? null : customers.get(0).getUuid())
        .orElseThrow(() -> new EntityNotFoundException(String.format(ENTITY_BY_EMAIL_NOT_FOUND_MSG, email)));
  }

  @Override
  @Transactional
  public <T> List<T> getAllEntities(Class<T> clazz) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> cq = cb.createQuery(clazz);
    Root<T> root = cq.from(clazz);
    cq.select(root).distinct(true);
    TypedQuery<T> query = entityManager.createQuery(cq);
    return query.getResultList();
  }

  @Transactional
  public <T> List<T> getAllEntities(Class<T> clazz, List<String> fetchRelations) {

    if (Customer.class.isAssignableFrom(clazz)) {
      clazz = (Class<T>) CustomerR2db.class;
    }

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<T> cq = cb.createQuery(clazz);
    Root<T> root = cq.from(clazz);
    for (String relation : fetchRelations) {
      root.fetch(relation, JoinType.LEFT);
    }
    cq.select(root).distinct(true); // Add distinct to avoid duplicates
    TypedQuery<T> query = entityManager.createQuery(cq);
    List<T> resultList = new ArrayList<>();

    if (CustomerR2db.class.isAssignableFrom(clazz)) {
      resultList = query.getResultList()
              .stream()
              .map(t -> (T) CUSTOMER_MAPPER.toCustomer((CustomerR2db) t))
              .toList();
    }
    return resultList;
  }
}
