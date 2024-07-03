package com.example.practice.controller;

import com.example.practice.dto.ClientDTO;
import com.example.practice.enums.Gender;
import com.example.practice.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ClientController.class)
class ClientControllerTest {
    @MockBean
    private ClientService clientService;
    private ClientDTO clientDTO;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        clientDTO = ClientDTO
                .builder()
                .id(1L)
                .firstName("Вася")
                .lastName("Васечкин")
                .gender(Gender.Male)
                .birthdate(new Date(999999))
                .phone("8(800)-555-35-35")
                .email("Vasya@gmail.com")
                .balance(65000)
                .build();
    }

    @Test
    void getClient() throws Exception {
        long id = new Random().nextInt(1, 1000);
        clientDTO.setId(id);
        when(clientService.findClientById(id)).thenReturn(clientDTO);
        mockMvc.perform(get("/api/v1/client/" + id))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void getAllClients() throws Exception {
        var listClients = new ArrayList<ClientDTO>();
        for (long i = 1; i < 100; i++)
            listClients.add(ClientDTO
                    .builder()
                    .id(i)
                    .build());
        var jsonClients = objectMapper.writeValueAsString(listClients);
        when(clientService.getAllClients()).thenReturn(listClients);
        mockMvc.perform(get("/api/v1/client"))
                .andExpect(content().json(jsonClients));
    }

    @Test
    void addClient() throws Exception {
        when(clientService.addClient(any())).thenReturn(clientDTO);
        var jsonClient = objectMapper.writeValueAsString(clientDTO);
        mockMvc.perform(post("/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonClient))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Вася"))
                .andExpect(jsonPath("$.lastName").value("Васечкин"))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.balance").value(65000));
    }

    @Test
    void updateClient() throws Exception {
        clientDTO.setBalance(75000);
        when(clientService.updateClient(any())).thenReturn(clientDTO);
        var jsonClient = objectMapper.writeValueAsString(clientDTO);
        mockMvc.perform(put("/api/v1/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonClient))
                .andExpect(content().json(jsonClient));
    }

    @Test
    void deleteClient() throws Exception {
        mockMvc.perform(delete("/api/v1/client/1"));
        verify(clientService, times(1)).deleteClient(1L);
    }
}