package services.GestionReservation;

import models.BlackList;
import services.GestionUser.UserService;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlacklistService {

    private Connection connection;

    public BlacklistService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    public void ajouterBlackList(BlackList blackList) throws SQLException {
        String query = "INSERT INTO blacklist (idReservation, duree, cause) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, blackList.getIdReservation());
            ps.setInt(2, blackList.getDuree());
            ps.setString(3, blackList.getCause());
            ps.executeUpdate();
            desactiverCompte(blackList.getIdReservation());
        }
    }
    public void desactiverCompte(int idReservation) throws SQLException {

        String query = "UPDATE reservation SET type = 'Compte_desactive' WHERE idReservation = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idReservation);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Mise à jour échouée, aucune ligne affectée.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erreur lors de la mise à jour de la réservation: " + e.getMessage(), e);
        }
    }


    public void supprimerBlackList(int idBlackList) throws SQLException {
        String query = "DELETE FROM blacklist WHERE idBlackList = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idBlackList);
            ps.executeUpdate();
        }
    }

    public List<BlackList> getAllBlackLists() throws SQLException {
        List<BlackList> blackLists = new ArrayList<>();
        String query = "SELECT * FROM blacklist";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BlackList blackList = new BlackList();
                    blackList.setIdBlackList(rs.getInt("idBlackList"));
                    blackList.setIdReservation(rs.getInt("idReservation"));
                    blackList.setDuree(rs.getInt("duree"));
                    blackList.setCause(rs.getString("cause"));

                    blackLists.add(blackList);
                }
            }
        }
        return blackLists;
    }

}
/*
public List<BlackList> getBlackListParMembre(int idMembre) throws SQLException {
    List<BlackList> blackLists = new ArrayList<>();
    String query = "SELECT * FROM blacklist WHERE idMembre = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, idMembre);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BlackList blackList = new BlackList();
                blackList.setIdBlackList(rs.getInt("idBlackList"));
                blackList.setIdMembre(rs.getInt("idMembre"));
                blackList.setIdReservation(rs.getInt("idReservation"));
                blackList.setDuree(rs.getInt("duree"));
                blackList.setCause(rs.getString("cause"));

                blackLists.add(blackList);
            }
        }
    }
    return blackLists;
}
*/