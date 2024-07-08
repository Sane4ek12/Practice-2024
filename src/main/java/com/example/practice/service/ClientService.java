package com.example.practice.service;

import com.example.practice.dto.ClientDTO;
import com.example.practice.mapper.ClientMapperImpl;
import com.example.practice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapperImpl clientMapper;

    public ClientDTO addClient(ClientDTO clientDTO) {
        var client = clientMapper.dtoToClient(clientDTO);
        return clientMapper.clientToDto(clientRepository.save(client));
    }

    public ClientDTO findClientById(Long id) {
        return clientMapper
                .clientToDto(clientRepository
                        .findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Клиент с id = " + id + " не найден")));
    }

    public List<ClientDTO> getAllClients() {
        return clientRepository
                .findAll()
                .stream()
                .map(clientMapper::clientToDto)
                .collect(Collectors.toList());
    }

    public ClientDTO updateClient(ClientDTO clientDTO) {
        var client = clientMapper.dtoToClient(clientDTO);
        return clientMapper.clientToDto(clientRepository.save(client));
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
