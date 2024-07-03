package com.example.practice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "API интернет - магазина",
                description = "API, позволяющий управлять интернет - магазином: " +
                        "(создавать заказы товаров, добавлять, изменять, удалять товары, клиентов, " +
                        "поставщиков и т.д.)",
                version = "1.0.0"
        )
)
public class SwaggerConfig {
}
