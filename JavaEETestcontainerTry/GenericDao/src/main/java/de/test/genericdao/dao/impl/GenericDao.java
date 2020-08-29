
package de.test.genericdao.dao.impl;

import de.test.genericdao.dao.IGenericDao;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Generic Class for Data Access Objects.
 * Provides typesafe methods for CRUD operations.
 *
 */
public class GenericDao<T> implements IGenericDao<T>, Serializable {

  private static final long serialVersionUID = 1L;

  private static final int ONE_RESULT = 1;

  /** The default flush mode, used in methods without flush parameter. */
  protected static final boolean FLUSH_MODE_DEFAULT = false;

  /** The flush mode for loops. */
  protected static final boolean FLUSH_MODE_LOOP = false;

  /** The class of the generic type. */
  private Class<T> type;

  @Inject
  private transient EntityManager entityManager;

  /**
   * Default constructor which calls the init() method.
   */
  public GenericDao() {
    init();
  }

  /**
   * Method to initialize the class (defines the class of the generic parameter).
   */
  @SuppressWarnings("unchecked")
  @PostConstruct
  private void init() {

    Type generic;
    if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
      generic = getClass().getGenericSuperclass();
    } else {
      generic = getClass().getSuperclass().getGenericSuperclass();
    }

    ParameterizedType pt = (ParameterizedType) generic;
    type = (Class<T>) pt.getActualTypeArguments()[0];
  }

  /**
   * Abstract method to access the EntityManager
   *
   * @return EntityManager to be used.
   */
  public EntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * Setter for the EntityManager.
   */
  protected void setEntityManager(EntityManager em) {
    this.entityManager = em;
  }

  /**
   * Returns a CriteriaBuilder.
   *
   * @return CriteriaBuilder to be used
   */
  protected CriteriaBuilder getCriteriaBuilder() {
    return getEntityManager().getCriteriaBuilder();
  }



  /**
   * Executes a TypedQuery to return a list of results.
   *
   * @param typedQuery Query to be executed
   * @param <E> Generic Type
   * @return Results, never null
   */
  protected <E> List<E> findByTypedQuery(TypedQuery<E> typedQuery) {

    List<E> returnValue = typedQuery.getResultList();

    returnValue = (null != returnValue) ? returnValue : Collections.emptyList();

    return returnValue;
  }

  /**
   * Executes a TypedQuery to return a list of results with a maximum of results, using "setmaxResults".
   *
   * @param typedQuery Query to be executed
   * @param <E> Generic Type
   * @param maxResults Maximum number of results. Only used if greater then zero.
   * @return Results, never null
   */
  protected <E> List<E> findByTypedQuery(TypedQuery<E> typedQuery, int maxResults) {

    if (maxResults > 0) {
      typedQuery.setMaxResults(maxResults);
    }
    List<E> returnValue = typedQuery.getResultList();

    returnValue = (null != returnValue) ? returnValue : Collections.emptyList();

    return returnValue;
  }


  /**
   * Executes a TypedQuery and returns the first result of the result list.
   * If the query is unsorted the result may vary.
   *
   * @param typedQuery Query to be executed
   * @param <E> Generic Type
   * @return Result, never null
   */
  protected <E> Optional<E> findByTypedQueryFirstResult(TypedQuery<E> typedQuery) {
    List<E> resultList = findByTypedQuery(typedQuery, ONE_RESULT);

    E returnValue = null;
    if (!resultList.isEmpty()) {
      returnValue = resultList.get(0);
    }


    return Optional.ofNullable(returnValue);
  }

  /**
   * Executes a TypedQuery and returns the first result of the result list, if it contains exactly one result.
   *
   * @param typedQuery Query to be executed
   * @param <E> Generic Type
   * @return Result if the query returns exactly one element.
   */
  protected <E> Optional<E> findByTypedQuerySingleResult(TypedQuery<E> typedQuery) {

    List<E> resultList = findByTypedQuery(typedQuery);

    E returnValue = null;
    if (resultList.size() == ONE_RESULT) {
      returnValue = resultList.get(0);
    }

    return Optional.ofNullable(returnValue);
  }

  /**
   * Executes a TypedQuery and returns the first result of the result list, if it contains exactly one result.
   * If the result does not contain exactly one element a NoResultException is thrown.
   *
   * Warning: The NoResultException forces the EntityManager to set the transaction on rollback!
   *
   * @param typedQuery Query to be executed
   * @param <E> Generic Type
   * @return Result if the query returns exactly one element.
   * @throws NoResultException
   *         When the result does not contain exactly one element.
   *
   * @see TypedQuery#getSingleResult()
   */
  protected <E> Optional<E> findByTypedQueryStrictlySingleResult(TypedQuery<E> typedQuery) throws NoResultException {

    E returnValue = typedQuery.getSingleResult();

    return Optional.ofNullable(returnValue);
  }

  /**
   * Executes a CriteriaQuery to return a list of results.
   * The CriteriaQuery is transformed into a TypedQuery.
   *
   * @param criteriaQuery Query to be executed
   * @param <E> Generic Type
   * @return Results, never null
   * @see #findByTypedQuery(TypedQuery)
   */
  protected <E> List<E> findByCriteriaQuery(CriteriaQuery<E> criteriaQuery) {

    TypedQuery<E> typedQuery = getEntityManager().createQuery(criteriaQuery);

    return findByTypedQuery(typedQuery);
  }

  /**
   * Executes a CriteriaQuery to return a list of results with a maximum of results, using "setmaxResults".
   * The CriteriaQuery is transformed into a TypedQuery.
   *
   * @param criteriaQuery Query to be executed
   * @param <E> Generic Type
   * @param maxResults Maximum number of results. Only used if greater then zero.
   * @return Results, never null
   * @see #findByTypedQuery(TypedQuery, int)
   */
  protected <E> List<E> findByCriteriaQuery(CriteriaQuery<E> criteriaQuery, int maxResults) {

    TypedQuery<E> typedQuery = getEntityManager().createQuery(criteriaQuery);

    return findByTypedQuery(typedQuery, maxResults);
  }

  /**
   * Executes a CriteriaQuery to and returns the first result of the result list.
   * If the query is unsorted the result may vary.
   * The CriteriaQuery is transformed into a TypedQuery.
   *
   * @param criteriaQuery Query to be executed
   * @param <E> Generic Type
   * @return Result, never null
   * @see #findByTypedQueryFirstResult(TypedQuery)
   */
  protected <E> Optional<E> findByCriteriaQueryFirstResult(CriteriaQuery<E> criteriaQuery) {

    TypedQuery<E> typedQuery = getEntityManager().createQuery(criteriaQuery);

    return findByTypedQueryFirstResult(typedQuery);
  }

  /**
   * Executes a CriteriaQuery to and returns the first result of the result list, if it contains exactly one result.
   * The CriteriaQuery is transformed into a TypedQuery.
   *
   * @param criteriaQuery Query to be executed
   * @param <E> Generic Type
   * @return Result if the query returns exactly one element.
   */
  protected <E> Optional<E> findByCriteriaQuerySingleResult(CriteriaQuery<E> criteriaQuery) {

    TypedQuery<E> typedQuery = getEntityManager().createQuery(criteriaQuery);

    return findByTypedQuerySingleResult(typedQuery);
  }

  /**
   * Executes a CriteriaQuery to and returns the first result of the result list, if it contains exactly one result.
   * If the result does not contain exactly one element a NoResultException is thrown.
   * The CriteriaQuery is transformed into a TypedQuery.
   *
   * Warning: The NoResultException forces the EntityManager to set the transaction on rollback!
   *
   * @param criteriaQuery Query to be executed
   * @param <E> Generic Type
   * @return Result if the query returns exactly one element.
   * @throws NoResultException
   *         When the result does not contain exactly one element.
   *
   * @see #findByTypedQueryStrictlySingleResult(TypedQuery)
   */
  protected <E> Optional<E> findByCriteriaQueryStrictlySingleResult(CriteriaQuery<E> criteriaQuery)
          throws NoResultException {

    TypedQuery<E> typedQuery = getEntityManager().createQuery(criteriaQuery);

    return findByTypedQueryStrictlySingleResult(typedQuery);
  }

  @Override
  public T create(T generic) {
    return create(generic, FLUSH_MODE_DEFAULT);
  }

  @Override
  public T create(T generic, boolean flush) {
    EntityManager entityManager = getEntityManager();
    entityManager.persist(generic);

    if (flush) {
      entityManager.flush();
    }
    return generic;
  }

  @Override
  public List<T> create(List<T> genericList) {
    return create(genericList, FLUSH_MODE_DEFAULT);
  }

  @Override
  public List<T> create(List<T> genericList, boolean flush) {

    if (null != genericList && !genericList.isEmpty()) {
      for (T t : genericList) {
        create(t, FLUSH_MODE_LOOP);
      }

      if (flush) {
        getEntityManager().flush();
      }
    }

    return genericList;
  }

  @Override
  public T update(T generic) {
    return update(generic, FLUSH_MODE_DEFAULT);
  }

  @Override
  public T update(T generic, boolean flush) {
    EntityManager entityManager = getEntityManager();
    T merge = entityManager.merge(generic);

    if (flush) {
      entityManager.flush();
    }

    return merge;
  }

  @Override
  public List<T> update(List<T> genericList) {
    return update(genericList, FLUSH_MODE_DEFAULT);
  }

  @Override
  public List<T> update(List<T> genericList, boolean flush) {

    List<T> returnValue = null;

    if (null != genericList && !genericList.isEmpty()) {
      // To increase performance, initialize the result list with size of the input list.
      returnValue = new ArrayList<T>(genericList.size());

      for (T t : genericList) {
        returnValue.add(update(t, FLUSH_MODE_LOOP));
      }

      if (flush) {
        getEntityManager().flush();
      }
    }

    returnValue = (null != returnValue) ? returnValue : Collections.emptyList();

    return returnValue;
  }

  @Override
  public void delete(Object id) {
    delete(id, FLUSH_MODE_DEFAULT);
  }

  @Override
  public void delete(Object id, boolean flush) {
    EntityManager entityManager = getEntityManager();
    entityManager.remove(entityManager.getReference(type, id));

    if (flush) {
      entityManager.flush();
    }
  }

  @Override
  public Optional<T> find(Object id) {

    Optional<T> returnValue = Optional.empty();

    if (null != id) {
      T temp = getEntityManager().find(type, id);
      returnValue = Optional.ofNullable(temp);
    }
    return returnValue;
  }

  @Override
  public List<T> findAll() {

    List<T> returnValue = getEntityManager().createQuery("FROM " + type.getName()).getResultList();

    returnValue = (null != returnValue) ? returnValue : Collections.emptyList();
    return returnValue;
  }

  /**
   * Executes a NamedQuery to return a list of results.
   * The query string is transformed into a TypedQuery, before the parameters are set.
   *
   * @param queryString
   *          Name of the NamedQuery.
   * @param parameters
   *          Parameters that should be passed to the query
   * @return Results, never null
   * @see #findByTypedQuery(TypedQuery)
   */
  protected List<T> findByNamedQuery(String queryString, Map<String, Object> parameters) {
    TypedQuery<T> typedQuery = getTypedQuery(queryString, parameters);

    return findByTypedQuery(typedQuery);
  }

  /**
   * Executes a NamedQuery to return a list of results with a maximum of results, using "setmaxResults".
   * The query string is transformed into a TypedQuery, before the parameters are set.
   *
   * @param queryString
   *          Name of the NamedQuery.
   * @param parameters
   *          Parameters that should be passed to the query
   * @param maxResults Maximum number of results. Only used if greater then zero.
   * @return Results, never null
   * @see #findByTypedQuery(TypedQuery, int)
   */
  protected List<T> findByNamedQuery(String queryString, Map<String, Object> parameters, int maxResults) {
    TypedQuery<T> typedQuery = getTypedQuery(queryString, parameters);

    return findByTypedQuery(typedQuery, maxResults);
  }

  /**
   * Executes a NamedQuery and returns the first result of the result list.
   * The query string is transformed into a TypedQuery, before the parameters are set.
   *
   * @param queryString
   *          Name of the NamedQuery.
   * @param parameters
   *          Parameters that should be passed to the query
   * @return The first element of the result set
   * @see #findByTypedQueryFirstResult(TypedQuery)
   */
  protected Optional<T> findByNamedQueryFirstResult(String queryString, Map<String, Object> parameters) {

    TypedQuery<T> typedQuery = getTypedQuery(queryString, parameters);

    return findByTypedQueryFirstResult(typedQuery);
  }

  /**
   * Executes a NamedQuery and returns the first result of the result list, if it contains exactly one result.
   * The query string is transformed into a TypedQuery, before the parameters are set.
   *
   * @param queryString
   *          Name of the NamedQuery.
   * @param parameters
   *          Parameters that should be passed to the query
   * @return The unique element of the result set
   * @see #findByTypedQuerySingleResult(TypedQuery)
   */
  protected Optional<T> findByNamedQuerySingleResult(String queryString, Map<String, Object> parameters) {
    TypedQuery<T> typedQuery = getTypedQuery(queryString, parameters);

    return findByTypedQuerySingleResult(typedQuery);
  }


  /**
   * Executes a NamedQuery and returns the single result of the result list, if it contains exactly one result.
   * The query string is transformed into a TypedQuery, before the parameters are set.
   *
   * @param queryString
   *          Name of the NamedQuery.
   * @param parameters
   *          Parameters that should be passed to the query
   * @return The unique element of the result set
   * @throws NoResultException
   *         When the result does not contain exactly one element.   *
   * @see #findByTypedQueryStrictlySingleResult(TypedQuery)
   */
  protected Optional<T> findByNamedQueryStrictlySingleResult(String queryString, Map<String, Object> parameters)
          throws NoResultException {

    TypedQuery<T> typedQuery = getTypedQuery(queryString, parameters);

    return findByTypedQueryStrictlySingleResult(typedQuery);
  }

  /**
   * Returns the reference of an object.
   *
   * @param id Object the reference should be returned
   * @return Found reference, never null
   */
  protected Optional<T> getReference(Object id) {

    T returnValue = getEntityManager().getReference(type, id);

    return Optional.ofNullable(returnValue);
  }

  /**
   * Creates a TypedQuery by a NamedQuery and sets the parameters to the query.
   *
   * @param queryString
   *          Name of the NamedQuery.
   * @param parameters
   *          Parameters that should be passed to the query
   * @return Created TypedQuery
   */
  private TypedQuery<T> getTypedQuery(String queryString, Map<String, Object> parameters) {

    TypedQuery<T> typedQuery = getEntityManager().createNamedQuery(queryString, type);
    for (Map.Entry<String, Object> entry : parameters.entrySet()) {
      typedQuery.setParameter(entry.getKey(), entry.getValue());
    }

    return typedQuery;
  }

}
