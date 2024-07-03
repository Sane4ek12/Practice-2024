package com.example.practice.mapper;

import com.example.practice.dto.ProviderDTO;
import com.example.practice.model.Provider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProviderMapper {
    Provider dtoToProvider(ProviderDTO providerDTO);
    ProviderDTO providerToDto(Provider provider);
}
