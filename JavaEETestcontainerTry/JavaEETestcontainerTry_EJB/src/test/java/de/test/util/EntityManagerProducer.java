package de.test.util;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.HashMap;

@RequestScoped
public class EntityManagerProducer {
    private EntityManagerFactory emf;
    private EntityManager em;
    @PostConstruct
    private void initializeEntityManagerFactory() {
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("hibernate.connection.driver_class",
                "oracle.jdbc.OracleDriver");

        properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");

        properties.put("hibernate.connection.url",
                "jdbc:oracle:thin:@localhost:1521:container");
        properties.put("hibernate.connection.username",
                "c##readinguser");
        properties.put("hibernate.connection.password",
                "oracle");



        properties.put("hibernate.hbm2ddl.auto", "validate");
        properties.put("hibernate.show_sql", "true");

        properties.put("hibernate.generate_statistics", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.synonyms", "true");
        properties.put("use_sql_comments", "true");


        emf=Persistence.createEntityManagerFactory("ReadingDSTest", properties);
    }
    @Produces
    public EntityManager getEntityManager(InjectionPoint ip) {
        PersistenceContext ctx =
                ip.getAnnotated().getAnnotation(PersistenceContext.class);
//An dieser Stelle hat man die Möglichkeit,
//die Konfiguration zur Persistenz zu verändern.
        if(em == null) {
            em = emf.createEntityManager();
        }
        return em;
    }
}
