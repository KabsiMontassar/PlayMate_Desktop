package models;

public class Fournisseur extends User{

    private String Nom_Societe;

    public String getNom_Societe() {
        return Nom_Societe;
    }

    public void setNom_Societe(String nom_Societe) {
        Nom_Societe = nom_Societe;
    }

    public Fournisseur(int id, String Email, String Password, String Name, int age, int Phone, String address, Roles role, String image) throws Exception {
        super(id, Email, Password, Name, age, Phone, address, role, image);
        this.Nom_Societe = "";
    }

    public Fournisseur(String Email, String Password, String Name, int age, int Phone, Roles role) throws Exception {
        super(Email, Password, Name, age, Phone, role);
        this.Nom_Societe = "";
    }
}
