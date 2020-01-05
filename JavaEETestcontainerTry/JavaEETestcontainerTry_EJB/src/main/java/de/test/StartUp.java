package de.test;

import de.test.entities.Emp;
import de.test.service.EmpService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Class only for test purpose
 */

@Stateless
@Startup
public class StartUp {

    private static final Logger logger = LogManager.getLogger(StartUp.class);

    @Inject
    EmpService empService;

    @PostConstruct
    void postConstruct() {
        System.out.println("=========== StartUp - POSTConstruct ===========");

        // For Tests only
        List<Emp> allEmps = empService.getAllEmps();
        logger.trace("There are [{}] emps stored", allEmps.size());

        logger.trace("Trying to delete the emps, but this should fail because the app is not allowed to");
        try {
            empService.deleteEmp();
        } catch (Exception e) {
            logger.error("Exception while trying to delete the emps", e);
        }

    }
}
