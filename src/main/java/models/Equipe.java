package models;

public class Equipe {

    private int id ;
    private String nom ;

    public Equipe(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Equipe(String nom) {

        this.nom = nom;
    }

    public Equipe() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Equipe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
