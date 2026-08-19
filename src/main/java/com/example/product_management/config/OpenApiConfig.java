package com.example.product_management.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "basicAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("Hệ thống quản lý sản phẩm API")
                        .version("1.0.0")
                        .description("Tài liệu tích hợp xác thực Basic Auth"))

                // 2. Khai báo cơ chế bảo mật (Security Scheme) là HTTP Basic
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")))

                // 3. Yêu cầu toàn bộ các API trong hệ thống phải sử dụng scheme bảo mật này
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }
}
