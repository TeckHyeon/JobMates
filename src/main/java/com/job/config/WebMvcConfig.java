package com.job.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

   @Autowired
   private  LoginCheckInterceptor   loginCheckInterceptor;
   
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

       System.out.println("okokok");
        registry.addInterceptor( loginCheckInterceptor )
                .addPathPatterns("/**")      // http://localhost:9090/
                .addPathPatterns("/**/*")    

                .excludePathPatterns("/IdDupCheck","/personjoin","/IdDupCheck*","/searchJobType","/searchResult",
                      "/join","/personlogin","/companylogin","/companyjoin",
                      "/ApplyPage","/Search*","/comlogin","/logout","/submit","/log", "/", "/log*", "/css/**", "/images/**", "/js/**");
       
    }
    
}