package com.example.practice.controller;

import com.example.practice.dto.ProviderDTO;
import com.example.practice.service.ProviderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProviderController.class)
class ProviderControllerTest {
    @MockBean
    private ProviderService providerService;
    private ProviderDTO providerDTO;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        providerDTO = ProviderDTO
                .builder()
                .id(1L)
                .name("ООО Sony")
                .country("Japan")
                .address("Tokyo, Kioto street, d. 1")
                .email("Sony@mail.ru")
                .build();
    }

    @Test
    void getProvider() throws Exception {
        long id = new Random().nextInt(1, 1000);
        providerDTO.setId(id);
        when(providerService.findProviderById(id)).thenReturn(providerDTO);
        mockMvc.perform(get("/api/v1/provider/" + id))
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void getAllProviders() throws Exception {
        var listProviders = new ArrayList<ProviderDTO>();
        for (long i = 1; i < 100; i++)
            listProviders.add(ProviderDTO
                    .builder()
                    .id(i)
                    .build());
        var jsonProviders = objectMapper.writeValueAsString(listProviders);
        when(providerService.getAllProviders()).thenReturn(listProviders);
        mockMvc.perform(get("/api/v1/provider"))
                .andExpect(content().json(jsonProviders));
    }

    @Test
    void addProvider() throws Exception {
        when(providerService.addProvider(any())).thenReturn(providerDTO);
        var jsonProvider = objectMapper.writeValueAsString(providerDTO);
        mockMvc.perform(post("/api/v1/provider")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProvider))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("ООО Sony"))
                .andExpect(jsonPath("$.country").value("Japan"))
                .andExpect(jsonPath("$.address").value("Tokyo, Kioto street, d. 1"))
                .andExpect(jsonPath("$.email").value("Sony@mail.ru"));
    }

    @Test
    void updateProvider() throws Exception {
        providerDTO.setCountry("Russia");
        when(providerService.updateProvider(any())).thenReturn(providerDTO);
        var jsonProvider = objectMapper.writeValueAsString(providerDTO);
        mockMvc.perform(put("/api/v1/provider")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProvider))
                .andExpect(content().json(jsonProvider));
    }

    @Test
    void deleteProvider() throws Exception {
        mockMvc.perform(delete("/api/v1/provider/1"));
        verify(providerService, times(1)).deleteProvider(1L);
    }
}