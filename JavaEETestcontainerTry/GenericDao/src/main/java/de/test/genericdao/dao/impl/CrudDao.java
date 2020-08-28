
package de.test.genericdao.dao.impl;

import de.test.genericdao.dao.IGenericDao;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.Map.Entry;

/**
 * Implements the IGenericDao interfaces and extends the FindDao class for additional CRUD methods.
 *
 * Default flush mode is false.
 */
public abstract class CrudDao<T> extends FindDao implements IGenericDao<T> {

  /** The serial version uid. */
  private static final long serialVersionUID = 1L;

  /** The default flush mode, used in methods without flush parameter. */
  protected static final boolean FLUSH_MODE_DEFAULT = false;

  /** The flush mode for loops. */
  protected static final boolean FLUSH_MODE_LOOP = false;

  /** The class of the generic type. */
  private Class<T> type;

  /**
   * Default constructor which calls the init() method.
   */
  public CrudDao() {
    init();
  }

  /**
   * Methoid to initalize the class (defines the class of the generic parameter).
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
   * @see FindDao#findByTypedQuery(TypedQuery)
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
   * @see FindDao#findByTypedQuery(TypedQuery, int)
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
   * @see FindDao#findByTypedQueryFirstResult(TypedQuery)
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
   * @see FindDao#findByTypedQuerySingleResult(TypedQuery)
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
   * @see FindDao#findByTypedQueryStrictlySingleResult(TypedQuery)
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
    for (Entry<String, Object> entry : parameters.entrySet()) {
      typedQuery.setParameter(entry.getKey(), entry.getValue());
    }

    return typedQuery;
  }

}
