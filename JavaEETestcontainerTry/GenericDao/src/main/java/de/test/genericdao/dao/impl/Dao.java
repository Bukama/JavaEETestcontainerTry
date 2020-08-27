
package de.test.genericdao.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

/**
 * Abstrakte Basisklasse für Data Access Objects. Bietet Methoden um Zugriff auf den {@link EntityManager} oder den
 * {@link CriteriaBuilder} zu erlangen.
 * <p>
 * Die tatsächliche Bereitstellung des {@link EntityManager} Objektes liegt in der Verantwortung der eigentlichen
 * Implementierung des Data Access Objects.

 */
public abstract class Dao implements Serializable {

  /** The serial version uid. */
  private static final long serialVersionUID = 1L;

  /** The static logger. */
  private static final Logger LOG = LogManager.getLogger();

  /**
   * Liefert den {@link EntityManager} zurück.
   * 
   * @return Der EntityManager, der verwendet wird.
   */
  protected abstract EntityManager getEntityManager();

  /**
   * Liefert den {@link CriteriaBuilder} zurück.
   * 
   * @return Der CriteriaBuilder, der verwendet wird.
   */
  protected CriteriaBuilder getCriteriaBuilder() {
    LOG.entry();

    CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

    LOG.exit(criteriaBuilder);
    return criteriaBuilder;
  }

}
