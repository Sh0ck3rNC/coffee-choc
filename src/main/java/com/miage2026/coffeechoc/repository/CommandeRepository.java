package com.miage2026.coffeechoc.repository;

import com.miage2026.coffeechoc.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByEmailClient(String emailClient);
    List<Commande> findByStatut(Commande.StatutCommande statut);
}