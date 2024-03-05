package services.GestionTournoi;

import models.Organisateur;
import models.Participation;
import models.Tournoi;
import utils.MyDatabase;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTournoi {
    private final Connection connection;

    public ServiceTournoi() {
        connection = MyDatabase.getInstance().getConnection();
    }
    public void ajouter(Tournoi J) throws SQLException {
        String Query = "INSERT INTO tournoi ( nbmaxequipe, nom, affiche, address, datedebut, datefin, idorganisateur) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement psTournoi = connection.prepareStatement(Query);
        psTournoi.setInt(1, J.getNbrquipeMax());
        psTournoi.setString(2, J.getNom());
        psTournoi.setString(3, J.getAffiche());
        psTournoi.setString(4, J.getAddress());
        psTournoi.setString(5, J.getDatedebut());
        psTournoi.setString(6, J.getDatefin());
        psTournoi.setInt(7,J.getOrg());

        psTournoi.executeUpdate();
        System.out.println("tournoi ajoutee avec succés");
    }

    public void supprimer(int id) throws SQLException {

        String query = "DELETE FROM participation WHERE idTournoi = ?";
       String query1 = "DELETE FROM tournoi WHERE id = ?";
       PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
       ps.executeUpdate();
        PreparedStatement ps1 = connection.prepareStatement(query1);
        ps1.setInt(1, id);
        ps1.executeUpdate();

        System.out.println("tournoi supprimer avec succés");
    }

    public void modifier(Tournoi t) throws SQLException {
        String query = "UPDATE tournoi SET NbmaxEquipe = ?, nom = ?  , affiche = ? , datedebut = ? , datefin = ? , address = ?  WHERE id = ?";


        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, t.getNbrquipeMax());
        ps.setString(2, t.getNom());
        ps.setString(3, t.getAffiche());
        ps.setString(4, t.getDatedebut());
        ps.setString(5, t.getDatefin());
        ps.setString(6, t.getAddress());
        ps.setInt(7, t.getId());
        ps.executeUpdate();
        System.out.println("tournoi modifieé avec succés");
    }
public Organisateur getbyidorg(int id) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
    Organisateur org = null; // Initialize Tournoi as null
    String query = "SELECT * FROM user WHERE id = ?";
    PreparedStatement ps = connection.prepareStatement(query);
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        // If a Tournoi is found, populate the Tournoi object
        org= new Organisateur();
        org.setId(rs.getInt("id"));
        org.setName(rs.getString("nom"));
        org.setEmail(rs.getString("email"));
        org.setPassword(rs.getString("password"));
        org.setAddress(rs.getString("address"));
        org.setPhone(rs.getInt("Phone"));
        org.setAge(rs.getInt("age"));
        org.setRole(rs.getString("role"));




    }
    return org;
}
    public Tournoi getbyid(int id) throws SQLException {
            Tournoi tr = null; // Initialize Tournoi as null
            String query = "SELECT * FROM Tournoi WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                  tr= new Tournoi();
                tr.setId(rs.getInt("id"));
                tr.setNbrquipeMax(rs.getInt("NbmaxEquipe"));
                tr.setNom(rs.getString("nom"));
                tr.setAffiche(rs.getString("affiche"));
                tr.setDatedebut(rs.getString("datedebut"));
                tr.setDatefin(rs.getString("datefin"));

                tr.setAddress(rs.getString("address"));

            }
            return tr; // Return either the populated Tournoi or null if no Tournoi is found
    }

    public List<Tournoi> allTournoi() throws SQLException {
        List<Tournoi> Tournois = new ArrayList<>();
        String query = "SELECT * FROM Tournoi";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {

            Tournoi Tr = new Tournoi();

            Tr.setId(rs.getInt("id"));
            Tr.setNbrquipeMax(rs.getInt("NbmaxEquipe"));
            Tr.setNom(rs.getString("nom"));
            Tr.setAffiche(rs.getString("affiche"));
            Tr.setDatedebut(rs.getString("datedebut"));
            Tr.setDatefin(rs.getString("datefin"));

            Tr.setAddress(rs.getString("address"));

            Tournois.add(Tr);
        }
        return Tournois;
    }

    public List<Participation> getparticipationbytournoiid(int id) throws SQLException {
        List<Participation> Participations = new ArrayList<>();
        String query = "SELECT * FROM participation WHERE idTournoi = ?";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery(); // Correction ici: ne passez pas la requête en argument
        while (rs.next()) {
            Participation pt = new Participation();
            pt.setIdMembre(rs.getInt("idmembre"));
            pt.setIdTournoi(rs.getInt("idTournoi"));
            pt.setId(rs.getInt("id"));
            pt.setNomEquipe(rs.getString("NomEquipe"));
            Participations.add(pt);
        }
        return Participations;
    }




}
