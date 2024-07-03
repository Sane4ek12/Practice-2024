package com.example.practice.service;

import com.example.practice.dto.ProductDTO;
import com.example.practice.mapper.ProductMapperImpl;
import com.example.practice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapperImpl productMapper;

    public ProductDTO addProduct(ProductDTO productDTO) {
        return productMapper
                .productToDto(productRepository
                        .save(productMapper
                                .dtoToProduct(productDTO)));
    }

    public ProductDTO getProductById(Long id) {
        return productMapper
                .productToDto(productRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Товар с id = " + id + " не найден")));
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(productMapper::productToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProductsByProviderId(Long id) {
        return productRepository
                .findAllByProviderId(id)
                .stream()
                .map(productMapper::productToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProductsByPartOfName(String name) {
        return productRepository
                .findAll()
                .stream()
                .filter(product -> product
                        .getName()
                        .toLowerCase()
                        .contains(name.
                                toLowerCase()))
                .map(productMapper::productToDto)
                .collect(Collectors.toList());
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        return productMapper
                .productToDto(productRepository
                        .save(productMapper
                                .dtoToProduct(productDTO)));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
