package org.mrshoffen.utils;

import com.google.inject.*;
import jakarta.inject.Singleton;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.validator.HibernateValidator;
import org.mrshoffen.mapper.MatchMapper;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.mapstruct.factory.Mappers;
import org.mrshoffen.mapper.OngoingMatchMapper;
import org.mrshoffen.mapper.PlayerMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DependencyManager extends AbstractModule {

    private static final Injector INSTANCE = Guice.createInjector(new DependencyManager());

    public static Injector getInjector() {
        return INSTANCE;
    }


    @Provides @Singleton
    private SessionFactory getSessionFactory() {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    @Provides @Singleton
    private MatchMapper getMatchMapper(){
        return Mappers.getMapper(MatchMapper.class);
    }

    @Provides @Singleton
    private PlayerMapper getPlayerMapper(){
        return Mappers.getMapper(PlayerMapper.class);
    }

    @Provides @Singleton
    private OngoingMatchMapper getOngoingMatchMapper(){
        return Mappers.getMapper(OngoingMatchMapper.class);
    }

    @Provides @Singleton
    private Validator getValidator(){
        return buildValidatorFactory()
                .getValidator();
    }

    @Provides @Singleton
    private ValidatorFactory buildValidatorFactory() {
        return Validation.byProvider(HibernateValidator.class)
                .configure()
                .buildValidatorFactory();
    }

}
