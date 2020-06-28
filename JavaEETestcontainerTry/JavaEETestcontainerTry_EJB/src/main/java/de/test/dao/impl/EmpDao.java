package de.test.dao.impl;

import de.test.dao.IEmpDao;
import de.test.entities.Emp;
import de.test.entities.Emp_;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Simple interface implementation. The PersistenceContext is defined by a JPA persistence unit and provided via CDI
 */
public class EmpDao implements IEmpDao {

//    @PersistenceContext(unitName = "ReadingDS")
//    // @PersistenceContext(unitName = "WritingDS")
   @Inject
   private EntityManager entityManager;

    public void removeAllEmps() {
        entityManager.createQuery("DELETE FROM Emp").executeUpdate();
    }

    public List<Emp> getAllEmps() {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Emp> criteriaQuery = builder.createQuery(Emp.class);
        Root<Emp> rootEmp = criteriaQuery.from(Emp.class);

        TypedQuery<Emp> q = entityManager.createQuery(criteriaQuery);
        return q.getResultList();
    }

    @Override
    public Optional<Emp> findByName(String name) {


        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Emp> criteriaQuery = builder.createQuery(Emp.class);
        Root<Emp> rootEmp = criteriaQuery.from(Emp.class);

        Predicate whereName = builder.equal(rootEmp.get(Emp_.ename), name);
        criteriaQuery.where(whereName);
        TypedQuery<Emp> q = entityManager.createQuery(criteriaQuery);

        Emp qryResult = q.getSingleResult();

        return Optional.ofNullable(qryResult);
    }

}
