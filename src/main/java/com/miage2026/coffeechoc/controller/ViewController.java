package com.miage2026.coffeechoc.controller;

import com.miage2026.coffeechoc.model.Commande;
import com.miage2026.coffeechoc.model.Produit;
import com.miage2026.coffeechoc.service.CommandeService;
import com.miage2026.coffeechoc.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final ProduitService produitService;
    private final CommandeService commandeService;

    // Page d'accueil - menu
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("produits", produitService.getProduitsDisponibles());
        return "index";
    }

    // Page admin - liste produits
    @GetMapping("/admin/produits")
    public String adminProduits(Model model) {
        model.addAttribute("produits", produitService.getAllProduits());
        model.addAttribute("produit", new Produit());
        return "admin/produits";
    }

    // Admin - créer produit
    @PostMapping("/admin/produits")
    public String createProduit(@ModelAttribute Produit produit) {
        produitService.saveProduit(produit);
        return "redirect:/admin/produits";
    }

    // Admin - formulaire édition
    @GetMapping("/admin/produits/edit/{id}")
    public String editProduit(@PathVariable Long id, Model model) {
        model.addAttribute("produit", produitService.getProduitById(id));
        return "admin/produit-form";
    }

    // Admin - sauvegarder édition
    @PostMapping("/admin/produits/edit/{id}")
    public String updateProduit(@PathVariable Long id, @ModelAttribute Produit produit) {
        produitService.updateProduit(id, produit);
        return "redirect:/admin/produits";
    }

    // Admin - supprimer produit
    @GetMapping("/admin/produits/delete/{id}")
    public String deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return "redirect:/admin/produits";
    }

    // Admin - commandes
    @GetMapping("/admin/commandes")
    public String adminCommandes(Model model) {
        model.addAttribute("commandes", commandeService.getAllCommandes());
        model.addAttribute("statuts", Commande.StatutCommande.values());
        return "admin/commandes";
    }

    // Admin - changer statut commande
    @PostMapping("/admin/commandes/{id}/statut")
    public String updateStatut(@PathVariable Long id,
                               @RequestParam Commande.StatutCommande statut) {
        commandeService.updateStatut(id, statut);
        return "redirect:/admin/commandes";
    }

    // Page panier
    @GetMapping("/panier")
    public String panier() {
        return "panier";
    }

    @GetMapping("/teapot")
    public String teapot() {
        throw new UnsupportedOperationException();
    }
}