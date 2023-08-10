package team.star.score_system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Configuration
@RequiredArgsConstructor
public class ProfileConfig {

    private final ApplicationContext context;

    public String getActiveProfile() {
        return context.getEnvironment().getActiveProfiles()[0];
    }

    public boolean isDev() {
        String activeProfile = getActiveProfile();
        return "dev".equals(activeProfile) || "debug".equals(activeProfile);
    }
}