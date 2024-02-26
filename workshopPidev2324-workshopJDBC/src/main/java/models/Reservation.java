package models;

import java.util.ArrayList;
import java.util.List;

public class Reservation {
    private int idReservation ;
    private boolean isConfirm ;
    private String dateReservation ;
    private String heureReservation ;
    private  TypeReservation type ;
    private int idTerrain ;
    private String  nomEquipe1 ;
    private String  nomEquipe2 ;


    public Reservation() {
    }

    public Reservation(boolean isConfirm, String dateReservation, String heureReservation, TypeReservation type , int idTerrain ,String  nomEquipe1 ) {
        this.idTerrain = idTerrain ;
        this.isConfirm = isConfirm;
        this.dateReservation = dateReservation;
        this.heureReservation = heureReservation;
        this.type = type;
        this.nomEquipe1 = nomEquipe1 ;
    }

    public Reservation(boolean isConfirm, String dateReservation, String heureReservation, TypeReservation type , int idTerrain ,String  nomEquipe1 ,String  nomEquipe2 ) {
        this.idTerrain = idTerrain ;
        this.isConfirm = isConfirm;
        this.dateReservation = dateReservation;
        this.heureReservation = heureReservation;
        this.type = type;
        this.nomEquipe1 = nomEquipe1 ;
        this.nomEquipe2 = nomEquipe2 ;
    }

    public int getIdTerrain() {
        return idTerrain;
    }

    public void setIdTerrain(int idTerrain) {
        this.idTerrain = idTerrain;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public boolean isConfirm() {
        return isConfirm;
    }

    public void setConfirm(boolean confirm) {
        isConfirm = confirm;
    }

    public String getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getHeureReservation() {
        return heureReservation;
    }

    public void setHeureReservation(String heureReservation) {
        this.heureReservation = heureReservation;
    }

    public String getType() {
        String t = this.type.name();
        return t;
    }

    public void setType(TypeReservation type) {
        this.type = type;
    }

    public String getNomEquipe1() {
        return nomEquipe1;
    }

    public void setNomEquipe1(String nomEquipe1) {
        this.nomEquipe1 = nomEquipe1;
    }

    public String getNomEquipe2() {
        return nomEquipe2;
    }

    public void setNomEquipe2(String nomEquipe2) {
        this.nomEquipe2 = nomEquipe2;
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "idReservation=" + idReservation +
                ", isConfirm=" + isConfirm +
                ", dateReservation='" + dateReservation + '\'' +
                ", heureReservation='" + heureReservation + '\'' +
                ", type=" + type +
                ", idTerrain=" + idTerrain +
                ", nomEquipe1='" + nomEquipe1 + '\'' +
                ", nomEquipe2='" + nomEquipe2 + '\'' +
                '}';
    }
}
