package services.GestionReservation;

import models.Paiement;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaiementService {
    private Connection connection;
    public PaiementService(){
        connection = MyDatabase.getInstance().getConnection();
    }

    public boolean AjouterPaiement(Paiement paiement,String paymentRef1) throws SQLException {
        String query = "INSERT INTO Payment (idMembre, idReservation, datePayment, horairePayment,paymentRef) VALUES (?,?,?,?,?);";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, paiement.getIdmembre());
        ps.setInt(2, paiement.getIdreservation());
        ps.setString(3, paiement.getDate());
        ps.setString(4, paiement.getHeure());
        ps.setString(5,paymentRef1);

//        ps.executeUpdate();
        int lignesAffectees = ps.executeUpdate();

        return lignesAffectees > 0;


    }
//    public boolean confirmerPaiment(int idpaiement){
//        String query = "UPDATE payment SET Payed = TRUE WHERE idPayment = ?;";
//        PreparedStatement ps = null;
//        try {
//            ps = connection.prepareStatement(query);
//
//            ps.setInt(1, idpaiement);
//            ps.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return false;
//    }


    public int getLastIdPayment() throws SQLException {
        int lastIdPayment = -1; // Ou toute autre valeur par défaut selon votre logique

        String query = "SELECT idPayment FROM payment ORDER BY idPayment DESC LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                lastIdPayment = rs.getInt("idPayment");
            }
        }

        return lastIdPayment;
    }
}
