package de.test.service;

import de.test.dao.IEmpDao;
import de.test.entities.Emp;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
public class EmpService {

    @Inject
    IEmpDao empDao;

    public void deleteEmp() {
        empDao.removeAllEmps();
    }

    public List<Emp> getAllEmps() {
        return empDao.getAllEmps();
    }

    public Optional<Emp> findByName(String name) {
        return empDao.findByName(name);
    }

}
