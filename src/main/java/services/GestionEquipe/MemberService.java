package services.GestionEquipe;

import models.Equipe;
import models.User;
import utils.MyDatabase;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberService {

    private Connection connection;

    public MemberService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    public List<User> findAll(){
        List<User> membres = new ArrayList<>();
        String query = "SELECT m.* FROM membre m ";
        try (PreparedStatement ps = connection.prepareStatement(query)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User membre = new User();
                    membre.setId(rs.getInt("id"));
                    membre.setName(rs.getString("Name"));
                    membre.setEmail(rs.getString("Email"));
                    // Set other member attributes as needed
                    membres.add(membre);
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return membres;
    }


    public List<User> findMembresByEquipe(int idEquipe) throws SQLException {
        List<User> membres = new ArrayList<>();
        String query = "SELECT m.* FROM User m JOIN membreParEquipe me ON m.id = me.idMembre WHERE me.idEquipe = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idEquipe);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User membre = new User();
                    membre.setId(rs.getInt("id"));
                    membre.setName(rs.getString("Name"));
                    membre.setEmail(rs.getString("Email"));
                    // Set other member attributes as needed
                    membres.add(membre);
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return membres;
    }  public List<User> findExternMembresByEquipe(int idEquipe) throws SQLException {
        List<User> membres = new ArrayList<>();
        String query = "SELECT m.* FROM User m JOIN membreParEquipe me ON m.id = me.id WHERE me.idEquipe <> ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idEquipe);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User membre = new User();
                    membre.setId(rs.getInt("id"));
                    membre.setName(rs.getString("Name"));
                    membre.setEmail(rs.getString("Email"));
                    // Set other member attributes as needed
                    membres.add(membre);
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return membres;
    }

//    public void ajouterEquipe(Equipe equipe) throws SQLException {
//        String query = "INSERT INTO equipe (nomEquipe , nbreJoueur) VALUES (?,?)";
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setString(1, equipe.getNomEquipe());
//            ps.setInt(2,equipe.getNbreJoueur());
//            ps.executeUpdate();
//        }
//    }
//    public void ajouterMembreParEquipe(int idEquipe , int idMembre) throws SQLException{
//
//        String query = "INSERT INTO `membreparequipe` (`idEquipe`, `idMembre`) VALUES (?, ?)";
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setInt(1, idEquipe);
//            ps.setInt(2,idMembre);
//            ps.executeUpdate();
//        }
//    }
//
//    public List<Equipe> getEquipesParMembre(int idMembre) throws SQLException {
//        List<Equipe> equipes = new ArrayList<>();
//        String query = "SELECT e.* FROM equipe e JOIN membreParEquipe mpe ON e.idEquipe = mpe.idEquipe WHERE mpe.idMembre = ?";
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setInt(1, idMembre);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    Equipe equipe = new Equipe();
//                    equipe.setIdEquipe(rs.getInt("idEquipe"));
//                    equipe.setNomEquipe(rs.getString("nomEquipe"));
//                    equipe.setNbreJoueur(rs.getInt("nbreJoueur"));
//
//                    equipes.add(equipe);
//                }
//            }
//        }
//        return equipes;
//    }
//
//    public List<Membre> getMembresByIdEquipe(int idEquipe) throws SQLException {
//        List<Membre> membres = new ArrayList<>();
//        String query = "SELECT m.* FROM membre m JOIN membreParEquipe me ON m.idMembre = me.idMembre WHERE me.idEquipe = ?";
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setInt(1, idEquipe);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    Membre membre = new Membre();
//                    membre.setId(rs.getInt("idMembre"));
//                    membre.setNom(rs.getString("nom"));
//                    membre.setPrenom(rs.getString("prenom"));
//                    // Set other member attributes as needed
//                    membres.add(membre);
//                }
//            }
//        }
//        return membres;
//    }
//
//    public int getIdEquipeParNom(String nomEquipe) {
//
//        String sql = "SELECT idEquipe FROM equipe WHERE nomEquipe = ?";
//
//        try (
//                PreparedStatement pstmt = connection.prepareStatement(sql)) {
//
//
//            pstmt.setString(1, nomEquipe);
//
//            try (ResultSet rs = pstmt.executeQuery()) {
//
//                if (rs.next()) {
//
//                    return rs.getInt("idEquipe");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        }
//
//        return 0;
//    }
//
//
//
//    public void supprimerEquipe(String nomEquipe) throws SQLException {
//        int idEquipe = getIdEquipeParNom(nomEquipe);
//        String sql = "DELETE FROM membreparequipe WHERE idEquipe = ?";
//        try (PreparedStatement ps1 = connection.prepareStatement(sql)){
//            ps1.setInt(1, idEquipe);
//            ps1.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        String query = "DELETE FROM equipe WHERE nomEquipe = ?";
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setString(1, nomEquipe);
//            ps.executeUpdate();
//        }
//    }
//
//    public int getLastIdEquipeAddRecently() throws SQLException {
//        Equipe equipe = new Equipe();
//        String query = "SELECT idEquipe FROM equipe ORDER BY idEquipe DESC LIMIT 1;";
//        PreparedStatement ps = connection.prepareStatement(query);
//        int dernierIdAjouter = 0;
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            dernierIdAjouter = rs.getInt(1);
//        }
//        return dernierIdAjouter;
//
//
//    }
}
