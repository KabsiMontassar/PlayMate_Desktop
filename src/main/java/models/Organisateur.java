package models;

public class Organisateur extends User{
    private String Nom_Organisation;

    public Organisateur(int id, String Email, String Password, String Name, int age, int Phone, String address, Roles role, String image) throws Exception {
        super(id, Email, Password, Name, age, Phone, address, role, image);
        this.Nom_Organisation = "";
    }

    public Organisateur(String Email, String Password, String Name, int age, int Phone, Roles role) throws Exception {
        super(Email, Password, Name, age, Phone, role);
        this.Nom_Organisation = "";
    }

    public Organisateur(String nom_Organisateur) {
        this.Nom_Organisation = nom_Organisateur;
    }

    public Organisateur() {

    }

    public String getNom_Organisation() {
        return Nom_Organisation;
    }

    public void setNom_Organisation(String nom_Organisateur) {
        this.Nom_Organisation = nom_Organisateur;
    }


}
