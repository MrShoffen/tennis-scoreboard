package org.mrshoffen.utils;

import com.google.inject.*;
import jakarta.inject.Singleton;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mapper.MatchMapper;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.mapstruct.factory.Mappers;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DependencyManager extends AbstractModule {

    private static final Injector INSTANCE = Guice.createInjector(new DependencyManager());

    public static Injector getInjector() {
        return INSTANCE;
    }





    @Provides @Singleton
    private SessionFactory buildSessionFactory() {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        return sessionFactory;
    }

    @Provides @Singleton
    private MatchMapper getMatchMapper(){
        return Mappers.getMapper(MatchMapper.class);
    }


}
