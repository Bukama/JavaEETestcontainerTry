package de.test.genericdao.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Extends the DAO class for additional find methods using TypedQuery or CriteriaQuery.
 */
public abstract class FindDao extends Dao {

  /** The serial version uid. */
  private static final long serialVersionUID = 1L;

  private static final int ONE_RESULT = 1;

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

}
