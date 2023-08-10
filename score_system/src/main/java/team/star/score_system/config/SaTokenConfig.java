package team.star.score_system.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Patrick_Star
 * @version 1.0
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    /**
     * 注册Sa-Token的注解拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {
                    SaRouter.match("/api/**", StpUtil::checkLogin);
                })).addPathPatterns("/api/**")
                .excludePathPatterns("/api/**");
//                .excludePathPatterns("/api/user/login/**",
//                        "/api/user/register",
//                        "/api/user/test/login/**");

        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");
    }
}