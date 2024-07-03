package com.example.practice.controller;

import com.example.practice.dto.ProviderDTO;
import com.example.practice.service.ProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/provider")
@Tag(name = "Контроллер поставщиков", description = "Позволяет выполнять добавление, обновление, " +
        "удаление и поиск поставщиков товаров магазина")
public class ProviderController {
    private final ProviderService providerService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение поставщика по id",
            description = "Позволяет получить поставщика по его id"
    )
    public ProviderDTO getProvider(@PathVariable
                                   @Min(value = 1, message = "Id не может быть меньше 1")
                                   @Parameter(description = "Id поставщика")
                                   Long id) {
        return providerService.findProviderById(id);
    }

    @GetMapping
    @Operation(
            summary = "Получение всех поставщиков",
            description = "Позволяет получить список всех поставщиков"
    )
    public List<ProviderDTO> getAllProviders() {
        return providerService.getAllProviders();
    }

    @PostMapping
    @Operation(
            summary = "Добавление поставщика",
            description = "Позволяет добавить поставщика в БД магазина"
    )
    public ProviderDTO addProvider(@Valid @RequestBody ProviderDTO providerDTO) {
        return providerService.addProvider(providerDTO);
    }

    @PutMapping
    @Operation(
            summary = "Обновление поставщика",
            description = "Позволяет обновить информацию о существующем поставщике"
    )
    public ProviderDTO updateProvider(@Valid @RequestBody ProviderDTO providerDTO) {
        return providerService.updateProvider(providerDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление поставщика",
            description = "Позволяет удалить существующего поставщика из БД"
    )
    public void deleteProvider(@PathVariable
                               @Min(value = 1, message = "Id не может быть меньше 1")
                               @Parameter(description = "Id поставщика")
                               Long id) {
        providerService.deleteProvider(id);
    }
}
