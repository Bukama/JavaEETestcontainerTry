
package de.test.genericdao.dao.impl;

import de.test.genericdao.dao.IGenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Implementierung des {@link IGenericDao} Interfaces und Erweiterung der {@link FindDao} Klasse um generiche
 * Funktionalitäten für Create, Read, Update und Delete.
 */
public abstract class CrudDao<T> extends FindDao implements IGenericDao<T> {

  /** The serial version uid. */
  private static final long serialVersionUID = 1L;

  /** The static logger. */
  private static final Logger LOG = LogManager.getLogger();

  /** The log4j Marker for SQL-Statements. Can be used in log4j Filter. */
  protected static final Marker SQL = MarkerManager.getMarker("SQL");

  /** The default flush mode, used in methods without flush parameter. */
  protected static final boolean FLUSH_MODE_DEFAULT = false;

  /** The flush mode for loops. */
  protected static final boolean FLUSH_MODE_LOOP = false;

  /** The class of the generic type. */
  private Class<T> type;

  /**
   * Default-Konstruktor, der zur Initialisierung die {@link #init()} Methode aufruft.
   */
  public CrudDao() {
    init();
  }

  /**
   * Methode zur Initialisierung der Klasse. Bestimmt die Klasse des generichen Klassenparameter.
   */
  @SuppressWarnings("unchecked")
  @PostConstruct
  private void init() {
    LOG.entry();

    Type generic;
    if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
      generic = getClass().getGenericSuperclass();
    } else {
      generic = getClass().getSuperclass().getGenericSuperclass();
    }

    ParameterizedType pt = (ParameterizedType) generic;
    type = (Class<T>) pt.getActualTypeArguments()[0];

    LOG.exit();
  }

  @Override
  public T create(T generic) {
    LOG.entry(generic);

    T returnValue = create(generic, FLUSH_MODE_DEFAULT);

    LOG.exit(returnValue);
    return returnValue;
  }

  @Override
  public T create(T generic, boolean flush) {
    LOG.entry(generic, flush);

    EntityManager entityManager = getEntityManager();
    entityManager.persist(generic);

    if (flush) {
      entityManager.flush();
    }

    LOG.exit(generic);
    return generic;
  }

  @Override
  public List<T> create(List<T> genericList) {
    LOG.entry(genericList);

    List<T> returnValue = create(genericList, FLUSH_MODE_DEFAULT);

    LOG.exit(returnValue);
    return returnValue;
  }

  @Override
  public List<T> create(List<T> genericList, boolean flush) {
    LOG.entry(genericList, flush);

    if (null != genericList && !genericList.isEmpty()) {
      for (T t : genericList) {
        create(t, FLUSH_MODE_LOOP);
      }

      if (flush) {
        getEntityManager().flush();
      }
    }

    LOG.exit(genericList);
    return genericList;
  }

  @Override
  public T update(T generic) {
    LOG.entry(generic);

    T merge = update(generic, FLUSH_MODE_DEFAULT);

    LOG.exit(merge);
    return merge;
  }

  @Override
  public T update(T generic, boolean flush) {
    LOG.entry(generic, flush);

    EntityManager entityManager = getEntityManager();
    T merge = entityManager.merge(generic);

    if (flush) {
      entityManager.flush();
    }

    LOG.exit(merge);
    return merge;
  }

  @Override
  public List<T> update(List<T> genericList) {
    LOG.entry(genericList);

    List<T> returnValue = update(genericList, FLUSH_MODE_DEFAULT);

    LOG.exit(returnValue);
    return returnValue;
  }

  @Override
  public List<T> update(List<T> genericList, boolean flush) {
    LOG.entry(genericList, flush);
    List<T> returnValue = null;

    if (null != genericList && !genericList.isEmpty()) {
      // Rückgabeliste aus Performancegründen direkt mit der Größe der Eingabliste initialisieren
      returnValue = new ArrayList<T>(genericList.size());

      for (T t : genericList) {
        returnValue.add(update(t, FLUSH_MODE_LOOP));
      }

      if (flush) {
        getEntityManager().flush();
      }
    }

    LOG.exit(returnValue);
    return returnValue;
  }

  @Override
  public void delete(Object id) {
    LOG.entry(id);

    delete(id, FLUSH_MODE_DEFAULT);

    LOG.exit();
  }

  @Override
  public void delete(Object id, boolean flush) {
    LOG.entry(id, flush);

    EntityManager entityManager = getEntityManager();
    entityManager.remove(entityManager.getReference(type, id));

    if (flush) {
      entityManager.flush();
    }

    LOG.exit();
  }

  @Override
  public T find(Object id) {
    LOG.entry(id);

    T returnValue = null;

    if (null != id) {
      returnValue = getEntityManager().find(type, id);
    }

    LOG.exit(returnValue);
    return returnValue;
  }

  @Override
  public List<T> findAll() {
    LOG.entry();

    List<T> returnValue = getEntityManager().createQuery("FROM " + type.getName()).getResultList();

    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link CriteriaQuery} gegen die Datenbank aus und gibt das Ergebnis zurück.
   * <p>
   * Wandelt die CriteriaQuery in eine TypedQuery um, setzt die übergebenen Parameter und ruft die entsprechende Methode
   * mit der TypedQuery als Parameter aus der Klasse {@link FindDao} auf.
   * 
   * @param queryString
   *          Die Abfrage, die ausgeführt werden soll.
   * @param parameters
   *          Die Parameter der Abfrage.
   * @return Die Liste der gefundenen Elemente.
   * @see FindDao#findByTypedQuery(TypedQuery)
   */
  protected List<T> findByNamedQuery(String queryString, Map<String, Object> parameters) {
    LOG.entry(queryString, parameters);

    TypedQuery<T> typedQuery = getTypedQuery(queryString, parameters);
    List<T> returnValue = findByTypedQuery(typedQuery);

    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link CriteriaQuery} gegen die Datenbank aus und gibt das Ergebnis zurück.
   * <p>
   * Wandelt die CriteriaQuery in eine TypedQuery um, setzt die übergebenen Parameter und ruft die entsprechende Methode
   * mit der TypedQuery als Parameter aus der Klasse {@link FindDao} auf.
   * 
   * @param queryString
   *          Die Abfrage, die ausgeführt werden soll.
   * @param parameters
   *          Die Parameter der Abfrage.
   * @param maxResults
   *          Die maximale Größe der Ergebnismenge.
   * @return Die Liste der gefundenen Elemente.
   * @see FindDao#findByTypedQuery(TypedQuery, int)
   */
  protected List<T> findByNamedQuery(String queryString, Map<String, Object> parameters, int maxResults) {
    LOG.entry(queryString, parameters, maxResults);

    TypedQuery<T> typedQuery = getTypedQuery(queryString, parameters);
    List<T> returnValue = findByTypedQuery(typedQuery, maxResults);

    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link CriteriaQuery} gegen die Datenbank aus und gibt das Ergebnis zurück.
   * <p>
   * Wandelt die CriteriaQuery in eine TypedQuery um, setzt die übergebenen Parameter und ruft die entsprechende Methode
   * mit der TypedQuery als Parameter aus der Klasse {@link FindDao} auf.
   * 
   * @param queryString
   *          Die Abfrage, die ausgeführt werden soll.
   * @param parameters
   *          Die Parameter der Abfrage.
   * @return Das gesuchte Element.
   * @see FindDao#findByTypedQueryFirstResult(TypedQuery)
   */
  protected T findByNamedQueryFirstResult(String queryString, Map<String, Object> parameters) {
    LOG.entry(queryString, parameters);

    TypedQuery<T> typedQuery = getTypedQuery(queryString, parameters);
    T returnValue = findByTypedQueryFirstResult(typedQuery);

    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link CriteriaQuery} gegen die Datenbank aus und gibt das Ergebnis zurück.
   * <p>
   * Wandelt die CriteriaQuery in eine TypedQuery um, setzt die übergebenen Parameter und ruft die entsprechende Methode
   * mit der TypedQuery als Parameter aus der Klasse {@link FindDao} auf.
   * 
   * @param queryString
   *          Die Abfrage, die ausgeführt werden soll.
   * @param parameters
   *          Die Parameter der Abfrage.
   * @return Das gesuchte Element.
   * @see FindDao#findByTypedQuerySingleResult(TypedQuery)
   */
  protected T findByNamedQuerySingleResult(String queryString, Map<String, Object> parameters) {
    LOG.entry(queryString, parameters);

    TypedQuery<T> typedQuery = getTypedQuery(queryString, parameters);
    T returnValue = findByTypedQuerySingleResult(typedQuery);

    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Führt eine {@link CriteriaQuery} gegen die Datenbank aus und gibt das Ergebnis zurück.
   * <p>
   * Wandelt die CriteriaQuery in eine TypedQuery um, setzt die übergebenen Parameter und ruft die entsprechende Methode
   * mit der TypedQuery als Parameter aus der Klasse {@link FindDao} auf.
   * 
   * @param queryString
   *          Die Abfrage, die ausgeführt werden soll.
   * @param parameters
   *          Die Parameter der Abfrage.
   * @return Das gesuchte Element.
   * @throws NoResultException
   *           Wenn nicht genau ein (!) Ergebnis gefunden wurde.
   * @see FindDao#findByTypedQueryStrictlySingleResult(TypedQuery)
   */
  protected T findByNamedQueryStrictlySingleResult(String queryString, Map<String, Object> parameters)
      throws NoResultException {
    LOG.entry(queryString, parameters);

    TypedQuery<T> typedQuery = getTypedQuery(queryString, parameters);
    T returnValue = findByTypedQueryStrictlySingleResult(typedQuery);

    LOG.exit(returnValue);
    return returnValue;
  }

  protected T getReference(Object id) {
    LOG.entry(id);

    T returnValue = getEntityManager().getReference(type, id);

    LOG.exit(returnValue);
    return returnValue;
  }

  /**
   * Erzeugt eine TypedQuery anhand der NamedQuery (queryString) und setzt die Parameter aus der Map.
   * 
   * @param queryString
   *          Die NamedQuery aus der die TypedQuery erzeugt werden soll.
   * @param parameters
   *          Die Parameter für die Query.
   * @return TypedQuery&lt;T&gt;
   */
  private TypedQuery<T> getTypedQuery(String queryString, Map<String, Object> parameters) {
    LOG.entry(queryString, parameters);

    TypedQuery<T> typedQuery = getEntityManager().createNamedQuery(queryString, type);
    for (Entry<String, Object> entry : parameters.entrySet()) {
      typedQuery.setParameter(entry.getKey(), entry.getValue());
    }

    LOG.exit(typedQuery);
    return typedQuery;
  }

}
