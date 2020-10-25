package io.pismo.transactions.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author leonardo.carmona (https://www.linkedin.com/in/leonardo-carmona/)
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket sampleApi() {
        return new Docket(DocumentationType.SWAGGER_2) //
                .select() //
                .apis(RequestHandlerSelectors.basePackage("io.pismo.transactions")) //
                .build() //
                .apiInfo(apiInfo()); //
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Pismo Transaction API",
                "Pismo Transaction API documentation",
                "1.0.0",
                null,
                new Contact("Leonardo Carmona", "https://www.linkedin.com/in/leonardo-carmona/", "leocarmona_@outlook.com"),
                null, null, Collections.emptyList());
    }

}
