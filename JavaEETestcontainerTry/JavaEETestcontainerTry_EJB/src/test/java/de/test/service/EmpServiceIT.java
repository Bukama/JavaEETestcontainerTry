package de.test.service;

import de.test.entities.Emp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

// Disabled, so project can be manually compiled and tested
@Disabled
@SharedContainerConfig(AppDeploymentConfig.class)
@MicroShedTest
public class EmpServiceIT {

    @Inject
    EmpService sut;

    @Test
    public void GetAllEmps() {
        List<Emp> allEmps = sut.getAllEmps();
        Assertions.assertThat(allEmps.size()).isEqualTo(16);
    }


    @Test
    public void DeleteAllEmps() {
        Exception exception = assertThrows(Exception.class, () -> {
            sut.deleteEmp();
        });

        //String expectedMessage = "could not execute statement";
        String expectedMessage = "ORA-01031: insufficient privileges";
        String actualMessage = exception.getMessage();

        Assertions.assertThat(exception).isExactlyInstanceOf(PersistenceException.class);
        Assertions.assertThat(actualMessage).isEqualTo(expectedMessage);
    }

}
