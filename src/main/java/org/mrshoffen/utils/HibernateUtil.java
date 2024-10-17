package org.mrshoffen.utils;


import com.google.inject.Provides;
import jakarta.inject.Singleton;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    private static final SessionFactory INSTANCE = buildSessionFactory();


    public static SessionFactory getSessionFactory() {
        return INSTANCE;
    }

    @Provides
    @Singleton
    private static SessionFactory buildSessionFactory() {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        return sessionFactory;
    }

}
