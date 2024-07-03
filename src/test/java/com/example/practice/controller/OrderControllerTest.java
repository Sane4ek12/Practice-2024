package com.example.practice.controller;

import com.example.practice.dto.OrderDTO;
import com.example.practice.enums.OrderStatus;
import com.example.practice.enums.PaymentMethod;
import com.example.practice.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @MockBean
    private OrderService orderService;
    private OrderDTO orderDTO;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        orderDTO = OrderDTO
                .builder()
                .id(1L)
                .date(new Date(999999))
                .paymentMethod(PaymentMethod.Online)
                .orderStatus(OrderStatus.Awaiting)
                .quantity(5)
                .clientId(1L)
                .productId(1L)
                .build();
    }

    @Test
    void addOrder() throws Exception {
        when(orderService.addOrder(any())).thenReturn(orderDTO);
        var jsonOrder = objectMapper.writeValueAsString(orderDTO);
        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOrder))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.paymentMethod").value("Online"))
                .andExpect(jsonPath("$.orderStatus").value("Awaiting"))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    void getOrder() throws Exception {
        long id = new Random().nextInt(1, 1000);
        orderDTO.setId(id);
        when(orderService.getOrderById(id)).thenReturn(orderDTO);
        mockMvc.perform(get("/api/v1/order/" + id))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void getAllOrdersByClient() throws Exception {
        long id = new Random().nextInt(1, 1000);
        var listOrders = new ArrayList<OrderDTO>();
        for (long i = 1; i < 100; i++)
            listOrders.add(OrderDTO
                    .builder()
                    .id(i)
                    .clientId(id)
                    .build());
        var jsonOrders = objectMapper.writeValueAsString(listOrders);
        when(orderService.getAllOrdersByClientId(id)).thenReturn(listOrders);
        mockMvc.perform(get("/api/v1/order/client/" + id))
                .andExpect(content().json(jsonOrders))
                .andExpect(jsonPath("$[1].clientId").value(id));
    }

    @Test
    void getAllOrdersByProduct() throws Exception {
        long id = new Random().nextInt(1, 1000);
        var listOrders = new ArrayList<OrderDTO>();
        for (long i = 1; i < 100; i++)
            listOrders.add(OrderDTO
                    .builder()
                    .id(i)
                    .productId(id)
                    .build());
        var jsonOrders = objectMapper.writeValueAsString(listOrders);
        when(orderService.getAllOrdersByProductId(id)).thenReturn(listOrders);
        mockMvc.perform(get("/api/v1/order/product/" + id))
                .andExpect(content().json(jsonOrders))
                .andExpect(jsonPath("$[1].productId").value(id));
    }

    @Test
    void getAllOrdersByStatus() throws Exception {
        var listOrders = new ArrayList<OrderDTO>();
        for (long i = 1; i < 100; i++)
            listOrders.add(OrderDTO
                    .builder()
                    .id(i)
                    .orderStatus(OrderStatus.Processed)
                    .build());
        var jsonOrders = objectMapper.writeValueAsString(listOrders);
        when(orderService.getAllOrdersByOrderStatus(any())).thenReturn(listOrders);
        mockMvc.perform(get("/api/v1/order/status/Processed"))
                .andExpect(content().json(jsonOrders))
                .andExpect(jsonPath("$[1].orderStatus").value("Processed"));
    }

    @Test
    void getAllOrders() throws Exception {
        var listOrders = new ArrayList<OrderDTO>();
        for (long i = 1; i < 100; i++)
            listOrders.add(OrderDTO
                    .builder()
                    .id(i)
                    .build());
        var jsonOrders = objectMapper.writeValueAsString(listOrders);
        when(orderService.getAllOrders()).thenReturn(listOrders);
        mockMvc.perform(get("/api/v1/order"))
                .andExpect(content().json(jsonOrders));
    }

    @Test
    void updateOrder() throws Exception {
        orderDTO.setOrderStatus(OrderStatus.Processed);
        orderDTO.setPaymentMethod(PaymentMethod.Personal);
        when(orderService.updateOrder(any())).thenReturn(orderDTO);
        var jsonOrder = objectMapper.writeValueAsString(orderDTO);
        mockMvc.perform(put("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOrder))
                .andExpect(content().json(jsonOrder));
    }

    @Test
    void deleteOrder() throws Exception {
        mockMvc.perform(delete("/api/v1/order/1"));
        verify(orderService, times(1)).deleteOrder(1L);
    }
}