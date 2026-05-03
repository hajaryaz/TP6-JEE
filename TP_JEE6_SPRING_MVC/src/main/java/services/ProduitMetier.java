package services;

import java.util.List;
import dao.Produit;

/**
 * Interface du service metier pour la gestion des produits.
 * Cette couche sert d'intermediaire entre le DAO (acces aux donnees)
 * et le Controller (gestion des requetes HTTP).
 *
 * Elle peut contenir de la logique metier supplementaire :
 *   - validation des donnees
 *   - calculs (ex: TVA, remises)
 *   - regles metier (ex: prix minimum, stock...)
 *
 * Le Controller ne connait que cette interface -> couplage faible.
 */
public interface ProduitMetier {

    /** Ajouter un nouveau produit. */
    void addProduit(Produit p);

    /** Supprimer un produit par son ID. */
    void deleteProduit(Long id);

    /** Recuperer la liste de tous les produits. */
    List<Produit> getAllProduits();

    /** Recuperer un produit par son ID. */
    Produit getProduitById(Long id);

    /** Mettre a jour un produit existant. */
    void updateProduit(Produit p);
}
