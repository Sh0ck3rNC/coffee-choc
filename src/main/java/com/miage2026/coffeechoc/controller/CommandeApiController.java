package com.miage2026.coffeechoc.controller;

import com.miage2026.coffeechoc.dto.ApiResponse;
import com.miage2026.coffeechoc.model.Commande;
import com.miage2026.coffeechoc.service.CommandeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
@Tag(name = "Commandes", description = "Gestion des commandes clients")
public class CommandeApiController {

    private final CommandeService commandeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Commande>>> getAll() {
        List<Commande> commandes = commandeService.getAllCommandes();
        return ResponseEntity.ok(ApiResponse.success(
                commandes.size() + " commande(s) trouvée(s)", commandes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Commande>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
                "Commande trouvée", commandeService.getCommandeById(id)));
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<ApiResponse<List<Commande>>> getByStatut(
            @PathVariable Commande.StatutCommande statut) {
        List<Commande> commandes = commandeService.getCommandesByStatut(statut);
        return ResponseEntity.ok(ApiResponse.success(
                commandes.size() + " commande(s) avec le statut " + statut, commandes));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Commande>> create(@RequestBody Map<String, Object> body) {
        String nomClient = (String) body.get("nomClient");
        String emailClient = (String) body.get("emailClient");

        @SuppressWarnings("unchecked")
        Map<String, Integer> panierRaw = (Map<String, Integer>) body.get("panier");
        Map<Long, Integer> panier = new java.util.HashMap<>();
        panierRaw.forEach((k, v) -> panier.put(Long.parseLong(k), v));

        Commande commande = commandeService.creerCommande(nomClient, emailClient, panier);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Commande créée avec succès", commande));
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<ApiResponse<Commande>> updateStatut(
            @PathVariable Long id,
            @RequestParam Commande.StatutCommande statut) {
        Commande commande = commandeService.updateStatut(id, statut);
        return ResponseEntity.ok(ApiResponse.success(
                "Statut mis à jour : " + statut, commande));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.ok(ApiResponse.success("Commande supprimée avec succès"));
    }
}