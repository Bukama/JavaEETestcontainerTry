package de.test.dao.impl;

import de.test.dao.IEmpDao;
import de.test.entities.Emp;
import de.test.entities.Emp_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Simple interface implementation. The PersistenceContext is definied by a JPA persistence unit.
 */
public class EmpDao implements IEmpDao {

    @PersistenceContext(unitName = "ReadingDS")
    // @PersistenceContext(unitName = "WritingDS")
            EntityManager em;

    public void removeAllEmps() {
        em.createQuery("DELETE FROM Emp").executeUpdate();
    }

    public List<Emp> getAllEmps() {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Emp> criteriaQuery = builder.createQuery(Emp.class);
        Root<Emp> rootEmp = criteriaQuery.from(Emp.class);

        TypedQuery<Emp> q = em.createQuery(criteriaQuery);
        return q.getResultList();
    }

    @Override
    public Optional<Emp> findByName(String name) {


        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Emp> criteriaQuery = builder.createQuery(Emp.class);
        Root<Emp> rootEmp = criteriaQuery.from(Emp.class);

        Predicate whereName = builder.equal(rootEmp.get(Emp_.ename), name);
        criteriaQuery.where(whereName);
        TypedQuery<Emp> q = em.createQuery(criteriaQuery);

        Emp qryResult = q.getSingleResult();

        return Optional.ofNullable(qryResult);
    }

}
