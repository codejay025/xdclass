package net_xdclass.online_xdclass.config;

import net_xdclass.online_xdclass.interceptor.CorsInterceptor;
import net_xdclass.online_xdclass.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器的配置
 * 不需要权限的访问 ：/api/v1/pub
 * 需要权限的访问： /api/v1/pri
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    LoginInterceptor loginInterceptor(){
        return  new LoginInterceptor();
    }

    @Bean
    CorsInterceptor corsInterceptor(){
        return new CorsInterceptor();
    }

    /**
     * 注册拦截器，设置拦截路径
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /**
         * 跨域问题，拦截所有路径
         */
        registry.addInterceptor(corsInterceptor()).addPathPatterns("/**");

        /**
         * 添加一个拦截器，并且增加拦截路径
         */
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/api/v1/pri/*/*/**")
                // 不拦截的路径
                .excludePathPatterns("/api/v1/pri/user/login","/api/v1/pri/user/register");
        // 传进父类
        WebMvcConfigurer.super.addInterceptors(registry);

    }
}
