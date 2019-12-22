package de.test.dao;

import de.test.entities.Emp;

import java.util.List;

public interface IEmpDao {

    void removeAllEmps();

    List<Emp> getAllEmps();

}
