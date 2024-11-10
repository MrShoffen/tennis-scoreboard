package org.mrshoffen.listener;


import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.validation.ValidatorFactory;
import org.hibernate.SessionFactory;
import org.mrshoffen.service.OngoingMatchesService;
import org.mrshoffen.utils.DependencyManager;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class ContextListener implements ServletContextListener {

    @Inject
    OngoingMatchesService ongoingMatchesService;

    @Inject
    SessionFactory sessionFactory;

    @Inject
    ValidatorFactory validatorFactory;

    private ScheduledExecutorService executorService;
    private static final int HOURS_BEFORE_REMOVE_MATCH = 3;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DependencyManager.getInjector().injectMembers(this);

        configureNotFinishedMatchRemover();
    }

    private void configureNotFinishedMatchRemover() {

        executorService = Executors.newSingleThreadScheduledExecutor();

        var ongoingMatches = OngoingMatchesService.getOngoingMatches();

        executorService.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();

            ongoingMatches.forEach((id, match) -> {
                if (match.getCreatedTime().plusHours(HOURS_BEFORE_REMOVE_MATCH).isBefore(now)) {
                    ongoingMatchesService.removeMatchById(id);
                }
            });
        }, 0, 1, TimeUnit.HOURS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (executorService != null) {
            executorService.shutdown();
        }

        validatorFactory.close();
        sessionFactory.close();

    }
}
