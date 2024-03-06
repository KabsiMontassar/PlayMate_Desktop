package models;

public class Product {
    private int id;
    private String nom;
    private String description;
    private int prix;
    private String image;
    private int categorie;
    private int idfournisseur;
    private int nbcategorie;
    public Product() {
    }

    public Product(int id, String nom, String description, int prix, String image, int categorie,int idfournisseur) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.categorie = categorie;
        this.idfournisseur=idfournisseur;
    }

    public int getIdfournisseur() {
        return idfournisseur;
    }

    public void setIdfournisseur(int idfournisseur) {
        this.idfournisseur = idfournisseur;
    }

    public int getNbcategorie() {
        return nbcategorie;
    }

    public void setNbcategorie(int nbcategorie) {
        this.nbcategorie = nbcategorie;
    }

    public Product(String nom, String description, int prix) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    public Product(String nom, String description, int prix, String image) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
    }

    public Product(int id, String nom, int prix) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
    }

    public Product(String nom, String description, int prix, String image, int categorie) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.categorie = categorie;
    }

    public Product( String nom, String description, int prix, String image, int categorie,int idfournisseur) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
        this.categorie = categorie;
        this.idfournisseur=idfournisseur;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public int getPrix() {
        return prix;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                ", categorie=" + categorie +
                '}';
    }
}
