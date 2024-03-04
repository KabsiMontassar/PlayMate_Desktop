package models;

public class Proprietaire_de_terrain extends User {


    public Proprietaire_de_terrain() {
    }

    public Proprietaire_de_terrain(int id, String Email, String Password, String Name, int age, int Phone, String address, Roles role, String image) throws Exception {
        super(id, Email, Password, Name, age, Phone, address, role, image);
    }

    public Proprietaire_de_terrain(String Email, String Password, String Name, int age, int Phone, Roles role) throws Exception {
        super(Email, Password, Name, age, Phone, role);
    }
}
