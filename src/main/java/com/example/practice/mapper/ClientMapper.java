package com.example.practice.mapper;

import com.example.practice.dto.ClientDTO;
import com.example.practice.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client dtoToClient(ClientDTO clientDTO);
    ClientDTO clientToDto(Client client);
}
