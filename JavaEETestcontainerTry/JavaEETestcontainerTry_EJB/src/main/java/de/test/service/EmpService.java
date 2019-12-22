package de.test.service;

import de.test.dao.IEmpDao;
import de.test.entities.Emp;

import javax.inject.Inject;
import java.util.List;

public class EmpService {

    @Inject
    IEmpDao empDao;

    public void deleteEmp() {
        empDao.removeAllEmps();
    }

    public List<Emp> getAllEmps() {
        return empDao.getAllEmps();
    }

}
