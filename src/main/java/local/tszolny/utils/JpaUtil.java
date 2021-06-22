package local.tszolny.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
    private static EntityManagerFactory entityManagerFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaUtil.class);

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        } catch (ExceptionInInitializerError ex) {
            LOGGER.error("Initial SessionFactory creation failed", ex);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if(!entityManagerFactory.isOpen()){
            throw new IllegalStateException("Entity manager factory has already been closed.");
        }
        return entityManagerFactory;
    }

    public static void closeEntityManagerFactory(){
        entityManagerFactory.close();
    }

}