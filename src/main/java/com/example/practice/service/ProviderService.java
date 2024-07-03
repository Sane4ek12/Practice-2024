package com.example.practice.service;

import com.example.practice.dto.ProviderDTO;
import com.example.practice.mapper.ProviderMapperImpl;
import com.example.practice.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProviderService {
    private final ProviderRepository providerRepository;
    private final ProviderMapperImpl providerMapper;

    public ProviderDTO findProviderById(Long id) {
        return providerMapper
                .providerToDto(providerRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Поставщик с id = " + id + " не найден")));
    }

    public List<ProviderDTO> getAllProviders() {
        return providerRepository
                .findAll()
                .stream()
                .map(providerMapper::providerToDto)
                .collect(Collectors.toList());
    }

    public ProviderDTO addProvider(ProviderDTO providerDTO) {
        return providerMapper
                .providerToDto(providerRepository
                        .save(providerMapper
                                .dtoToProvider(providerDTO)));
    }

    public ProviderDTO updateProvider(ProviderDTO providerDTO) {
        return providerMapper
                .providerToDto(providerRepository
                        .save(providerMapper
                                .dtoToProvider(providerDTO)));
    }

    public void deleteProvider(Long id) {
        providerRepository.deleteById(id);
    }
}
