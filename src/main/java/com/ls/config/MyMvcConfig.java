package com.ls.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    //视图解析器
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //在浏览器访问/,就会跳转到index.html页面
        registry.addViewController("/").setViewName("index");
        //在浏览器访问/index.html,就会跳转到index.html页面
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/main.html").setViewName("dashboard");
    }

    //使区域化信息生效，注入到spring容器
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")//拦截所有请求
                .excludePathPatterns("/", "/index.html", "/user/login", "/css/**", "/img/**", "/js**");//排除这些请求
    }
}
