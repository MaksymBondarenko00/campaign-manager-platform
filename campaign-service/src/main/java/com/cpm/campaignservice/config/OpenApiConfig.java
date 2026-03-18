package com.cpm.campaignservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI campaignServiceApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Campaign Service API")
                        .description("Advertising campaign management service")
                        .version("1.0"));
    }

}