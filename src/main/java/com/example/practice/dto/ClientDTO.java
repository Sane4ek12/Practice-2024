package com.example.practice.dto;

import com.example.practice.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO клиента")
public class ClientDTO {
    @Schema(description = "Id клиента")
    private Long id;
    @NotBlank(message = "Имя не должно быть пустым")
    @Schema(description = "Имя клиента")
    private String firstName;
    @NotBlank(message = "Фамилия не должна быть пустой")
    @Schema(description = "Фамилия клиента")
    private String lastName;
    @NotNull(message = "Укажите пол")
    @Schema(description = "Пол клиента")
    private Gender gender;
    @NotNull(message = "Укажите дату рождения")
    @Schema(description = "Дата рождения клиента")
    private Date birthdate;
    @Pattern(regexp = "8[(]\\d{3}[)]-\\d{3}-\\d{2}-\\d{2}",
            message = "Пожалуйста, введите номер в указанном формате: 8(XXX)-XXX-XX-XX")
    @Schema(description = "Телефон клиента")
    private String phone;
    @Email(message = "Введите корректный email")
    @Schema(description = "E-mail клиента")
    private String email;
    @PositiveOrZero(message = "Баланс не может быть отрицательным")
    @NotNull(message = "Укажите баланс")
    @Schema(description = "Баланс клиента")
    private Integer balance;
}
