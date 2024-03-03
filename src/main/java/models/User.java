package models;


import services.GestionUser.CurrentTime;
import services.GestionUser.VerificationCodeGenerator;

public class User {
    private int id;

    private int age;
    private String image ;
    private int Phone ;
    private Roles role ;

    private String VerificationCode;

    private Boolean isVerified ;

    private String Date_de_Creation;



    private String Email ;
    private String Address;

    private String Password;
    private String name;

    private boolean Status ;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate_de_Creation() {
        return Date_de_Creation;
    }

    public void setDate_de_Creation(String date_de_Creation) {
        this.Date_de_Creation = date_de_Creation;
    }



    public void setVerificationCode(String verificationCode) {
        this.VerificationCode = verificationCode;
    }

    public boolean getStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        this.Status = status;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(String role) {

        //convert string to role with switch case
        //Proprietaire_de_Terrain,Joueur,Organisateur,Fournisseur;
        switch (role) {
            case "Fournisseur":
                this.role = Roles.Fournisseur;
                break;
            case "Joueur":

                this.role = Roles.Joueur;
                break;
            case "Organisateur":

                this.role = Roles.Organisateur;
                break;
            default:
                this.role = Roles.Proprietaire_de_Terrain;
                break;

        }
        

          


    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        this.Phone = phone;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name ;   }

    public boolean isStatus() {
        return Status;
    }

    public void setName(String name) {
        this.name = name;    }





    public String getVerificationCode() {
        return VerificationCode;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        this.isVerified = verified;
    }


    public User(){}
    public User(int id, String Email , String Password, String Name , int age , int Phone , String address , Roles role , String image ) throws Exception {
        this.id = id;
        this.Email = Email;
        this.Password = Password;
        this.name = Name;
        this.age = age;
        this.Phone = Phone;
        this.Address = address;
        this.role = role;
        this.image = image;
        this.Status = false ;
        this.Date_de_Creation = CurrentTime.GetCurrentTime() ;
        this.VerificationCode = VerificationCodeGenerator.generateVerificationCode();
        this.isVerified = false;

    }
    public User( String Email , String Password, String Name , int age , int Phone  , Roles role ) throws Exception {
        this.Email = Email;
        this.Password = Password;
        this.name = Name;
        this.age = age;
        this.Address = "";
        this.Phone = Phone;
        this.role = role;
        this.Status = true ;
        this.Date_de_Creation = CurrentTime.GetCurrentTime() ;
        this.VerificationCode = VerificationCodeGenerator.generateVerificationCode();
        this.isVerified = false;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", image='" + image + '\'' +
                ", Phone=" + Phone +
                ", role=" + role +
                ", VerificationCode='" + VerificationCode + '\'' +
                ", isVerified=" + isVerified +
                ", Date_de_Creation='" + Date_de_Creation + '\'' +
                ", Email='" + Email + '\'' +
                ", Address='" + Address + '\'' +
                ", Password='" + Password + '\'' +
                ", name='" + name + '\'' +
                ", Status=" + Status +
                '}';
    }
}
