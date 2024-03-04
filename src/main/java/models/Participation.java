package models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Participation {

    private int id ;
    private int idMembre;

    private String nomEquipe ;

    private boolean status ;

    private String dateC ;

    private int idTournoi;

    LocalTime currentTime = LocalTime.now();

    // Define a formatter to format the time as a string
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Format the local time as a string
    String currentTimeString = currentTime.format(formatter);
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDateC() {
        return dateC;
    }

    public void setDateC(String dateC) {
        this.dateC = dateC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getIdTournoi() {
        return idTournoi;
    }

    public void setIdTournoi(int idTournoi) {
        this.idTournoi = idTournoi;
    }


    public int getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(int idMembre) {
        this.idMembre = idMembre;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public Participation() {

    }

    public Participation(int id, int idMembre, String nomEquipe, int idTournoi) {
        this.id = id;
        this.idMembre = idMembre;
        this.nomEquipe = nomEquipe;
        this.status = false;
        this.dateC = currentTimeString;
        this.idTournoi = idTournoi;
    }

    public Participation(int idMembre, String nomEquipe, int idTournoi) {
        this.idMembre = idMembre;
        this.nomEquipe = nomEquipe;
        this.status = false;
        this.dateC = currentTimeString;
        this.idTournoi = idTournoi;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "id=" + id +
                ", idMembre=" + idMembre +
                ", nomEquipe='" + nomEquipe + '\'' +
                ", status=" + status +
                ", dateC='" + dateC + '\'' +
                ", idTournoi=" + idTournoi +
                ", currentTime=" + currentTime +
                ", formatter=" + formatter +
                ", currentTimeString='" + currentTimeString + '\'' +
                '}';
    }
}