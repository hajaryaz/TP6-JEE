package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Utilitaire JPA : fournit un EntityManagerFactory Singleton.
 * L'EMF est cree une seule fois au chargement de la classe (bloc static).
 * Il est ferme a l'arret de l'application via AppStartupListener.
 *
 * Pourquoi Singleton ?
 *   Creer un EMF est couteux (lecture persistence.xml, connexion BDD...).
 *   On ne le cree qu'une seule fois et on reutilise des EntityManagers legers.
 */
public class JpaUtil {

    private static final String PERSISTENCE_UNIT = "produitPU";
    private static EntityManagerFactory emf;

    // Bloc static : execute une seule fois au chargement de la classe JVM
    static {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }

    /**
     * Retourne un nouvel EntityManager (leger, a fermer apres usage).
     * Un EM represente une "session" avec la BDD.
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Fermer la factory (a appeler au shutdown de l'application).
     * Libere les connexions BDD et les ressources Hibernate.
     */
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
