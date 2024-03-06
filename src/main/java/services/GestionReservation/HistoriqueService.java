package services.GestionReservation;

import models.Historique;
import models.Reservation;
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

        String query = "SELECT * FROM historique h JOIN reservation r ON h.idReservation = r.idReservation JOIN payment p ON r.idReservation = p.idReservation WHERE p.idMembre = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idmembre);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Historique historique = new Historique();
            historique.setIdHistorique(rs.getInt("idHistorique"));
            historique.setIdReservation(rs.getInt("idReservation"));
            historiques.add(historique);

        }
        return historiques;


    }
    public void actualiserTableHistorique() throws SQLException {
        ReservationService reservationService = new ReservationService();

        List<Reservation> reservations ;
        reservations = reservationService.getAllReservation();




        for (Reservation reservation : reservations) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateReservation = LocalDate.parse(reservation.getDateReservation(), formatter);
            LocalDate dateActuelle = LocalDate.now();
            if (dateReservation.isEqual(dateActuelle)) {


                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime heureReservation = LocalTime.parse(reservation.getHeureReservation(), formatter2);
                LocalTime heureActuelle = LocalTime.now();

                    if (heureReservation.isBefore(heureActuelle)) {/*isafter*/

                        Historique historique = new Historique();
                        historique.setIdReservation(reservation.getIdReservation());

                        String query2 = "INSERT INTO historique (idReservation) VALUES (?);";
                        PreparedStatement ps2 = connection.prepareStatement(query2);
                        ps2.setInt(1, historique.getIdReservation());

                        ps2.executeUpdate();
                }
            }
        }

    }
    public List<Historique> getAllHistorique() throws SQLException {
        List<Historique> historiques = new ArrayList<>();
        String query = "SELECT * FROM historique;";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Historique historique = new Historique();
            historique.setIdHistorique(rs.getInt("idHistorique"));
            historique.setIdReservation(rs.getInt("idReservation"));
            historiques.add(historique);

        }
        return historiques;
    }


    }

    /*
idHistorique
dateReservation
heureReservation
idReservation
idReservation
isConfirm
dateReservation
heureReservation
type
idTerrain
idPayment
idMembre
idReservation
datePayment
horairePayment
paymentRef        */
