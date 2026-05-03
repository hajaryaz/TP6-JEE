package dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation de ProduitDAO avec JPA/Hibernate.
 * Remplace l'ancienne liste en memoire par une vraie BDD MySQL.
 * Chaque methode gere son propre EntityManager et sa propre transaction.
 *
 * Difference avec TP precedent (liste en memoire) :
 *   Avant : private List<Produit> produits = new ArrayList<>();
 *   Maintenant : on utilise EntityManager de JPA -> persistance reelle
 */
public class ProduitImpl implements ProduitDAO {

    @Override
    public void addProduit(Produit p) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(p);                  // INSERT INTO produit ...
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback(); // Annuler si erreur
            throw new RuntimeException("Erreur ajout produit", e);
        } finally {
            em.close();                     // Toujours fermer l'EntityManager
        }
    }

    @Override
    public void deleteProduit(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Produit p = em.find(Produit.class, id); // SELECT ... WHERE id = ?
            if (p != null) {
                em.remove(p);               // DELETE FROM produit WHERE id = ?
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur suppression produit", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Produit getProduitById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Produit.class, id); // SELECT ... WHERE id_produit = ?
        } finally {
            em.close();
        }
    }

    @Override
    public List<Produit> getAllProduits() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // JPQL : langage de requete oriente objet (pas SQL pur)
            TypedQuery<Produit> query = em.createQuery(
                "SELECT p FROM Produit p", Produit.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void updateProduit(Produit p) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(p); // UPDATE produit SET nom=?, prix=? WHERE id_produit=?
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur mise a jour produit", e);
        } finally {
            em.close();
        }
    }
}
