package com.miage2026.coffeechoc.service;

import com.miage2026.coffeechoc.exception.ConflictException;
import com.miage2026.coffeechoc.exception.ResourceNotFoundException;
import com.miage2026.coffeechoc.exception.UnauthorizedException;
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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Commande introuvable avec l'id : " + id));
    }

    public List<Commande> getCommandesByEmail(String email) {
        List<Commande> commandes = commandeRepository.findByEmailClient(email);
        if (commandes.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Aucune commande trouvée pour l'email : " + email);
        }
        return commandes;
    }

    public List<Commande> getCommandesByStatut(Commande.StatutCommande statut) {
        List<Commande> commandes = commandeRepository.findByStatut(statut);
        if (commandes.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Aucune commande avec le statut : " + statut);
        }
        return commandes;
    }

    public Commande creerCommande(String nomClient, String emailClient, Map<Long, Integer> panier) {
        if (panier == null || panier.isEmpty()) {
            throw new ConflictException("Impossible de créer une commande avec un panier vide.");
        }

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

            // Vérifie que le produit est disponible
            if (!produit.getDisponible()) {
                throw new ConflictException(
                        "Le produit '" + produit.getNom() + "' n'est plus disponible.");
            }

            int quantite = entry.getValue();
            if (quantite <= 0) {
                throw new ConflictException(
                        "La quantité pour '" + produit.getNom() + "' doit être supérieure à 0.");
            }

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

        // Interdit de modifier une commande déjà livrée ou annulée
        if (commande.getStatut() == Commande.StatutCommande.LIVRE ||
                commande.getStatut() == Commande.StatutCommande.ANNULE) {
            throw new UnauthorizedException(
                    "Impossible de modifier une commande déjà " +
                            commande.getStatut().name().toLowerCase() + ".");
        }

        commande.setStatut(nouveauStatut);
        return commandeRepository.save(commande);
    }

    public void deleteCommande(Long id) {
        Commande commande = getCommandeById(id);

        // Interdit de supprimer une commande en cours de préparation
        if (commande.getStatut() == Commande.StatutCommande.EN_PREPARATION ||
                commande.getStatut() == Commande.StatutCommande.PRET) {
            throw new UnauthorizedException(
                    "Impossible de supprimer une commande en cours de préparation.");
        }

        commandeRepository.deleteById(id);
    }
}