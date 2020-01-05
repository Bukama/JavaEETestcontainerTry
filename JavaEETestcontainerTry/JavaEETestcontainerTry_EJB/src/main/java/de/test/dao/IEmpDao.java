package de.test.dao;

import de.test.entities.Emp;

import java.util.List;

/**
 * Simple interface to wrap database access.
 */
public interface IEmpDao {

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

}
