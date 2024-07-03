package com.example.practice.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Класс, содержащий информацию об ошибках валидации")
public record Violation(@Schema(description = "Название поля, в котором произошла ошибка") String fieldName,
                        @Schema(description = "Текст сообщения") String message) {
}
