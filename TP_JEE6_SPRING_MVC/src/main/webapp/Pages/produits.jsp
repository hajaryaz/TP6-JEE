<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%-- Import de la bibliotheque JSTL (core) pour <c:forEach>, <c:if> ... --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Gestion des Produits | Spring MVC</title>
    <%-- Bootstrap CSS pour le style --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

    <%-- ===== EN-TETE ===== --%>
    <div class="card mb-4">
        <div class="card-header bg-primary text-white">
            <h2>Gestion des produits | Spring MVC</h2>
        </div>

        <div class="card-body">

            <%-- ============================= --%>
            <%-- FORMULAIRE RECHERCHE PRODUIT  --%>
            <%-- ============================= --%>
            <%--
                Action : searchProduct.aspx -> @RequestMapping("/searchProduct")
                Le parametre "idProduit" est recupere avec @RequestParam dans le Controller
            --%>
            <p class="fw-bold">recherche :</p>
            <form action="searchProduct.aspx" method="post" class="row g-2 mb-3">
                ID :
                <div class="col-md-3">
                    <%-- ${idProduit} : valeur renvoyee par le Controller pour conserver la recherche --%>
                    <input type="text" class="form-control" name="idProduit" value="${idProduit}" />
                </div>
                <div class="col-auto">
                    <input type="submit" class="btn btn-info" value="Afficher" />
                </div>
            </form>

            <hr />

            <%-- ============================= --%>
            <%-- FORMULAIRE AJOUTER / MODIFIER --%>
            <%-- ============================= --%>
            <%--
                Formulaire dynamique :
                - Si ${produitEdit} existe  -> formulaire de MODIFICATION (updateProduit.aspx)
                - Sinon                      -> formulaire d'AJOUT (addProduct.aspx)
                C'est le Controller action "editProduit" qui place "produitEdit" dans le Model.
            --%>
            <p class="fw-bold">Ajouter / Modifier :</p>
            <form action="${produitEdit != null ? 'updateProduit' : 'addProduct'}.aspx" method="post" class="row g-2 mb-3">

                <%-- Champ cache : ID du produit, utilise uniquement en mode modification --%>
                <input type="hidden" name="idProduit"
                       value="${produitEdit != null ? produitEdit.idProduit : ''}" />

                <div class="col-md-3">
                    <label>Nom :</label>
                    <input type="text" class="form-control" name="nom"
                           value="${produitEdit != null ? produitEdit.nom : ''}" />
                </div>

                <div class="col-md-3">
                    <label>Description :</label>
                    <input type="text" class="form-control" name="description"
                           value="${produitEdit != null ? produitEdit.description : ''}" />
                </div>

                <div class="col-md-2">
                    <label>Prix :</label>
                    <input type="text" class="form-control" name="prix"
                           value="${produitEdit != null ? produitEdit.prix : ''}" />
                </div>

                <div class="col-md-2 d-flex align-items-end">
                    <%-- Bouton dynamique : "Ajouter" ou "Mettre a jour" selon le mode --%>
                    <input type="submit"
                           class="btn ${produitEdit != null ? 'btn-warning' : 'btn-success'} w-100"
                           value="${produitEdit != null ? 'Mettre a jour' : 'Ajouter'}" />
                </div>

            </form>

            <hr />

            <%-- ============================= --%>
            <%-- TABLEAU DES PRODUITS          --%>
            <%-- ============================= --%>
            <%--
                ${listeProduit} : attribut place dans le Model par le Controller.
                Exemple : model.addAttribute("listeProduit", services.getAllProduits());
                <c:forEach> : balise JSTL pour parcourir la liste (remplace scriptlet Java)
            --%>
            <table class="table table-bordered" width="80%">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>NOM</th>
                        <th>DESCRIPTION</th>
                        <th>PRIX</th>
                        <th>Option</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listeProduit}" var="o">
                        <tr>
                            <td>${o.idProduit}</td>
                            <td>${o.nom}</td>
                            <td>${o.description}</td>
                            <td>${o.prix}</td>

                            <%-- Lien suppression -> deleteProduit.aspx?id=X
                                 @RequestMapping("/deleteProduit") dans le Controller --%>
                            <td>
                                <a href="deleteProduit.aspx?id=${o.idProduit}" class="text-danger">
                                    supprimer
                                </a>
                            </td>

                            <%-- Lien modification -> editProduit.aspx?id=X
                                 @RequestMapping("/editProduit") -> charge le produit dans le formulaire --%>
                            <td>
                                <a href="editProduit.aspx?id=${o.idProduit}" class="text-primary">
                                    Modifier
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
