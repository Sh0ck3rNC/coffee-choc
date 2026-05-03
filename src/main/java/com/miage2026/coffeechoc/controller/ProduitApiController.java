package com.miage2026.coffeechoc.controller;

import com.miage2026.coffeechoc.dto.ApiResponse;
import com.miage2026.coffeechoc.model.Produit;
import com.miage2026.coffeechoc.service.ProduitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
@Tag(name = "Produits", description = "Gestion des produits du menu")
public class ProduitApiController {

    private final ProduitService produitService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Produit>>> getAll() {
        List<Produit> produits = produitService.getAllProduits();
        return ResponseEntity.ok(ApiResponse.success(
                produits.size() + " produit(s) trouvé(s)", produits));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Produit>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
                "Produit trouvé", produitService.getProduitById(id)));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<ApiResponse<List<Produit>>> getDisponibles() {
        List<Produit> produits = produitService.getProduitsDisponibles();
        return ResponseEntity.ok(ApiResponse.success(
                produits.size() + " produit(s) disponible(s)", produits));
    }

    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<ApiResponse<List<Produit>>> getByCategorie(@PathVariable String categorie) {
        List<Produit> produits = produitService.getProduitsByCategorie(categorie);
        return ResponseEntity.ok(ApiResponse.success(
                produits.size() + " produit(s) dans la catégorie " + categorie, produits));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Produit>> create(@RequestBody Produit produit) {
        Produit created = produitService.saveProduit(produit);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Produit créé avec succès", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Produit>> update(@PathVariable Long id,
                                                       @RequestBody Produit produit) {
        Produit updated = produitService.updateProduit(id, produit);
        return ResponseEntity.ok(ApiResponse.success("Produit mis à jour avec succès", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.ok(ApiResponse.success("Produit supprimé avec succès"));
    }
}