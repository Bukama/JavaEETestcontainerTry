package de.test;

import de.test.entities.Emp;
import de.test.service.EmpService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.List;

/**
 * Class only for test purpose
 */

@Singleton
@Startup
public class StartUp {
    @Inject
    EmpService empService;

    @PostConstruct
    void postConstruct() {
        System.out.println("=========== StartUp - POSTConstruct ===========");

        // For Tests only
        List<Emp> allEmps = empService.getAllEmps();
        System.out.println("There are [" + allEmps.size() + "] emps stored");

        System.out.println("Trying to delete the emps, but this should fail because the app is not allowed to");
        try {
            empService.deleteEmp();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
