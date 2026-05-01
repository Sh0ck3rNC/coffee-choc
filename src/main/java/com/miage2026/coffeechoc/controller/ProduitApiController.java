package com.miage2026.coffeechoc.controller;

import com.miage2026.coffeechoc.model.Produit;
import com.miage2026.coffeechoc.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
@Tag(name = "Produits", description = "Gestion des produits du menu")
public class ProduitApiController {

    private final ProduitService produitService;

    @GetMapping
    public List<Produit> getAll() {
        return produitService.getAllProduits();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getById(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.getProduitById(id));
    }

    @GetMapping("/disponibles")
    public List<Produit> getDisponibles() {
        return produitService.getProduitsDisponibles();
    }

    @GetMapping("/categorie/{categorie}")
    public List<Produit> getByCategorie(@PathVariable String categorie) {
        return produitService.getProduitsByCategorie(categorie);
    }

    @PostMapping
    public ResponseEntity<Produit> create(@RequestBody Produit produit) {
        return ResponseEntity.ok(produitService.saveProduit(produit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> update(@PathVariable Long id, @RequestBody Produit produit) {
        return ResponseEntity.ok(produitService.updateProduit(id, produit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}