package services.GestionReservation;

import models.Historique;
import models.Reservation;
import services.GestionReservation.ReservationService;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoriqueService {
    private Connection connection;
    public HistoriqueService(){
        connection = MyDatabase.getInstance().getConnection();
    }


    // gener un vocher
    public List<Historique> consulterHistoriqueParMembre(int  idmembre) throws SQLException {

        List<Historique> historiques = new ArrayList<>();

        String query = "SELECT * FROM historique WHERE idMembre  = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idmembre);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Historique historique = new Historique();
            historique.setIdHistorique(rs.getInt(1));
            historique.setIdMembre(rs.getInt(2));
            historique.setDateReservation(rs.getString(3));
            historique.setHeureReservtion(rs.getString(4));
            historiques.add(historique);

        }
        return historiques;


    }
    public List<Historique> actualiserTableHistorique() throws SQLException {
        ReservationService reservationService = new ReservationService();

        List<Reservation> reservations ;
        reservations = reservationService.getAllReservation();

        List<Historique> historiques = new ArrayList<>();


        for (Reservation reservation : reservations) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateTransformee = LocalDate.parse(reservation.getDateReservation(), formatter);
            LocalDate dateActuelle = LocalDate.now();
            if (dateTransformee.isEqual(dateActuelle)) {


                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime heureTransformee = LocalTime.parse(reservation.getHeureReservation(), formatter2);
                LocalTime heureActuelle = LocalTime.now();

                if (heureTransformee.isAfter(heureActuelle)) {
                    String query = "SELECT p.idMembre FROM payment p JOIN reservation r ON p.idReservation = r.idReservation WHERE r.idReservation = ? ";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setInt(1, reservation.getIdReservation());
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        Historique historique = new Historique();
                        historique.setIdMembre(rs.getInt(1));
                        historique.setHeureReservtion(reservation.getHeureReservation());
                        historique.setDateReservation(reservation.getDateReservation());

                        historiques.add(historique);
                        //supp apres ins

                        String query2 = "INSERT INTO historique (idMembre, dateReservation, heureReservation) VALUES (?,?,?);";
                        PreparedStatement ps2 = connection.prepareStatement(query2);
                        ps2.setInt(1, historique.getIdMembre());
                        ps2.setString(2, historique.getDateReservation());
                        ps2.setString(3, historique.getHeureReservtion());

                        ps2.executeUpdate();
                        reservationService.supprimerReservation(reservation.getIdReservation());


                    }
                }
            }
        }

            return historiques;
    }



    }