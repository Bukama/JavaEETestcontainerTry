package de.test;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class CDIContext {

    @Produces
    @PersistenceContext(unitName = "ReadingDS")
    // @PersistenceContext(unitName = "WritingDS")
    private EntityManager em;


}
