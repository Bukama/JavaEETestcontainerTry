
package de.test.genericdao.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

/**
 * Abstract base class for Data Access Objects.
 * Offers methods to access the EntityManager or the CriteriaBuilder.
 *
 * Implementation must provide concrete methods to access the EntityManager.
 */
public abstract class Dao implements Serializable {

  /** The serial version uid. */
  private static final long serialVersionUID = 1L;

  /**
   * Abstract method to access the EntityManager
   * 
   * @return EntityManager to be used.
   */
  protected abstract EntityManager getEntityManager();

  /**
   * Setter for the EntityManager.
   */
  protected abstract void setEntityManager(EntityManager em);

  /**
   * Returns a CriteriaBuilder.
   * 
   * @return CriteriaBuilder to be used
   */
  protected CriteriaBuilder getCriteriaBuilder() {
    return getEntityManager().getCriteriaBuilder();
  }

}
