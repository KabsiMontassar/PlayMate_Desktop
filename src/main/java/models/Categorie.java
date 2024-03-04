package models;

import java.util.ArrayList;
import java.util.List;

public class Categorie {
    private int id;
    private String nom;
    private String description;
    private List<Product> productList;

    public Categorie(int id, String nom, String description ) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.productList = new ArrayList<>();
    }

    public Categorie(String nom, String description) {
        this.nom = nom;
        this.description = description;
        this.productList = new ArrayList<>();
    }

    public Categorie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", productList=" + productList +
                '}';
    }
}
