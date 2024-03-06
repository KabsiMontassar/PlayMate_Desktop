package services.GestionReservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import models.*;
import utils.MyDatabase;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

import static models.TypeReservation.ReserverTerrainPourEquipe;
/*implements IService<Reservation> */
public class ReservationService {
    private static Connection connection;
    public ReservationService(){
        connection = MyDatabase.getInstance().getConnection();
    }
/*select u.*
FROM user u
join payment p on p.idMembre = u.id
JOIN reservation r ON r.idReservation = p.idReservation
WHERE r.idReservation = 6;*/
    public static User getUserWithIdReservation(int idReservation) throws SQLException {

        User user = new User();
        String query = "select u.* FROM user u join payment p on p.idMembre = u.id JOIN reservation r ON r.idReservation = p.idReservation WHERE r.idReservation = ?;";
        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1,idReservation);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("Name"));
            user.setEmail(rs.getString("Email"));


        }
        return user ;
    }

    public void ajouterReservation(Reservation reservation) throws SQLException {


        String query = "INSERT INTO reservation (isConfirm, dateReservation, heureReservation, type, idTerrain ) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setBoolean(1, false);
        ps.setString(2, reservation.getDateReservation());
        ps.setString(3, reservation.getHeureReservation());
        ps.setString(4, reservation.getType());
        ps.setInt(5, reservation.getIdTerrain());


        ps.executeUpdate();




        }

        // j ai les deux noms
    public void ajouterReservationPourLancerUnePartie(Reservation reservation) throws SQLException {

        String query = "INSERT INTO reservation (isConfirm, dateReservation, heureReservation, type, idTerrain ) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setBoolean(1, false);
        ps.setString(2, reservation.getDateReservation());
        ps.setString(3, reservation.getHeureReservation());
        ps.setString(4, reservation.getType());
        ps.setInt(5, reservation.getIdTerrain());


        ps.executeUpdate();




    }



        // tous les reservations
        public List<Reservation> getAllReservation() throws SQLException {
            List<Reservation> reservations = new ArrayList<>();
            String query = "SELECT * FROM reservation ";
            PreparedStatement ps = connection.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setIdReservation(rs.getInt("idReservation"));
                reservation.setConfirm(rs.getBoolean("isConfirm"));
                reservation.setDateReservation(rs.getString("dateReservation"));
                reservation.setHeureReservation(rs.getString("heureReservation"));
                reservation.setType(TypeReservation.valueOf(rs.getString("type")));
                reservation.setIdTerrain(rs.getInt("idTerrain"));


                reservations.add(reservation);

            }
            return reservations ;


        }


        // pour l affichage dans la table par membre
        public static List<Reservation> getAllReservationByIdMembre(int idm) throws SQLException {
            List<Reservation> reservations = new ArrayList<>();
            String query = "SELECT r.* FROM reservation r JOIN payment p ON r.idReservation = p.idReservation JOIN membre m ON p.idMembre = m.idMembre WHERE m.idMembre =   ? ";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idm);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Reservation reservation = new Reservation();
                reservation.setIdReservation(rs.getInt("idReservation"));
                reservation.setConfirm(rs.getBoolean("isConfirm"));
                reservation.setDateReservation(rs.getString("dateReservation"));
                reservation.setHeureReservation(rs.getString("heureReservation"));
                reservation.setType(TypeReservation.valueOf(rs.getString("type")));
                reservation.setIdTerrain(rs.getInt("idTerrain"));


                reservations.add(reservation);

            }
            return reservations ;


        }


    public static List<Reservation> getAllFutureUniqueReservations() throws SQLException {
        List<Reservation> allReservations = new ArrayList<>();
        Map<String, Integer> reservationUnique = new HashMap<>();
        String query = "SELECT * FROM reservation ";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Première passe pour compter les occurrences
        while (rs.next()) {
            String dateString = rs.getString("dateReservation");
            String heureString = rs.getString("heureReservation");
            LocalDateTime reservationDateTime = LocalDateTime.parse(dateString + " " + heureString, formatter);

            if (reservationDateTime.isAfter(now)) {
                int idTerrain = rs.getInt("idTerrain");
                String uniqueKey = dateString + "-" + heureString + "-" + idTerrain;

                Reservation reservation = new Reservation();
                reservation.setIdReservation(rs.getInt("idReservation"));
                reservation.setConfirm(rs.getBoolean("isConfirm"));
                reservation.setDateReservation(dateString);
                reservation.setHeureReservation(heureString);
                reservation.setType(TypeReservation.valueOf(rs.getString("type")));
                reservation.setIdTerrain(idTerrain);

                allReservations.add(reservation);
                reservationUnique.put(uniqueKey, reservationUnique.getOrDefault(uniqueKey, 0) + 1);
            }
        }

        // Filtrer pour exclure les doublons
        List<Reservation> uniqueFutureReservations = allReservations.stream()
                .filter(reservation -> reservationUnique.get(reservation.getDateReservation() + "-" + reservation.getHeureReservation() + "-" + reservation.getIdTerrain()) == 1)
                .collect(Collectors.toList());

        return uniqueFutureReservations;
    }



    public static List<Reservation> getAllFutureReservationsByIdMembre(int idm) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT r.* FROM reservation r JOIN payment p ON r.idReservation = p.idReservation JOIN membre m ON p.idMembre = m.JoueurId WHERE m.JoueurId = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idm);
        ResultSet rs = ps.executeQuery();

        LocalDate today = LocalDate.now();

        while (rs.next()) {
            String dateString = rs.getString("dateReservation");
            LocalDate reservationDate = LocalDate.parse(dateString);

            if (reservationDate.isAfter(today)) {
                Reservation reservation = new Reservation();
                reservation.setIdReservation(rs.getInt("idReservation"));
                reservation.setConfirm(rs.getBoolean("isConfirm"));
                reservation.setDateReservation(dateString); // Gardez comme String ou convertissez en LocalDate selon votre besoin
                reservation.setHeureReservation(rs.getString("heureReservation"));
                reservation.setType(TypeReservation.valueOf(rs.getString("type")));
                reservation.setIdTerrain(rs.getInt("idTerrain"));


                reservations.add(reservation);
            }
        }

        return reservations;
    }



        // avoir l info d une reservation
        public Reservation getReservationByIdReservation(int id ) throws SQLException {
        Reservation reservation = new Reservation();
            String query = "SELECT * FROM reservation WHERE idReservation  = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reservation.setIdReservation(rs.getInt("idReservation"));
                reservation.setConfirm(rs.getBoolean("isConfirm"));
                reservation.setDateReservation(rs.getString("dateReservation"));
                reservation.setHeureReservation(rs.getString("heureReservation"));
                reservation.setType(TypeReservation.valueOf(rs.getString("type")));
                reservation.setIdTerrain(rs.getInt("idTerrain"));


            }
            return reservation;


        }

        // appel de l historique
        public void supprimerReservation(int idreservation) throws SQLException {

            String query = "DELETE FROM reservation WHERE idReservation = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idreservation);
            ps.executeUpdate();

        }

    // verfier disponibilter de teerain


    public boolean VerfierDisponibleTerrain(int idTerrain, int dureeAnnoce, String heure, String date) throws SQLException {
        Reservation reservation = new Reservation();

        String query = "SELECT * FROM reservation WHERE idTerrain  = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idTerrain);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            return true;
        } else {
            do {
                reservation.setIdReservation(rs.getInt("idReservation"));
                reservation.setConfirm(rs.getBoolean("isConfirm"));
                reservation.setDateReservation(rs.getString("dateReservation"));
                reservation.setHeureReservation(rs.getString("heureReservation"));
                reservation.setType(TypeReservation.valueOf(rs.getString("type")));
                reservation.setIdTerrain(rs.getInt("idTerrain"));


                if (reservation.getDateReservation() != null && sontMemesDates(reservation.getDateReservation(), date)) {
                    LocalTime heureMatchReserve = LocalTime.parse(reservation.getHeureReservation());
                    Duration dureeReservation = Duration.ofHours(dureeAnnoce / 60).plusMinutes(dureeAnnoce % 60);
                    LocalTime heureNouveauMatch = LocalTime.parse(heure);

                    boolean seCroisent = verifCroisementHoraire(heureMatchReserve, dureeReservation, heureNouveauMatch);
                    if (seCroisent) {
                        return false;
                    } else {
                        return true;
                    }
                }
            } while (rs.next());

            return true;
        }
    }

    public boolean sontMemesDates(String dateStr1, String dateStr2) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date1 = LocalDate.parse(dateStr1, formatter);
        LocalDate date2 = LocalDate.parse(dateStr2, formatter);

        return date1.equals(date2);
    }

    private boolean verifCroisementHoraire(LocalTime heureMatchReserve, Duration dureeReservation, LocalTime heureNouveauMatch) {
        LocalTime finReserve = heureMatchReserve.plus(dureeReservation);
        LocalTime finNouveau = heureNouveauMatch.plus(dureeReservation);
        return !finReserve.isBefore(heureNouveauMatch) && !finNouveau.isBefore(heureMatchReserve);
    }

    public int getLastIdReservationAddRecently() throws SQLException {
        Reservation reservation = new Reservation();
        String query = "SELECT idReservation FROM reservation ORDER BY idReservation DESC LIMIT 1;";
        PreparedStatement ps = connection.prepareStatement(query);
        int dernierIdAjouter = 0;
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            dernierIdAjouter = rs.getInt(1);
        }
        return dernierIdAjouter;


    }



    //************************** appel de la partie blacklist dans la classe blacklistServices            a faire !
    // a changer apres modification db
    /*
        public  void annulerReservation(int idReservation , int idMembre) throws SQLException {
            ReservationService reservationService =  new ReservationService();
            Reservation reservation = reservationService.getReservationByIdReservation(idReservation);


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateTransformee = LocalDate.parse(reservation.getDateReservation(), formatter);
            LocalDate dateActuelle = LocalDate.now();
            if(dateTransformee.isEqual(dateActuelle)){
                BlackList blackList = new BlackList();
                blackList.setReservation(reservation);
                /*
                blackList.setIdTerrain(reservation.getIdTerrain());
                blackList.setCause("annulation apres 24h");
                blackList.setDuree(30);
                blackList.setIdMembre(idMembre);

                String query = "INSERT INTO blacklist (idReservation, idMembre, idTerrain, duree, cause) VALUES (?,?,?,?,?);";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, idReservation);
                ps.setInt(2, blackList.getIdMembre());
                ps.setInt(3, blackList.getIdMembre());
                ps.setInt(4, blackList.getDuree());
                ps.setString(5, blackList.getCause());

                ps.executeUpdate();


            }

        }

     */



}
/*
*  public boolean updateNomEquipe2(int idReservation, String nomEquipe2) throws SQLException {
        String query = "UPDATE reservation SET nomEquipe2 = ? WHERE idReservation = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nomEquipe2);
            ps.setInt(2, idReservation);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return false ;
            }
        } catch (SQLException e) {
            return  false;
        }
        return true ;
    }*/