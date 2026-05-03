package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dao.Produit;
import services.ProduitMetier;

/**
 * Controller Spring MVC - Couche presentation.
 * Gere toutes les requetes HTTP liees aux produits.
 *
 * Annotations cles :
 *   @Controller   -> Spring detecte cette classe comme Bean Controller
 *   @Autowired    -> Spring injecte automatiquement le service metier
 *   @RequestMapping -> Mappe une URL vers une methode Java
 *
 * Difference avec TP precedent (MVC1) :
 *   Avant : une Servlet separee par action (ListProduitServlet, AddProduitServlet...)
 *   Maintenant : un seul Controller regroupe toutes les actions
 *
 * Flux d'une requete Spring MVC :
 *   Browser -> DispatcherServlet -> @RequestMapping -> methode -> Model -> ViewResolver -> JSP
 */
@Controller
public class ProduitController {

    /**
     * Injection du service metier par Spring IOC.
     * @Autowired : Spring cherche un Bean de type ProduitMetier dans son contexte
     * et l'injecte automatiquement ici.
     * Le service est configure dans spring-beans.xml (injection XML).
     */
    @Autowired
    ProduitMetier services;

    // ============================================================
    // ACTION 1 : Afficher la liste de tous les produits
    // URL : /index.aspx
    // ============================================================

    /**
     * Afficher la page principale avec la liste des produits.
     * Model : conteneur Spring pour passer des donnees a la JSP.
     * Retourne "produits" -> ViewResolver resout vers /Pages/produits.jsp
     */
    @RequestMapping(value = "/index")
    public String pageIndex(Model model) {
        // Recuperer tous les produits via la couche service
        model.addAttribute("listeProduit", services.getAllProduits());
        // Retourner le nom de la vue (sans extension ni chemin)
        return "produits";
    }

    // ============================================================
    // ACTION 2 : Rechercher un produit par ID
    // URL : /searchProduct.aspx
    // ============================================================

    /**
     * Rechercher un produit par son identifiant.
     * @RequestParam : recupere le parametre "idProduit" depuis l'URL/formulaire.
     */
    @RequestMapping(value = "/searchProduct")
    public String searchProduct(Model model,
            @RequestParam(value = "idProduit") Long id) {

        List<Produit> liste = new ArrayList<Produit>();
        Produit p = services.getProduitById(id);
        if (p != null) {
            liste.add(p);
        }
        // Renvoyer l'ID pour pre-remplir le champ de recherche dans la JSP
        model.addAttribute("listeProduit", liste);
        model.addAttribute("idProduit", id);
        return "produits";
    }

    // ============================================================
    // ACTION 3 : Ajouter un produit
    // URL : /addProduct.aspx  (POST)
    // ============================================================

    /**
     * Ajouter un nouveau produit.
     * Spring MVC fait le "data binding" automatiquement :
     *   les parametres du formulaire (nom, description, prix)
     *   sont injectes dans l'objet Produit p.
     * -> Pas besoin de req.getParameter("nom") comme dans les Servlets !
     */
    @RequestMapping(value = "/addProduct")
    public String addProduct(Model model, Produit p) {
        services.addProduit(p);
        // Rafraichir la liste apres ajout
        model.addAttribute("listeProduit", services.getAllProduits());
        return "produits";
    }

    // ============================================================
    // ACTION 4 : Supprimer un produit
    // URL : /deleteProduit.aspx
    // ============================================================

    @RequestMapping(value = "/deleteProduit")
    public String supprimerProduit(Model model, @RequestParam Long id) {
        services.deleteProduit(id);
        model.addAttribute("listeProduit", services.getAllProduits());
        return "produits";
    }

    // ============================================================
    // ACTION 5 : Charger un produit pour modification (GET)
    // URL : /editProduit.aspx?id=X
    // ============================================================

    /**
     * Charger un produit dans le formulaire pour modification.
     * method=RequestMethod.GET : repond uniquement aux requetes HTTP GET.
     * L'attribut "produitEdit" est utilise dans la JSP pour pre-remplir le formulaire.
     */
    @RequestMapping(value = "/editProduit", method = RequestMethod.GET)
    public String editProduit(Model model, @RequestParam Long id) {
        Produit p = services.getProduitById(id);
        model.addAttribute("produitEdit", p);              // Pour pre-remplir le formulaire
        model.addAttribute("listeProduit", services.getAllProduits());
        return "produits";
    }

    // ============================================================
    // ACTION 6 : Enregistrer la modification (POST)
    // URL : /updateProduit.aspx  (POST)
    // ============================================================

    /**
     * Mettre a jour un produit apres validation du formulaire.
     * method=RequestMethod.POST : repond uniquement aux requetes HTTP POST.
     * On recupere chaque parametre du formulaire individuellement.
     */
    @RequestMapping(value = "/updateProduit", method = RequestMethod.POST)
    public String updateProduitPost(Model model,
            @RequestParam Long idProduit,
            @RequestParam String nom,
            @RequestParam String description,
            @RequestParam Double prix) {

        // Construire l'objet Produit avec les nouvelles valeurs
        Produit p = new Produit();
        p.setIdProduit(idProduit);
        p.setNom(nom);
        p.setDescription(description);
        p.setPrix(prix);

        // Appeler la couche service pour persister la mise a jour
        services.updateProduit(p);

        model.addAttribute("listeProduit", services.getAllProduits());
        return "produits";
    }
}
