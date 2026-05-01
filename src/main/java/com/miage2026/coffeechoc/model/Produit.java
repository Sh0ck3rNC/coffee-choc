package com.miage2026.coffeechoc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "produits")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Double prix;

    @Column(nullable = false)
    private String categorie;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean disponible = true;
}