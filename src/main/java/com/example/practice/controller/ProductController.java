package com.example.practice.controller;

import com.example.practice.dto.ProductDTO;
import com.example.practice.service.ProductService;
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
@RequestMapping("/api/v1/product")
@Tag(name = "Контроллер товаров", description = "Позволяет выполнять добавление, обновление, " +
        "удаление и поиск товаров по разным критериям")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Operation(
            summary = "Добавление товара",
            description = "Позволяет добавить товар в БД магазина"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO addProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение товара по id",
            description = "Позволяет получить товар по его id"
    )
    public ProductDTO getProduct(@PathVariable
                                 @Min(value = 1, message = "Id не может быть меньше 1")
                                 @Parameter(description = "Id товара")
                                 Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/provider/{id}")
    @Operation(
            summary = "Получение всех товаров по поставщику",
            description = "Позволяет получить список всех товаров по id поставщика"
    )
    public List<ProductDTO> getAllProductsByProvider(@PathVariable
                                                     @Min(value = 1, message = "Id не может быть меньше 1")
                                                     @Parameter(description = "Id поставщика")
                                                     Long id) {
        return productService.getAllProductsByProviderId(id);
    }

    @GetMapping("/name")
    @Operation(
            summary = "Получение всех товаров по наименованию",
            description = "Позволяет получить список всех товаров по слову в названии" +
                    " (вводим 'наушники' и получаем список всех наушников)"
    )
    public List<ProductDTO> getAllProductsByPartOfName(@RequestParam("name")
                                                       @Parameter(description = "Название товара")
                                                       String name) {
        return productService.getAllProductsByPartOfName(name);
    }

    @GetMapping
    @Operation(
            summary = "Получение всех товаров",
            description = "Позволяет получить список всех товаров"
    )
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping
    @Operation(
            summary = "Обновление товара",
            description = "Позволяет обновить существующий товар"
    )
    public ProductDTO updateProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(productDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление товара",
            description = "Позволяет удалить существующий товар из БД"
    )
    public void deleteProduct(@PathVariable
                              @Min(value = 1, message = "Id не может быть меньше 1")
                              @Parameter(description = "Id товара")
                              Long id) {
        productService.deleteProduct(id);
    }
}
