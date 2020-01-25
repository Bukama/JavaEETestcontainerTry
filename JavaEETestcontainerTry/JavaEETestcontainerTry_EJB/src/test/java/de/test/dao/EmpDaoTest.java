package de.test.dao;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.junit5.DBUnitExtension;
import com.github.database.rider.junit5.util.EntityManagerProvider;
import de.test.dao.impl.EmpDao;
import de.test.entities.Emp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(DBUnitExtension.class)
@RunWith(JUnitPlatform.class)
// Disabled, so project can be manually compiled and tested
//@Disabled
public class EmpDaoTest {

    private ConnectionHolder connectionHolder = () -> EntityManagerProvider.instance("ReadingDS").connection();


    EmpDao sut;

    @BeforeEach
    public void setUp() {
        sut = new EmpDao();
    }

    @Test
    public void GetAllEmps() {
        List<Emp> allEmps = sut.getAllEmps();
        Assertions.assertThat(allEmps.size()).isEqualTo(16);
    }


    @Test
    public void DeleteAllEmps() {
        Exception exception = assertThrows(Exception.class, () -> {
            sut.removeAllEmps();
        });

        //String expectedMessage = "could not execute statement";
        String expectedMessage = "ORA-01031: insufficient privileges";
        String actualMessage = exception.getMessage();

        Assertions.assertThat(exception).isExactlyInstanceOf(PersistenceException.class);
        Assertions.assertThat(actualMessage).isEqualTo(expectedMessage);
    }

}
