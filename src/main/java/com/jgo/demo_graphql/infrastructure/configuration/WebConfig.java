package com.jgo.demo_graphql.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry handlerRegistry) {
        handlerRegistry.addResourceHandler("/media/doc/**")
                .addResourceLocations("classpath:/media/doc/");
    }

}
