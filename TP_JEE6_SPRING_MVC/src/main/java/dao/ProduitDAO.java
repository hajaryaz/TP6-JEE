package dao;

import java.util.List;

/**
 * Interface DAO pour la gestion des produits.
 * Definit les operations CRUD sans preciser la methode de persistance.
 * L'implementation concrete peut etre en memoire, JDBC, JPA, etc.
 * -> Principe : programmer vers une interface, pas une implementation.
 */
public interface ProduitDAO {

    /** Ajouter un nouveau produit en base. */
    void addProduit(Produit p);

    /** Supprimer un produit par son identifiant. */
    void deleteProduit(Long id);

    /** Recuperer un produit par son ID. Retourne null si non trouve. */
    Produit getProduitById(Long id);

    /** Recuperer la liste complete des produits. */
    List<Produit> getAllProduits();

    /** Mettre a jour un produit existant (l'ID doit etre renseigne). */
    void updateProduit(Produit p);
}
