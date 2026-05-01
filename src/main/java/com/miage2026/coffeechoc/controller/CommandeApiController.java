package com.miage2026.coffeechoc.controller;

import com.miage2026.coffeechoc.model.Commande;
import com.miage2026.coffeechoc.service.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
@Tag(name = "Commandes", description = "Gestion des commandes clients")
public class CommandeApiController {

    private final CommandeService commandeService;

    @GetMapping
    public List<Commande> getAll() {
        return commandeService.getAllCommandes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getById(@PathVariable Long id) {
        return ResponseEntity.ok(commandeService.getCommandeById(id));
    }

    @GetMapping("/statut/{statut}")
    public List<Commande> getByStatut(@PathVariable Commande.StatutCommande statut) {
        return commandeService.getCommandesByStatut(statut);
    }

    @PostMapping
    public ResponseEntity<Commande> create(@RequestBody Map<String, Object> body) {
        String nomClient = (String) body.get("nomClient");
        String emailClient = (String) body.get("emailClient");

        @SuppressWarnings("unchecked")
        Map<String, Integer> panierRaw = (Map<String, Integer>) body.get("panier");
        Map<Long, Integer> panier = new java.util.HashMap<>();
        panierRaw.forEach((k, v) -> panier.put(Long.parseLong(k), v));

        return ResponseEntity.ok(commandeService.creerCommande(nomClient, emailClient, panier));
    }

    @PutMapping("/{id}/statut")
    public ResponseEntity<Commande> updateStatut(
            @PathVariable Long id,
            @RequestParam Commande.StatutCommande statut) {
        return ResponseEntity.ok(commandeService.updateStatut(id, statut));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }


}