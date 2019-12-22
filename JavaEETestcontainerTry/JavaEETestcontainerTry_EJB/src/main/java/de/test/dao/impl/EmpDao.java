package de.test.dao.impl;

import de.test.dao.IEmpDao;
import de.test.entities.Emp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public class EmpDao implements IEmpDao {

    @PersistenceContext // (unitName = "ReadingUnit")
    private EntityManager em;

    public void removeAllEmps() {
        em.createQuery("DELETE FROM Emp").executeUpdate();
    }

    public List<Emp> getAllEmps() {
        return em.createQuery("FROM Emp", Emp.class).getResultList();
    }

}
