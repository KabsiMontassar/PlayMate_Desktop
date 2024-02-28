package models;

public class Equipe {
    private int idEquipe ;
    private String nomEquipe ;
    private int  nbreJoueur ;

    public Equipe() {
    }

    public Equipe(int idEquipe, String nomEquipe, int nbreJoueur) {
        this.idEquipe = idEquipe;
        this.nomEquipe = nomEquipe;
        this.nbreJoueur = nbreJoueur;
    }

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public int getNbreJoueur() {
        return nbreJoueur;
    }

    public void setNbreJoueur(int nbreJoueur) {
        this.nbreJoueur = nbreJoueur;
    }

    @Override
    public String toString() {
        return "Equipe{" +
                "idEquipe=" + idEquipe +
                ", nomEquipe='" + nomEquipe + '\'' +
                ", nbreJoueur=" + nbreJoueur +
                '}';
    }
}
