package models;

public class membre extends User{


    public membre() {
    }

    public membre(int id, String Email, String Password, String Name, int age, int Phone, String address, Roles role) {
        super(id, Email, Password, Name, age, Phone, address, role);
    }

    public membre(String Email, String Password, String Name, int age, int Phone, Roles role) {
        super(Email, Password, Name, age, Phone, role);
    }
}
