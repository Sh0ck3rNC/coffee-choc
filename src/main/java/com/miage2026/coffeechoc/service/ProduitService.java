package com.miage2026.coffeechoc.service;

import com.miage2026.coffeechoc.exception.ConflictException;
import com.miage2026.coffeechoc.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Produit introuvable avec l'id : " + id));
    }

    public Produit saveProduit(Produit produit) {
        // Vérifie doublon sur le nom
        boolean existe = produitRepository.findAll().stream()
                .anyMatch(p -> p.getNom().equalsIgnoreCase(produit.getNom()));
        if (existe) {
            throw new ConflictException(
                    "Un produit avec le nom '" + produit.getNom() + "' existe déjà.");
        }
        return produitRepository.save(produit);
    }

    public Produit updateProduit(Long id, Produit produitModifie) {
        Produit produit = getProduitById(id);

        // Vérifie doublon sur le nom (en excluant le produit actuel)
        boolean doublon = produitRepository.findAll().stream()
                .anyMatch(p -> p.getNom().equalsIgnoreCase(produitModifie.getNom())
                        && !p.getId().equals(id));
        if (doublon) {
            throw new ConflictException(
                    "Un autre produit avec le nom '" + produitModifie.getNom() + "' existe déjà.");
        }

        produit.setNom(produitModifie.getNom());
        produit.setDescription(produitModifie.getDescription());
        produit.setPrix(produitModifie.getPrix());
        produit.setCategorie(produitModifie.getCategorie());
        produit.setImageUrl(produitModifie.getImageUrl());
        produit.setDisponible(produitModifie.getDisponible());
        return produitRepository.save(produit);
    }

    public void deleteProduit(Long id) {
        // Vérifie que le produit existe avant de supprimer
        getProduitById(id);
        produitRepository.deleteById(id);
    }
}