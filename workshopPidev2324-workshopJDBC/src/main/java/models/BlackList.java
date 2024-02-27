package models;

public class BlackList {
    private int idBlackList ;
    private int idMembre ;

    private Reservation reservation ;
    private int  idReservation ;
    private int duree ;
    private String cause ;

    public BlackList() {
    }

    public BlackList(int idBlackList, int idMembre, int idReservation, int duree, String cause) {
        this.idBlackList = idBlackList;
        this.idMembre = idMembre;
        this.idReservation = idReservation;
        this.duree = duree;
        this.cause = cause;
    }

    public int getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    /*
            public BlackList( int idMembre, int idTerrain, Reservation reservation, int duree, String cause) {

                this.reservation = reservation;
                this.duree = duree;
                this.cause = cause;
            }
        */
    public BlackList(Reservation reservation) {
        this.reservation = reservation;
    }

    public int getIdBlackList() {
        return idBlackList;
    }

    public void setIdBlackList(int idBlackList) {
        this.idBlackList = idBlackList;
    }


    @Override
    public String toString() {
        return "BlackList{" +
                "idBlackList=" + idBlackList +
                ", idMembre=" + idMembre +
                ", reservation=" + reservation +
                ", idReservation=" + idReservation +
                ", duree=" + duree +
                ", cause='" + cause + '\'' +
                '}';
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

}
