package com.example.practice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO поставщика")
public class ProviderDTO {
    @Schema(description = "Id поставщика")
    private Long id;
    @NotBlank(message = "Название организации не должно быть пустым")
    @Schema(description = "Название фирмы поставщика")
    private String name;
    @NotBlank(message = "Укажите страну производителя")
    @Schema(description = "Страна поставщика")
    private String country;
    @NotBlank(message = "Введите адрес производителя")
    @Schema(description = "Адрес поставщика")
    private String address;
    @Email(message = "Введите корректный email")
    @Schema(description = "E-mail поставщика")
    private String email;
}
