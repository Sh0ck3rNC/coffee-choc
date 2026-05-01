package com.miage2026.coffeechoc.service;

import com.miage2026.coffeechoc.model.Commande;
import com.miage2026.coffeechoc.model.LigneCommande;
import com.miage2026.coffeechoc.model.Produit;
import com.miage2026.coffeechoc.repository.CommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final ProduitService produitService;

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable : " + id));
    }

    public List<Commande> getCommandesByEmail(String email) {
        return commandeRepository.findByEmailClient(email);
    }

    public List<Commande> getCommandesByStatut(Commande.StatutCommande statut) {
        return commandeRepository.findByStatut(statut);
    }

    // Map<produitId, quantite>
    public Commande creerCommande(String nomClient, String emailClient, Map<Long, Integer> panier) {
        Commande commande = Commande.builder()
                .nomClient(nomClient)
                .emailClient(emailClient)
                .dateCommande(LocalDateTime.now())
                .statut(Commande.StatutCommande.EN_ATTENTE)
                .total(0.0)
                .build();

        double total = 0.0;
        for (Map.Entry<Long, Integer> entry : panier.entrySet()) {
            Produit produit = produitService.getProduitById(entry.getKey());
            int quantite = entry.getValue();

            LigneCommande ligne = LigneCommande.builder()
                    .commande(commande)
                    .produit(produit)
                    .quantite(quantite)
                    .prixUnitaire(produit.getPrix())
                    .build();

            commande.getLignes().add(ligne);
            total += produit.getPrix() * quantite;
        }

        commande.setTotal(total);
        return commandeRepository.save(commande);
    }

    public Commande updateStatut(Long id, Commande.StatutCommande nouveauStatut) {
        Commande commande = getCommandeById(id);
        commande.setStatut(nouveauStatut);
        return commandeRepository.save(commande);
    }

    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }
}