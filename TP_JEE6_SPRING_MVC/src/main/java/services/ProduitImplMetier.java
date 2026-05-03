package services;

import java.util.List;
import dao.Produit;
import dao.ProduitDAO;
import dao.ProduitImpl;

/**
 * Implementation de la couche metier pour les produits.
 *
 * Dans Spring MVC avec IOC XML :
 *   - Cette classe est declaree comme Bean dans spring-beans.xml
 *   - Le DAO est injecte par Spring via le setter setDao()
 *   -> Plus besoin de "new ProduitImpl()" ici : Spring s'en charge !
 *
 * Difference avec le TP precedent (MVC1 / Singleton manuel) :
 *   Avant : private static ProduitMetierImpl instance;  (Singleton fait a la main)
 *   Maintenant : Spring gere le cycle de vie du Bean (Singleton par defaut)
 */
public class ProduitImplMetier implements ProduitMetier {

    // Referece vers la couche DAO
    // Spring injectera automatiquement l'objet via setDao()
    private ProduitDAO dao;

    /**
     * Setter necessaire pour l'injection XML de Spring.
     * spring-beans.xml contient : <property name="dao" ref="objDAO"/>
     * Spring appellera ce setter au demarrage de l'application.
     */
    public void setDao(ProduitDAO dao) {
        this.dao = dao;
    }

    // --- Delegation des operations au DAO ---
    // La couche service peut enrichir la logique avant de deleguer au DAO

    @Override
    public void addProduit(Produit p) {
        // Ici on pourrait ajouter : validation, log, notification...
        dao.addProduit(p);
    }

    @Override
    public void deleteProduit(Long id) {
        dao.deleteProduit(id);
    }

    @Override
    public List<Produit> getAllProduits() {
        return dao.getAllProduits();
    }

    @Override
    public Produit getProduitById(Long id) {
        return dao.getProduitById(id);
    }

    @Override
    public void updateProduit(Produit p) {
        dao.updateProduit(p);
    }
}
