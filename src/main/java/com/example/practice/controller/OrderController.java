package com.example.practice.controller;

import com.example.practice.dto.OrderDTO;
import com.example.practice.enums.OrderStatus;
import com.example.practice.service.OrderService;
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
@RequestMapping("/api/v1/order")
@Tag(name = "Контроллер заказов", description = "Позволяет выполнять добавление, обновление, " +
        "удаление и поиск заказов")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(
            summary = "Добавление заказа",
            description = "Позволяет добавить заказ в БД магазина"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO addOrder(@Valid @RequestBody OrderDTO orderDTO) {
        return orderService.addOrder(orderDTO);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение заказа по id",
            description = "Позволяет получить заказ по его id"
    )
    public OrderDTO getOrder(@PathVariable
                             @Min(value = 1, message = "Id не может быть меньше 1")
                             @Parameter(description = "Id заказа")
                             Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/client/{id}")
    @Operation(
            summary = "Получение заказов клиента",
            description = "Позволяет получить список заказов по id клиента"
    )
    public List<OrderDTO> getAllOrdersByClient(@PathVariable
                                               @Min(value = 1, message = "Id не может быть меньше 1")
                                               @Parameter(description = "Id клиента")
                                               Long id) {
        return orderService.getAllOrdersByClientId(id);
    }

    @GetMapping("/product/{id}")
    @Operation(
            summary = "Получение заказов по товару",
            description = "Позволяет получить список заказов по id товара"
    )
    public List<OrderDTO> getAllOrdersByProduct(@PathVariable
                                                @Min(value = 1, message = "Id не может быть меньше 1")
                                                @Parameter(description = "Id товара")
                                                Long id) {
        return orderService.getAllOrdersByProductId(id);
    }

    @GetMapping("/status/{orderStatus}")
    @Operation(
            summary = "Получение заказов по их статусу",
            description = "Позволяет получить список заказов с определенным статусом"
    )
    public List<OrderDTO> getAllOrdersByStatus(@PathVariable
                                               @Parameter(description = "Статус заказа")
                                               OrderStatus orderStatus) {
        return orderService.getAllOrdersByOrderStatus(orderStatus);
    }

    @GetMapping
    @Operation(
            summary = "Получение всех заказов",
            description = "Позволяет получить список всех заказов"
    )
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping
    @Operation(
            summary = "Обновление заказа",
            description = "Позволяет обновить информацию о существующем заказе"
    )
    public OrderDTO updateOrder(@Valid @RequestBody OrderDTO orderDTO) {
        return orderService.updateOrder(orderDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление заказа",
            description = "Позволяет удалить существующий заказ из БД"
    )
    public void deleteOrder(@PathVariable
                            @Min(value = 1, message = "Id не может быть меньше 1")
                            @Parameter(description = "Id заказа")
                            Long id) {
        orderService.deleteOrder(id);
    }
}
