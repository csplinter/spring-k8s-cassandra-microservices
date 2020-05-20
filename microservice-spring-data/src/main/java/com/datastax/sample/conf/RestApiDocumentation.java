package com.datastax.sample.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.datastax.sample.SpringDataApplication;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class RestApiDocumentation implements WebMvcConfigurer {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("MicroservicesWithCassandra")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.datastax.sample.resources"))
            .paths(PathSelectors.regex("/api/v1.*"))
            .build()
            .apiInfo(apiInfo())
            .useDefaultResponseMessages(false);
    }
    
    /**
     * Initialization of documentation
     *
     * @return static infos
     */
    private ApiInfo apiInfo() {
        ApiInfoBuilder builder = new ApiInfoBuilder();
        builder.title("Microservices With Spring and Cassandra");
        builder.description("Implementation with Spring Data Cassandra");
        builder.version(SpringDataApplication.class.getPackage().getImplementationVersion());
        builder.license("Apache 2 Licence");
        builder.licenseUrl("https://github.com/clun/spring-microservices-with-cassandra");
        return builder.build();
    }
    
    /** {@inheritDoc} */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
}
