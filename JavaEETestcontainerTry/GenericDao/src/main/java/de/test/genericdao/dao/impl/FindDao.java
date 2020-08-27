package de.test.genericdao.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Erweiterung der {@link Dao} Klasse, um generische Find-Operationen.
 * <p>
 * Es werden entweder {@link TypedQuery} oder {@link CriteriaQuery} gegen eine Datenbank ausgeführt und je nach Methode
 * eine Liste von Objekten oder genau ein Objekt zurück gegeben.
 * <p>
 * Jede Methode loggt ihre Übergabe- und Rückgabewerte und misst ihre Ausführungszeit.
 */
public abstract class FindDao extends Dao {

  /** The serial version uid. */
  private static final long serialVersionUID = 1L;

  /** The static logger. */
  private static final Logger LOG = LogManager.getLogger();

  private static final String CLASS_NAME = FindDao.class.getName();

  /** The format of the performance Log (CSV-Style). */
  private static final String LOGGING_MESSAGE_PERFORMANCE = "{};{};{}";

  /** The log4j marker for the performance Log. */
  private static final Marker LOGGING_MARKER_PERFORMANCE = MarkerManager.getMarker("PERFORMANCE");

  /**
   * Führt eine {@link TypedQuery} gegen die Datenbank aus und gibt eine Liste von Ergebnissen zurück.
   * 
   * @param typedQuery
   *          Die Abfrage, die ausgeführt werden soll.
   * @return Eine Liste der gesuchten Elemente.
   */
  protected <E> List<E> findByTypedQuery(TypedQuery<E> typedQuery) {
    LOG.entry(typedQuery);
    final long startTime = System.currentTimeMillis();

    List<E> returnValue = typedQuery.getResultList();

    if (LOG.isTraceEnabled(LOGGING_MARKER_PERFORMANCE)) {
      LOG.trace(LOGGING_MARKER_PERFORMANCE, LOGGING_MESSAGE_PERFORMANCE, CLASS_NAME, "findByTypedQuery(TypedQuery)",
          System.currentTimeMillis() - startTime);
    }
    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link TypedQuery} gegen die Datenbank aus und gibt eine Liste von Ergebnissen zurück.
   * <p>
   * Zusätzlich kann die maximale Anzahl von Ergebnissen bestimmt werden. Der Parameter maxResults wird nur beachtet,
   * wenn er größer als 0 ist.
   * 
   * @param typedQuery
   *          Die Abfrage, die ausgeführt werden soll.
   * @param maxResults
   *          Die maximale Größe der Ergebnismenge.
   * @return Eine Liste der gesuchten Elemente.
   */
  protected <E> List<E> findByTypedQuery(TypedQuery<E> typedQuery, int maxResults) {
    LOG.entry(typedQuery);
    final long startTime = System.currentTimeMillis();

    if (maxResults > 0) {
      typedQuery.setMaxResults(maxResults);
    }
    List<E> returnValue = typedQuery.getResultList();

    if (LOG.isTraceEnabled(LOGGING_MARKER_PERFORMANCE)) {
      LOG.trace(LOGGING_MARKER_PERFORMANCE, LOGGING_MESSAGE_PERFORMANCE, CLASS_NAME, "findByTypedQuery(TypedQuery,int)",
          System.currentTimeMillis() - startTime);
    }
    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link TypedQuery} gegen die Datenbank aus und gibt das erste Objekt der
   * Ergebnismenge zurück. Wenn die Abfrage kein Ergebnis geliefert hat, wird {@code NULL} zurückgegeben.
   * <p>
   * Bei unsortierten Abfragen besteht keine Garantie, dass immer das gleiche Objekt zurückgeliefert wird.
   * 
   * @param typedQuery
   *          Die Abfrage, die ausgeführt werden soll.
   * @return Das gesuchte Objekt.
   */
  protected <E> E findByTypedQueryFirstResult(TypedQuery<E> typedQuery) {
    LOG.entry(typedQuery);

    List<E> resultList = findByTypedQuery(typedQuery, 1);

    E returnValue = null;
    if (null != resultList && !resultList.isEmpty()) {
      returnValue = resultList.get(0);
    }

    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link TypedQuery} gegen die Datenbank aus und gibt das erste Objekt der Ergebnismenge zurück, wenn
   * diese genau ein Element enthält. Wenn die Abfrage kein Ergebnis geliefert hat, wird {@code NULL} zurückgegeben.
   * 
   * @param typedQuery
   *          Die Abfrage, die ausgeführt werden soll.
   * @return Das gesuchte Objekt.
   */
  protected <E> E findByTypedQuerySingleResult(TypedQuery<E> typedQuery) {
    LOG.entry(typedQuery);

    List<E> resultList = findByTypedQuery(typedQuery);

    E returnValue = null;
    if (null != resultList && resultList.size() == 1) {
      returnValue = resultList.get(0);
    }

    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link TypedQuery} gegen die Datenbank aus und erwartet, dass genau ein Element gefunden wird, das auch
   * zurückgegeben wird. Ist dies nicht der Fall, weil kein Element oder mehr als ein Element gefunden wurde, wird eine
   * {@link NoResultException} geworfen.
   * <p>
   * Durch das Werfen der {@link NoResultException} setzt der {@link EntityManager} die Transaktion in den Rollback
   * Modus!
   * 
   * @param typedQuery
   *          Die Abfrage, die ausgeführt werden soll.
   * @return Das gesuchte Objekt.
   * @throws NoResultException
   *           Wenn die Abfrage nicht genau ein(!) Element in der Datenbank findet.
   */
  protected <E> E findByTypedQueryStrictlySingleResult(TypedQuery<E> typedQuery) throws NoResultException {
    LOG.entry(typedQuery);
    final long startTime = System.currentTimeMillis();

    E returnValue = typedQuery.getSingleResult();

    if (LOG.isTraceEnabled(LOGGING_MARKER_PERFORMANCE)) {
      LOG.trace(LOGGING_MARKER_PERFORMANCE, LOGGING_MESSAGE_PERFORMANCE, CLASS_NAME,
          "findByTypedQueryStrictlySingleResult(TypedQuery)", System.currentTimeMillis() - startTime);
    }
    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link CriteriaQuery} gegen die Datenbank aus und gibt das Ergebnis zurück.
   * <p>
   * Wandelt die CriteriaQuery in eine TypedQuery um und ruft die entsprechende Methode mit der TypedQuery als Parameter
   * auf.
   * 
   * @param criteriaQuery
   *          Die Abfrage, die ausgeführt werden soll.
   * @return Eine Liste der gesuchten Elemente.
   * @see #findByTypedQuery(TypedQuery)
   */
  protected <E> List<E> findByCriteriaQuery(CriteriaQuery<E> criteriaQuery) {
    LOG.entry(criteriaQuery);

    TypedQuery<E> typedQuery = getEntityManager().createQuery(criteriaQuery);

    List<E> returnValue = findByTypedQuery(typedQuery);

    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link CriteriaQuery} gegen die Datenbank aus und gibt das Ergebnis zurück.
   * <p>
   * Wandelt die CriteriaQuery in eine TypedQuery um und ruft die entsprechende Methode mit der TypedQuery als Parameter
   * auf.
   * 
   * @param criteriaQuery
   *          Die Abfrage, die ausgeführt werden soll.
   * @param maxResults
   *          Die maximale Größe der Ergebnismenge.
   * @return Eine Liste der gesuchten Elemente.
   * @see #findByTypedQuery(TypedQuery, int)
   */
  protected <E> List<E> findByCriteriaQuery(CriteriaQuery<E> criteriaQuery, int maxResults) {
    LOG.entry(criteriaQuery, maxResults);

    TypedQuery<E> typedQuery = getEntityManager().createQuery(criteriaQuery);

    List<E> returnValue = findByTypedQuery(typedQuery, maxResults);

    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link CriteriaQuery} gegen die Datenbank aus und gibt das Ergebnis zurück.
   * <p>
   * Wandelt die CriteriaQuery in eine TypedQuery um und ruft die entsprechende Methode mit der TypedQuery als Parameter
   * auf.
   * 
   * @param criteriaQuery
   *          Die Abfrage, die ausgeführt werden soll.
   * @return Das gesuchte Element.
   * @see #findByTypedQueryFirstResult(TypedQuery)
   */
  protected <E> E findByCriteriaQueryFirstResult(CriteriaQuery<E> criteriaQuery) {
    LOG.entry(criteriaQuery);

    TypedQuery<E> typedQuery = getEntityManager().createQuery(criteriaQuery);

    E returnValue = findByTypedQueryFirstResult(typedQuery);

    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link CriteriaQuery} gegen die Datenbank aus und gibt das Ergebnis zurück.
   * <p>
   * Wandelt die CriteriaQuery in eine TypedQuery um und ruft die entsprechende Methode mit der TypedQuery als Parameter
   * auf.
   * 
   * @param criteriaQuery
   *          Die Abfrage, die ausgeführt werden soll.
   * @return Das gesuchte Element.
   * @see #findByTypedQuerySingleResult(TypedQuery)
   */
  protected <E> E findByCriteriaQuerySingleResult(CriteriaQuery<E> criteriaQuery) {
    LOG.entry(criteriaQuery);

    TypedQuery<E> typedQuery = getEntityManager().createQuery(criteriaQuery);

    E returnValue = findByTypedQuerySingleResult(typedQuery);

    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link CriteriaQuery} gegen die Datenbank aus und gibt das Ergebnis zurück.
   * <p>
   * Wandelt die CriteriaQuery in eine TypedQuery um und ruft die entsprechende Methode mit der TypedQuery als Parameter
   * auf.
   * 
   * @param criteriaQuery
   *          Die Abfrage, die ausgeführt werden soll.
   * @return Das gesuchte Element.
   * @throws NoResultException
   *           Wenn die Abfrage nicht genau ein(!) Element in der Datenbank findet.
   * @see #findByTypedQueryStrictlySingleResult(TypedQuery)
   */
  protected <E> E findByCriteriaQueryStrictlySingleResult(CriteriaQuery<E> criteriaQuery)
      throws NoResultException {
    LOG.entry(criteriaQuery);

    TypedQuery<E> typedQuery = getEntityManager().createQuery(criteriaQuery);

    E returnValue = findByTypedQueryStrictlySingleResult(typedQuery);

    LOG.exit(returnValue);
    return returnValue;
  }

}
