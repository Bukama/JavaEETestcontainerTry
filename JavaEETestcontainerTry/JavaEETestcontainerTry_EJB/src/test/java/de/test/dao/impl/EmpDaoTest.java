package de.test.dao.impl;

import de.test.entities.Emp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


//@ExtendWith(DBUnitExtension.class)
//@RunWith(JUnitPlatform.class)
// Disabled, so project can be manually compiled and tested
//@Disabled
public class EmpDaoTest {

    //private ConnectionHolder connectionHolder = () -> EntityManagerProvider.instance("ReadingDS").connection();


    EmpDao sut;

    @BeforeEach
    public void setUp() {
        sut = new EmpDao();
        EntityManager em = Persistence.createEntityManagerFactory("ReadingDSTest").createEntityManager();


        em.getTransaction().begin();

        sut.em = em;

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
