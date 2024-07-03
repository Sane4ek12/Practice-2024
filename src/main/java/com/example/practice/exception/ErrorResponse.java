package com.example.practice.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Кастомный класс для отправки сообщений об ошибках")
public record ErrorResponse(@Schema(description = "Код ответа") int statusCode,
                            @Schema(description = "Текст сообщения") String message,
                            @Schema(description = "Список записей ошибок валидации в полях")
                            List<Violation> violations){
}
