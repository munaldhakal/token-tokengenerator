package com.enfiny.tokens.tokengenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class TokenGeneratorApplication {

//    @Bean
//    public FilterRegistrationBean corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(0);
//        return bean;
//    }

    public static void main(String[] args) {
        System.out.println("----------->>>>>>>"+BCrypt.hashpw("3nfiny@dmin@t0k3nG3n3r@t0r",BCrypt.gensalt()));
        if(BCrypt.checkpw("3nfiny@dmin@t0k3nG3n3r@t0r","$2a$10$Iyvz8sd49odA5is/RBpX5OkhcHRWPw3pvTibT6mI7rWy80.OTwQza"))
            System.out.println("------>>>>>>Correct");
        SpringApplication.run(TokenGeneratorApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/*")
                        .allowedOrigins("http://localhost:8081");
            }
        };
    }
}
