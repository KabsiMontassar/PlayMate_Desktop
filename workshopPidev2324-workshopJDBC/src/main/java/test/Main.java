package test;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.mailjet.client.errors.MailjetException;
import models.Equipe;
import models.MailJettAPI;
import models.Reservation;
import services.EquipeService;
import services.ReservationService;
import services.TerrainService;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.List;



public class Main {


    public static void main(String[] args) throws MailjetException, SQLException {

//
//        MailJettAPI mailJettAPI = new MailJettAPI();
//        mailJettAPI.send("aziztaraji1@gmail.com","playmatepidev@gmail.com", "33355");
/*
        ReservationService reservationService = new ReservationService();
        List<Reservation> list = reservationService.getReservationByIdMembre(8);
        for (Reservation reservation : list){
            System.out.println(reservation.toString());
        }*/
    }






}


//        EquipeService equipeService = new EquipeService();
//        List<Equipe> equipeList = equipeService.getEquipesParMembre(8);
//        for (Equipe equipe :equipeList){
//            System.out.println(equipe.toString());
//        }
//        TerrainService terrainService = new TerrainService();
//        System.out.println(terrainService.getTerrainById(6));

//        ReservationService reservationService = new ReservationService();
//
//        //  ****************************      id user 8
//        List<Reservation> reservations = reservationService.getReservationByIdMembre(8);
//        for (Reservation reservation: reservations){
//            System.out.println(reservation.toString());
//        }
//        PaimentController paimentController = new PaimentController();
//        try {
//            paimentController.PaymentAPI();
//        }catch (Exception e){
//            System.out.println(e);
//        }