package models;

public class Joueur extends User{


    public Joueur() {
    }

    public Joueur(int id, String Email, String Password, String Name, int age, int Phone, String address, Roles role, String image) throws Exception {
        super(id, Email, Password, Name, age, Phone, address, role, image);
    }

    public Joueur(String Email, String Password, String Name, int age, int Phone, Roles role) throws Exception {
        super(Email, Password, Name, age, Phone, role);
    }
}
