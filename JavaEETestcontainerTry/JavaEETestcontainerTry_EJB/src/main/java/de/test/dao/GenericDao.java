package de.test.dao;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class GenericDao  {

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext(unitName = "ReadingDS")
    @Produces
    //@Inject
    protected transient EntityManager entityManager;

    /** The serial version uid. */
    private static final long serialVersionUID = 1L;


}
