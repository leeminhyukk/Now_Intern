package org.example.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        String jwt = "JWT";

        // SecurityRequirement 설정
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);

        // SecurityScheme 설정
        Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
                .name(jwt)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        );

        // OpenAPI 설정 반환
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components);  // 두 번째 components 설정 제거
    }

    // API Info 설정
    private Info apiInfo() {
        return new Info()
                .title("Now_Intern")
                .description("백엔드 개발 과제 (Java)")
                .version("1.0.0");
    }
}
