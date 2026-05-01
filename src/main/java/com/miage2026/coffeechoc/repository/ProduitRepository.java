package com.miage2026.coffeechoc.repository;

import com.miage2026.coffeechoc.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByDisponibleTrue();
    List<Produit> findByCategorie(String categorie);
}