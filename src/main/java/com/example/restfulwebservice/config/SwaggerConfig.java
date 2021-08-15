package com.example.restfulwebservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final Contact DEFAULT_CONTACT = new Contact("Ryu", "https://www.notion.so/45b44f4e9fd34837b8d622ca4018995c?v=78de4ca2037d45c383a4b6ffdfdb8b62", "yali@gmail.com");
    private static final ApiInfo DEFAULT_API_INFO =
            new ApiInfo("Awesome Api", "super awesome", "v-1.0", "urn:tos", DEFAULT_CONTACT, "Apache 2.0", "https://www.apache.org/license/LICENSE-2.0", new ArrayList<>());
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Arrays.asList("application/json", "application/xml"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO)
                                                      .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                                                      .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
}

/**
 * http://localhost:8080/v2/api-docs - 문서
 * http://localhost:8080/swagger-ui/index.html - css 입힌 문서
 * http://localhost:8080/actuator - 모니터링을 위한 문서
 * localhost:8080 루트 접속 시 HAL-explorer 로 이동!
 */