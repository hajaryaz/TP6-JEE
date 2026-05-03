package dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entite JPA representant un produit.
 * Mappe automatiquement sur la table "produit" en base MySQL.
 * Les annotations JPA remplacent le fichier hbm.xml de Hibernate classique.
 */
@Entity
@Table(name = "produit")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private Long idProduit;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "prix", nullable = false)
    private Double prix;

    // Constructeur vide requis par JPA et Spring MVC (data binding)
    public Produit() {}

    // Constructeur pratique pour creer un produit sans ID (avant persistance)
    public Produit(String nom, String description, Double prix) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    // --- Getters et Setters ---
    // Necessaires pour Spring MVC (data binding formulaire <-> objet)

    public Long getIdProduit() { return idProduit; }
    public void setIdProduit(Long idProduit) { this.idProduit = idProduit; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }
}
