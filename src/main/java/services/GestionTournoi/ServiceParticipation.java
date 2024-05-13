package services.GestionTournoi;

import models.Participation;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceParticipation {

    private final Connection connection;

    public ServiceParticipation() {
        connection = MyDatabase.getInstance().getConnection();
    }
    public void ajouter(Participation J) throws SQLException {
        String Query = "INSERT INTO participation ( idmembre, idTournoi, Status, datec,NomEquipe ) VALUES (?,?,?,?,?)";
        PreparedStatement psParticipation = connection.prepareStatement(Query);
        psParticipation.setInt(1, J.getIdMembre());
        psParticipation.setInt(2, J.getIdTournoi());
        psParticipation.setBoolean(3, J.isStatus());
        psParticipation.setString(4, J.getDateC());
        psParticipation.setString(5, J.getNomEquipe());


        psParticipation.executeUpdate();
        System.out.println("participation ajoutee avec succés");
    }

    public void supprimer(int id) throws SQLException {

        String query = "DELETE FROM participation WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("participation supprimer avec succés");
    }


    public Participation getbyidpart(int id) throws SQLException {
        Participation par = null;
        String query = "SELECT * FROM participation WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {

            par= new Participation();
            par.setId(rs.getInt("id"));
            par.setIdMembre(rs.getInt("idmembre"));
            par.setIdTournoi(rs.getInt("idTournoi"));
            par.setStatus(rs.getBoolean("Status"));
            par.setDateC(rs.getString("datec"));
            par.setNomEquipe(rs.getString("NomEquipe"));

        }
        return par;
    }


    public List<Participation> allParticipation() throws SQLException {
        List<Participation> Tournois = new ArrayList<>();
        String query = "SELECT * FROM Tournoi";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {

            Participation pr = new Participation();

            pr.setId(rs.getInt("id"));
            pr.setNomEquipe(rs.getString("NomEquipe"));
            pr.setDateC(rs.getString("datec"));
            pr.setStatus(rs.getBoolean("Status"));
            pr.setIdMembre(rs.getInt("idmembre"));
            pr.setIdTournoi(rs.getInt("idTournoi"));



            Tournois.add(pr);
        }
        return Tournois;
    }

    public  List<String> getNomsEquipesPourMembre(int idMembre) throws SQLException {
        List<String> nomsEquipes = new ArrayList<>();
        String query = "SELECT e.nomEquipe " +
                "FROM equipe e " +
                "JOIN membreparequipe mpe ON e.idEquipe = mpe.idEquipe " +
                "WHERE mpe.idMembre = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, idMembre); // Set the idMembre parameter in the query
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            nomsEquipes.add(rs.getString("nomEquipe"));
        }

        return nomsEquipes;
    }
}