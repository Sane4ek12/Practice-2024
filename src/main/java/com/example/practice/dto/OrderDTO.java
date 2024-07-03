package com.example.practice.dto;

import com.example.practice.enums.OrderStatus;
import com.example.practice.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO заказа")
public class OrderDTO {
    @Schema(description = "Id заказа")
    private Long id;
    @Schema(description = "Дата заказа")
    private Date date;
    @Schema(description = "Способ оплаты заказа")
    private PaymentMethod paymentMethod;
    @Schema(description = "Статус заказа")
    private OrderStatus orderStatus;
    @Schema(description = "Количество товаров в заказе")
    @Positive(message = "Количество товаров в заказе должно быть больше 0")
    private Integer quantity;
    @Positive(message = "Id клиента не может быть меньше или равен 0")
    @Schema(description = "Id клиента, сделавшего заказ")
    private Long clientId;
    @Positive(message = "Id товара не может быть меньше или равен 0")
    @Schema(description = "Id товара, на который оформлен заказ")
    private Long productId;
}
