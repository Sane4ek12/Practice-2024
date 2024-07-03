package com.example.practice.controller;

import com.example.practice.dto.ProductDTO;
import com.example.practice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @MockBean
    private ProductService productService;
    private ProductDTO productDTO;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productDTO = ProductDTO
                .builder()
                .id(1L)
                .name("Монитор Sony")
                .price(5000)
                .productionYear(new Date(999999))
                .warrantyPeriod(2.5)
                .description("Монитор Sony.")
                .quantity(100)
                .providerId(1L)
                .build();
    }

    @Test
    void addProduct() throws Exception {
        when(productService.addProduct(any())).thenReturn(productDTO);
        var jsonProduct = objectMapper.writeValueAsString(productDTO);
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Монитор Sony"))
                .andExpect(jsonPath("$.price").value(5000))
                .andExpect(jsonPath("$.description").value("Монитор Sony."))
                .andExpect(jsonPath("$.quantity").value(100));
    }

    @Test
    void getProduct() throws Exception {
        long id = new Random().nextInt(1, 1000);
        productDTO.setId(id);
        when(productService.getProductById(id)).thenReturn(productDTO);
        mockMvc.perform(get("/api/v1/product/" + id))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void getAllProductsByProvider() throws Exception {
        long id = new Random().nextInt(1, 1000);
        var listProducts = new ArrayList<ProductDTO>();
        for (long i = 1; i < 100; i++)
            listProducts.add(ProductDTO
                    .builder()
                    .id(i)
                    .providerId(id)
                    .build());
        var jsonProducts = objectMapper.writeValueAsString(listProducts);
        when(productService.getAllProductsByProviderId(id)).thenReturn(listProducts);
        mockMvc.perform(get("/api/v1/product/provider/" + id))
                .andExpect(content().json(jsonProducts))
                .andExpect(jsonPath("$[1].providerId").value(id));
    }

    @Test
    void getAllProductsByPartOfName() throws Exception {
        var names = List.of("Сабвуфер Sony", "Плита Polaris", "Стиралка Bosh");
        var listProducts = new ArrayList<ProductDTO>();
        for (long i = 1; i < 4; i++)
            listProducts.add(ProductDTO
                    .builder()
                    .id(i)
                    .name(names.get((int) i-1))
                    .build());
        int index = new Random().nextInt(0, 3);
        String name = names.get(index).split(" ")[0];
        when(productService.getAllProductsByPartOfName(name)).thenReturn(List.of(listProducts
                .get(index)));
        var jsonProduct = objectMapper.writeValueAsString(listProducts.get(index));
        mockMvc.perform(get("/api/v1/product/name?name=" + name))
                .andExpect(content().json("[" + jsonProduct + "]"))
                .andExpect(jsonPath("$[0].name").value(names.get(index)));
    }

    @Test
    void getAllProducts() throws Exception {
        var listProducts = new ArrayList<ProductDTO>();
        for (long i = 1; i < 100; i++)
            listProducts.add(ProductDTO
                    .builder()
                    .id(i)
                    .build());
        var jsonProducts = objectMapper.writeValueAsString(listProducts);
        when(productService.getAllProducts()).thenReturn(listProducts);
        mockMvc.perform(get("/api/v1/product"))
                .andExpect(content().json(jsonProducts));
    }

    @Test
    void updateProduct() throws Exception {
        productDTO.setPrice(7000);
        productDTO.setQuantity(150);
        when(productService.updateProduct(any())).thenReturn(productDTO);
        var jsonProduct = objectMapper.writeValueAsString(productDTO);
        mockMvc.perform(put("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(content().json(jsonProduct));
    }

    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/api/v1/product/1"));
        verify(productService, times(1)).deleteProduct(1L);
    }
}