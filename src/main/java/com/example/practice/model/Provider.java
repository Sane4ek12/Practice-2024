package com.example.practice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "provider")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "country")
    private String country;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "provider", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Product> productList;
}
