package models;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

public class Tournoi {

    private int id ;
    private int  nbrquipeMax ;
    private String nom ;

    private String affiche ;
    private String Address ;
    private Date datedebut;
    private Date datefin;

    private int visite;

    private List<Participation> participationList = new ArrayList<>() ;

    private int orgid ;

    public Tournoi(int id, int nbrquipeMax, String nom, String affiche, String address, String datedebut, String datefin, int orgid) {
        this.id = id;
        this.nbrquipeMax = nbrquipeMax;
        this.nom = nom;
        this.affiche = affiche;
        this.Address = address;
         try {
            this.datedebut = Date.valueOf(datedebut);
            this.datefin = Date.valueOf(datefin);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        this.participationList = new ArrayList<>();
        this.orgid = orgid;
        this.visite = 0 ;
    }

    public Tournoi(int id, int nbrquipeMax, String nom, String affiche, String address, String datedebut, String datefin) {
        this.id = id;
        this.nbrquipeMax = nbrquipeMax;
        this.nom = nom;
        this.affiche = affiche;
        this.Address = address;
        try {
            this.datedebut = Date.valueOf(datedebut);
            this.datefin = Date.valueOf(datefin);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        this.participationList = new ArrayList<>();
        this.visite = 0 ;
    }

    public Tournoi() {
    }

    public Tournoi( int nbrquipeMax, String nom, String affiche, String address, String datedebut, String datefin, int orgid) {
        this.nbrquipeMax = nbrquipeMax;
        this.nom = nom;
        this.affiche = affiche;
        this.Address = address;
        try {
            this.datedebut = Date.valueOf(datedebut);
            this.datefin = Date.valueOf(datefin);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        this.participationList = new ArrayList<>();
        this.orgid = orgid;
        this.visite = 0 ;
    }

    public Tournoi(int nbrquipeMax, String nom, String affiche, String address, String datedebut, String datefin) {
        this.nbrquipeMax = nbrquipeMax;
        this.nom = nom;
        this.affiche = affiche;
        this.Address = address;
        try {
            this.datedebut = Date.valueOf(datedebut);
            this.datefin = Date.valueOf(datefin);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        this.visite = 0 ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbrquipeMax() {
        return nbrquipeMax;
    }

    public void setNbrquipeMax(int nbrquipeMax) {
        this.nbrquipeMax = nbrquipeMax;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAffiche() {
        return affiche;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public Date getDatedebut() {

        return datedebut;
    }
    /*public String getDatedebut() {
        return datedebut;
    }
*/
    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }
    public Date getDatefin() {
         return datefin;
    }
    /*public String getDatefin() {
        return datefin;
    }
*/
    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public List<Participation> getParticipationList() {
        return participationList;
    }

    public void setParticipationList(List<Participation> participationList) {
        this.participationList = participationList;
    }

    public int getOrg() {
        return orgid;
    }

    public void setOrg(int org) {
        this.orgid = org;
    }

    public int getVisite() {
        return visite;
    }

    public void setVisite(int visite) {
        this.visite = visite;
    }

    @Override
    public String toString() {
        return "Tournoi{" +
                "id=" + id +
                ", nbrquipeMax=" + nbrquipeMax +
                ", nom='" + nom + '\'' +
                ", affiche='" + affiche + '\'' +
                ", Address='" + Address + '\'' +
                ", datedebut='" + datedebut + '\'' +
                ", datefin='" + datefin + '\'' +
                ", visite=" + visite +
                ", participationList=" + participationList +
                ", orgid=" + orgid +
                '}';
    }

    public void ajouterParticipation(Participation participation) {
        this.participationList.add(participation);
        System.out.println(participationList);
    }
}
