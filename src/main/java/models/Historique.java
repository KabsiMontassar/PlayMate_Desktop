package models;

public class Historique {
    private int idHistorique ;

    private int idReservation ;

    public Historique() {
    }

    public Historique(int idHistorique, int idReservation) {
        this.idHistorique = idHistorique;
        this.idReservation = idReservation;
    }

    public int getIdHistorique() {
        return idHistorique;
    }

    public void setIdHistorique(int idHistorique) {
        this.idHistorique = idHistorique;
    }

    public int getIdReservation() {
        return this.idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    @Override
    public String toString() {
        return "Historique{" +
                "idHistorique=" + idHistorique +
                ", idReservation=" + idReservation +
                '}';
    }
}
