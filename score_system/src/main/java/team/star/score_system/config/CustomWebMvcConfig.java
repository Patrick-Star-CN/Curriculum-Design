package team.star.score_system.config;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import team.star.score_system.interceptor.LogInterceptor;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Configuration
@AllArgsConstructor
public class CustomWebMvcConfig implements WebMvcConfigurer {
    private final LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
    }
}