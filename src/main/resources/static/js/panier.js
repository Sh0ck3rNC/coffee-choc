function ajouterAuPanier(btn) {
    const id = btn.getAttribute('data-id');
    const nom = btn.getAttribute('data-nom');
    const prix = parseFloat(btn.getAttribute('data-prix'));
    let panier = JSON.parse(localStorage.getItem('panier') || '[]');
    const existing = panier.find(p => p.id === id);
    if (existing) {
        existing.quantite++;
    } else {
        panier.push({ id, nom, prix, quantite: 1 });
    }
    localStorage.setItem('panier', JSON.stringify(panier));

    const toast = document.getElementById('toastAjout');
    document.getElementById('toastMessage').textContent = nom + ' ajouté au panier ☕';
    new bootstrap.Toast(toast).show();
}