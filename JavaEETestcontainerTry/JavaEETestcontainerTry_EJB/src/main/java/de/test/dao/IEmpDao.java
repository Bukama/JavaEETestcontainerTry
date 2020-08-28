package de.test.dao;

import de.test.entities.Emp;
import de.test.genericdao.dao.IGenericDao;

import java.util.List;
import java.util.Optional;

/**
 * Simple interface to wrap database access.
 */
public interface IEmpDao extends IGenericDao<Emp> {

    /**
     * Removes all records in the table.
     */
    void removeAllEmps();

    /**
     * Selecting all records from the table.
     *
     * @return List of stored EMP-Entities
     */
    List<Emp> getAllEmps();

    /**
     * Searches a record by name of the Emp.
     *
     * @param name Name of the Emp
     * @return Entity if found
     */
    Optional<Emp> findByName(String name);

}
