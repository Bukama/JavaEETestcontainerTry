package de.test.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class GenericDao {

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Inject
    protected transient EntityManager entityManager;





}
