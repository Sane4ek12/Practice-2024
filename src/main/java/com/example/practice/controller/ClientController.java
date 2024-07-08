package com.example.practice.controller;

import com.example.practice.dto.ClientDTO;
import com.example.practice.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
@Tag(name = "Контроллер клиентов", description = "Позволяет выполнять добавление, обновление, " +
        "удаление и поиск клиентов")
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение клиента по id",
            description = "Позволяет получить клиента по его id"
    )
    public ClientDTO getClient(@PathVariable
                               @Min(value = 1, message = "Id не может быть меньше 1")
                               @Parameter(description = "Id клиента")
                               Long id) {
        return clientService.findClientById(id);
    }

    @GetMapping
    @Operation(
            summary = "Получение всех клиентов",
            description = "Позволяет получить список всех клиентов"
    )
    public List<ClientDTO> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping
    @Operation(
            summary = "Добавление клиента",
            description = "Позволяет добавить клиента в БД магазина"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO addClient(@Valid @RequestBody ClientDTO clientDTO) {
        return clientService.addClient(clientDTO);
    }

    @PutMapping
    @Operation(
            summary = "Обновление клиента",
            description = "Позволяет обновить информацию о существующем клиенте"
    )
    public ClientDTO updateClient(@Valid @RequestBody ClientDTO clientDTO) {
        return clientService.updateClient(clientDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление клиента",
            description = "Позволяет удалить существующего клиента из БД"
    )
    public void deleteClient(@PathVariable
                             @Min(value = 1, message = "Id не может быть меньше 1")
                             @Parameter(description = "Id клиента")
                             Long id) {
        clientService.deleteClient(id);
    }
}
