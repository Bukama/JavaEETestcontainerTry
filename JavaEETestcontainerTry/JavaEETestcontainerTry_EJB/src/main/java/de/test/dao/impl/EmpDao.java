package de.test.dao.impl;

import de.test.dao.IEmpDao;
import de.test.entities.Emp;
import de.test.entities.Emp_;
import de.test.genericdao.dao.impl.GenericDao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Simple interface implementation. The PersistenceContext is defined by a JPA persistence unit and provided via CDI.
 */
public class EmpDao extends GenericDao<Emp> implements IEmpDao {

  @Override
    public void removeAllEmps() {
        getEntityManager().createQuery("DELETE FROM Emp").executeUpdate();
    }

    @Override
    public List<Emp> getAllEmps() {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Emp> criteriaQuery = builder.createQuery(Emp.class);
        Root<Emp> rootEmp = criteriaQuery.from(Emp.class);

        return findByCriteriaQuery(criteriaQuery);
    }

    @Override
    public Optional<Emp> findByName(String name) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Emp> criteriaQuery = builder.createQuery(Emp.class);
        Root<Emp> rootEmp = criteriaQuery.from(Emp.class);

        Predicate whereName = builder.equal(rootEmp.get(Emp_.ename), name);
        criteriaQuery.where(whereName);

        return findByCriteriaQuerySingleResult(criteriaQuery);
    }

}
