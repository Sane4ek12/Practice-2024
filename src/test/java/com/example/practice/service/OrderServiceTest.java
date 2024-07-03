package com.example.practice.service;

import com.example.practice.dto.OrderDTO;
import com.example.practice.enums.OrderStatus;
import com.example.practice.enums.PaymentMethod;
import com.example.practice.exception.NotEnoughMoneyException;
import com.example.practice.exception.NotEnoughQuantityException;
import com.example.practice.mapper.OrderMapperImpl;
import com.example.practice.model.Client;
import com.example.practice.model.Order;
import com.example.practice.model.Product;
import com.example.practice.repository.ClientRepository;
import com.example.practice.repository.OrderRepository;
import com.example.practice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapperImpl orderMapper;
    private OrderDTO orderDTO;

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
    void addOrderButNotEnoughMoney() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(Product
                .builder()
                .id(1L)
                .price(3000)
                .quantity(30)
                .providerId(1L)
                .build()));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(Client
                .builder()
                .id(1L)
                .balance(10000)
                .build()));
        when(orderMapper.dtoToOrder(orderDTO)).thenReturn(Order
                .builder()
                .id(1L)
                .date(new Date(999999))
                .paymentMethod(PaymentMethod.Online)
                .orderStatus(OrderStatus.Awaiting)
                .quantity(5)
                .clientId(1L)
                .productId(1L)
                .build());
        assertThrows(NotEnoughMoneyException.class, () -> orderService.addOrder(orderDTO));
    }

    @Test
    void addOrderButNotEnoughQuantity() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(Product
                .builder()
                .id(1L)
                .price(3000)
                .quantity(2)
                .providerId(1L)
                .build()));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(Client
                .builder()
                .id(1L)
                .balance(10000)
                .build()));
        when(orderMapper.dtoToOrder(orderDTO)).thenReturn(Order
                .builder()
                .id(1L)
                .date(new Date(999999))
                .paymentMethod(PaymentMethod.Online)
                .orderStatus(OrderStatus.Awaiting)
                .quantity(3)
                .clientId(1L)
                .productId(1L)
                .build());
        assertThrows(NotEnoughQuantityException.class, () -> orderService.addOrder(orderDTO));
    }

    @Test
    void addOrderSuccessfully() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(Product
                .builder()
                .id(1L)
                .price(3000)
                .quantity(30)
                .providerId(1L)
                .build()));
        when(clientRepository.findById(1L)).thenReturn(Optional.of(Client
                .builder()
                .id(1L)
                .balance(20000)
                .build()));
        when(orderMapper.dtoToOrder(orderDTO)).thenReturn(Order
                .builder()
                .id(1L)
                .date(new Date(999999))
                .paymentMethod(PaymentMethod.Online)
                .orderStatus(OrderStatus.Awaiting)
                .quantity(5)
                .clientId(1L)
                .productId(1L)
                .build());
        var product = productRepository.findById(1L).get();
        var client = clientRepository.findById(1L).get();
        orderService.addOrder(orderDTO);
        assertEquals(product.getQuantity(), 25);
        assertEquals(client.getBalance(), 5000);
    }

    @Test
    void cancelOrderAndCheckQuantityAndBalance() {
        when(orderRepository.findById(orderDTO.getId())).thenReturn(Optional.of(Order
                .builder()
                .id(1L)
                .date(new Date(999999))
                .paymentMethod(PaymentMethod.Online)
                .orderStatus(OrderStatus.Awaiting)
                .quantity(3)
                .clientId(1L)
                .productId(1L)
                .client(Client
                        .builder()
                        .id(1L)
                        .balance(10000)
                        .build())
                .product(Product
                        .builder()
                        .id(1L)
                        .price(3000)
                        .quantity(2)
                        .providerId(1L)
                        .build())
                .build()));
        orderDTO.setOrderStatus(OrderStatus.Cancelled);
        var order = orderRepository.findById(orderDTO.getId()).get();
        orderService.updateOrder(orderDTO);
        assertEquals(order.getProduct().getQuantity(), 5);
        assertEquals(order.getClient().getBalance(), 19000);
    }

    @Test
    void notCancelOrderAndCheckQuantityAndBalance() {
        when(orderRepository.findById(orderDTO.getId())).thenReturn(Optional.of(Order
                .builder()
                .id(1L)
                .date(new Date(999999))
                .paymentMethod(PaymentMethod.Online)
                .orderStatus(OrderStatus.Awaiting)
                .quantity(3)
                .clientId(1L)
                .productId(1L)
                .client(Client
                        .builder()
                        .id(1L)
                        .balance(10000)
                        .build())
                .product(Product
                        .builder()
                        .id(1L)
                        .price(3000)
                        .quantity(2)
                        .providerId(1L)
                        .build())
                .build()));
        orderDTO.setOrderStatus(OrderStatus.Finished);
        var order = orderRepository.findById(orderDTO.getId()).get();
        orderService.updateOrder(orderDTO);
        assertEquals(order.getProduct().getQuantity(), 2);
        assertEquals(order.getClient().getBalance(), 10000);
    }
}