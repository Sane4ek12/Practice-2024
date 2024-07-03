package com.example.practice.service;

import com.example.practice.dto.OrderDTO;
import com.example.practice.enums.OrderStatus;
import com.example.practice.exception.NotEnoughMoneyException;
import com.example.practice.exception.NotEnoughQuantityException;
import com.example.practice.mapper.OrderMapperImpl;
import com.example.practice.repository.ClientRepository;
import com.example.practice.repository.OrderRepository;
import com.example.practice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapperImpl orderMapper;

    public OrderDTO addOrder(OrderDTO orderDTO) {
        var order = orderMapper.dtoToOrder(orderDTO);
        var product = productRepository.findById(order.getProductId()).get();
        var client = clientRepository.findById(order.getClientId()).get();
        if(product.getQuantity() < order.getQuantity())
            throw new NotEnoughQuantityException("Недостаточно товара на складе! Сейчас на складе - "
                    + product.getQuantity() + ", необходимо - " + order.getQuantity());
        if(client.getBalance() < product.getPrice() * order.getQuantity())
            throw new NotEnoughMoneyException("Недостаточно средств на счете! Текущий баланс - "
                    + client.getBalance() + ", необходимо - "
                    + product.getPrice() * order.getQuantity());
        client.setBalance(client.getBalance() - product.getPrice() * order.getQuantity());
        product.setQuantity(product.getQuantity() - order.getQuantity());
        clientRepository.save(client);
        productRepository.save(product);
        return orderMapper
                .orderToDto(orderRepository
                        .save(order));
    }

    public OrderDTO getOrderById(Long id) {
        return orderMapper
                .orderToDto(orderRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Заказ с id = " + id + " не найден")));
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository
                .findAll()
                .stream()
                .map(orderMapper::orderToDto)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrdersByClientId(Long id) {
        return orderRepository
                .findAllByClientId(id)
                .stream()
                .map(orderMapper::orderToDto)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrdersByProductId(Long id) {
        return orderRepository
                .findAllByProductId(id)
                .stream()
                .map(orderMapper::orderToDto)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrdersByOrderStatus(OrderStatus orderStatus) {
        return orderRepository
                .findAllByOrderStatus(orderStatus)
                .stream()
                .map(orderMapper::orderToDto)
                .collect(Collectors.toList());
    }

    public OrderDTO updateOrder(OrderDTO orderDTO) {
        var order = orderRepository.findById(orderDTO.getId()).get();
        var product = order.getProduct();
        var client = order.getClient();
        if (orderDTO.getOrderStatus() == OrderStatus.Cancelled) {
            product.setQuantity(product.getQuantity() + order.getQuantity());
            client.setBalance(client.getBalance() + product.getPrice() * order.getQuantity());
            clientRepository.save(client);
            productRepository.save(product);
        }
        return orderMapper
                .orderToDto(orderRepository
                        .save(orderMapper
                                .dtoToOrder(orderDTO)));
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
