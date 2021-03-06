package com.rqy.config;

import com.rqy.converter.String2DateConverter;
import com.rqy.intercepter.MyFirstInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.annotation.PostConstruct;


/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/6 20:48
 */
@EnableWebMvc
@ComponentScan(value = "com.rqy.controller",
        includeFilters =@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = Controller.class) )
public class SpringMvcConfig implements WebMvcConfigurer{

    //注入本身已有的接口
    @Autowired
    ConfigurableConversionService conversionService;
    //把自定义的conversionService重新注册到bean中
    @Bean
    //保证conversionService的唯一性
    @Primary
    public ConfigurableConversionService conversionService(){
        return conversionService;
    }
    //指定执行顺序
    @PostConstruct
    public  void addConverters(){
        String2DateConverter string2DateConverter = new String2DateConverter();
        conversionService.addConverter(string2DateConverter);
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyFirstInterceptor());
        //registry.addInterceptor().addPathPatterns("/abc/**");
    }
    //静态资源的配置
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pic/**").addResourceLocations("file:D:/pic/");
        registry.addResourceHandler("/pic/**").addResourceLocations("classpath:/pic/");
        registry.addResourceHandler("/pic/**").addResourceLocations("/WEB-INF/pic");
    }
    //配置视图解析器
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/views/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }
}
