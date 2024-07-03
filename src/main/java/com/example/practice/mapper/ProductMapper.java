package com.example.practice.mapper;

import com.example.practice.dto.ProductDTO;
import com.example.practice.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product dtoToProduct(ProductDTO productDTO);
    ProductDTO productToDto(Product product);
}
