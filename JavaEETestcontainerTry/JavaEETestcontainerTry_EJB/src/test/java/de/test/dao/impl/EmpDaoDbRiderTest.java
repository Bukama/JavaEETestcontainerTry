package de.test.dao.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.util.EntityManagerProvider;
import com.github.database.rider.junit5.DBUnitExtension;
import de.test.entities.Emp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Disabled
@ExtendWith(DBUnitExtension.class)
@RunWith(JUnitPlatform.class)
@DBUnit(url = "jdbc:oracle:thin:@localhost:1521:containers;DB_CLOSE_DELAY=-1", driver = "oracle.jdbc.OracleDriver", user = "c##readinguser", password = "oralce")
public class EmpDaoDbRiderTest {

    private ConnectionHolder connectionHolder = () -> EntityManagerProvider.instance("ReadingDS").connection();


    EmpDao sut;

//    @BeforeEach
//    public void setUp() {
//        System.setProperty("hibernate.connection.username", System.getProperty("username"));
//        System.setProperty("hibernate.connection.password", System.getProperty("password"));
//
//        sut = new EmpDao();
//        EntityManager em = Persistence.createEntityManagerFactory("ReadingDSTest").createEntityManager();
//        em.getTransaction().begin();
//
//        sut.em = em;
//    }

    @BeforeEach
    public void setUp() {


        sut = new EmpDao();

    }


    @Test
    public void GetAllEmps() {
        List<Emp> allEmps = sut.getAllEmps();
        Assertions.assertThat(allEmps.size()).isEqualTo(14);


    }


    @Test
    public void DeleteAllEmps() {
        Exception exception = assertThrows(Exception.class, () -> {
            sut.removeAllEmps();
        });

        String expectedMessage = "org.hibernate.exception.SQLGrammarException: could not execute statement";
        //String expectedMessage = "ORA-01031: insufficient privileges";
        String actualMessage = exception.getMessage();

        Assertions.assertThat(exception).isExactlyInstanceOf(PersistenceException.class);
        Assertions.assertThat(actualMessage).isEqualTo(expectedMessage);
    }

}
