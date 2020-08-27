
package de.test.genericdao.dao.impl;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Generische Klasse für DAOs. Stellt Implementationen für typsichere Methoden für Create, Read, Update und Delete zur
 * Verfügung.
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

}
