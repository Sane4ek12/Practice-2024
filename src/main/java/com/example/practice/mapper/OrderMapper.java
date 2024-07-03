package com.example.practice.mapper;

import com.example.practice.dto.OrderDTO;
import com.example.practice.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order dtoToOrder(OrderDTO orderDTO);
    OrderDTO orderToDto(Order order);
}
