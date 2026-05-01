package com.miage2026.coffeechoc.service;

import com.miage2026.coffeechoc.model.Produit;
import com.miage2026.coffeechoc.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitRepository produitRepository;

    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    public List<Produit> getProduitsDisponibles() {
        return produitRepository.findByDisponibleTrue();
    }

    public List<Produit> getProduitsByCategorie(String categorie) {
        return produitRepository.findByCategorie(categorie);
    }

    public Produit getProduitById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable : " + id));
    }

    public Produit saveProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    public Produit updateProduit(Long id, Produit produitModifie) {
        Produit produit = getProduitById(id);
        produit.setNom(produitModifie.getNom());
        produit.setDescription(produitModifie.getDescription());
        produit.setPrix(produitModifie.getPrix());
        produit.setCategorie(produitModifie.getCategorie());
        produit.setImageUrl(produitModifie.getImageUrl());
        produit.setDisponible(produitModifie.getDisponible());
        return produitRepository.save(produit);
    }

    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }
}