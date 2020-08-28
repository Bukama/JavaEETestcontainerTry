
package de.test.genericdao.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Generic Class for Data Access Objects.
 * Provides typesafe methods for CRUD operations.
 */
public class GenericDao<T> extends CrudDao<T> {

  private static final long serialVersionUID = 1L;

  @Inject
  private transient EntityManager entityManager;

  public GenericDao() {
    super();
  }

  @Override
  public EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  protected void setEntityManager(EntityManager em) {
    this.entityManager = em;
  }


}
