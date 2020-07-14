package de.test;

import de.test.entities.Emp;
import de.test.service.EmpService;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * This @StartUp Bean is used as a test class to verify the database configuration when the app is manually deployed
 * to an application server.
 */

@Singleton
@Startup
public class StartUp {

    @EJB
    EmpService empService;

    @Inject
    Logger logger;


    @PostConstruct
    void postConstruct() {
        System.out.println("=========== StartUp - POSTConstruct ===========");

        // For Tests only
        List<Emp> allEmps = empService.getAllEmps();
        System.out.println("There are [" + allEmps.size() + "] emps stored");

        Optional<Emp> empKing = empService.findByName("KING");
        if (empKing.isPresent()) {
            System.out.println("We have a King!");
        } else {
            System.out.println("No King was found :( ");
        }

        System.out.println("Trying to delete the emps, but this should fail because the app is not allowed to");
        try {
            empService.deleteEmp();
        } catch (Exception e) {
            logger.catching(e);
        }

    }
}
