package com.sysc.bulag_faust.core.Security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {
    @Value("${app.cors.allowed-origins}")
    private  String[] allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //development vs production
        if(Arrays.asList(allowedOrigins).contains("*")){
            corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        }else{
            corsConfiguration.setAllowedOriginPatterns(Arrays.asList(allowedOrigins));

        }
        //http methods
        corsConfiguration.setAllowedMethods(Arrays.asList("GET","PUT","POST","DELETE","PATCH","OPTIONS"));

        //HEADERS THAT CAN BE SENT TO CLIENT
        corsConfiguration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"));

        //HEADERS THAT BROWSER CAN ACCESS
        corsConfiguration.setExposedHeaders(Arrays.asList(
                "Authorization",
                "X-Total-Count",
                "X-Total-Pages"
        ));

        //ALLOW CREDENTIAL COOKIES, AUTH HEADER
        corsConfiguration.setAllowCredentials(true);
        //HOW LONG BROWSER CAN CACHE PREFLIGHT RESPONSE(IN SECONDS)
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return  source;
    }

}
