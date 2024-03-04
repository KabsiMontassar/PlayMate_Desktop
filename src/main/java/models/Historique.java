package models;

public class Historique {
    private int idHistorique ;
    private String dateReservation,heureReservtion ;
    private int idMembre ;

    public Historique() {
    }

    public Historique( String dateReservation, String heureReservtion, int idMembre) {

        this.dateReservation = dateReservation;
        this.heureReservtion = heureReservtion;
        this.idMembre = idMembre;
    }

    public int getIdHistorique() {
        return idHistorique;
    }

    public void setIdHistorique(int idHistorique) {
        this.idHistorique = idHistorique;
    }

    public String getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getHeureReservtion() {
        return heureReservtion;
    }

    public void setHeureReservtion(String heureReservtion) {
        this.heureReservtion = heureReservtion;
    }

    public int getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }


    @Override
    public String toString() {
        return "Historique{" +
                "idHistorique=" + idHistorique +
                ", dateReservation='" + dateReservation + '\'' +
                ", heureReservtion='" + heureReservtion + '\'' +
                ", idMembre=" + idMembre +
                '}';
    }
}
