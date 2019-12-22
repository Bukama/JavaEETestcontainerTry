package de.test;

import de.test.entities.Emp;
import de.test.service.EmpService;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Class only for test purose
 */
@Stateless
@Startup
public class StartUp {

    @Inject
    Logger logger;

    @Inject
    EmpService empService;

    @PostConstruct
    void postConstruct() {

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
