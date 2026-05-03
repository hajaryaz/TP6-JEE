package dao;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener de demarrage de l'application.
 * Execute automatiquement au demarrage du serveur Tomcat.
 * Role : inserer des donnees de test si la base est vide.
 *
 * Avantage : remplace l'ancien bloc static {} eparpille dans les classes.
 * Tout l'initialisation est centralise ici, dans un seul endroit.
 */
@WebListener
public class AppStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Demarrage application Spring MVC : initialisation donnees ===");

        ProduitDAO produitDAO = new ProduitImpl();

        // Inserer des produits de test si la liste est vide
        if (produitDAO.getAllProduits().isEmpty()) {
            produitDAO.addProduit(new Produit("PC 1", "Sony Vaio 1", 7000.0));
            produitDAO.addProduit(new Produit("PC 2", "Sony Vaio 2", 6000.0));
            produitDAO.addProduit(new Produit("PC 3", "Sony Vaio 3", 4000.0));
            produitDAO.addProduit(new Produit("PC 4", "Sony Vaio 4", 9000.0));
            System.out.println("=== Produits initiaux inseres ===");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Fermer l'EntityManagerFactory proprement a l'arret
        JpaUtil.close();
        System.out.println("=== Application arretee, EntityManagerFactory ferme ===");
    }
}
