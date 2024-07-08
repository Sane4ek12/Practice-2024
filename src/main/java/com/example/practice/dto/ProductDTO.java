package com.example.practice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO товара")
public class ProductDTO {
    @Schema(description = "ID товара")
    private Long id;
    @NotBlank(message = "Название продукта не должно быть пустым")
    @Schema(description = "Название товара")
    private String name;
    @Positive(message = "Цена товара должна быть положительной")
    @NotNull(message = "Укажите цену товара")
    @Schema(description = "Цена товара")
    private Integer price;
    @NotNull(message = "Укажите год производства")
    @Schema(description = "Год производства")
    private Date productionYear;
    @Positive(message = "Гарантийный срок не может быть отрицательным")
    @Schema(description = "Гарантийный период")
    private Double warrantyPeriod;
    @Schema(description = "Описание товара")
    private String description;
    @PositiveOrZero(message = "Количество товара не может быть отрицательным")
    @NotNull(message = "Укажите количество товара")
    @Schema(description = "Количество товара в наличии")
    private Integer quantity;
    @Positive(message = "Укажите id компании поставщика")
    @Schema(description = "ID поставщика")
    private Long providerId;
}
