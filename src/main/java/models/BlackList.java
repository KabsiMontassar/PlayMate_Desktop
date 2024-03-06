package models;

public class BlackList {
    private int idBlackList ;
    private int  idReservation ;
    private int duree ;
    private String cause ;

    public BlackList() {
    }

    public BlackList(int idBlackList, int idReservation, int duree, String cause) {
        this.idBlackList = idBlackList;
        this.idReservation = idReservation;
        this.duree = duree;
        this.cause = cause;
    }

    public int getIdBlackList() {
        return idBlackList;
    }

    public void setIdBlackList(int idBlackList) {
        this.idBlackList = idBlackList;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
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

    @Override
    public String toString() {
        return "BlackList{" +
                "idBlackList=" + idBlackList +
                ", idReservation=" + idReservation +
                ", duree=" + duree +
                ", cause='" + cause + '\'' +
                '}';
    }
}
