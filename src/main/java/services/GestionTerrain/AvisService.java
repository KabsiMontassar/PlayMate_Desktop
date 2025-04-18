package services.GestionTerrain;

import models.*;
import utils.MyDatabase;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
//*******************************************************************************************
public class AvisService {
    private Connection connection; //Déclaration d'une variable de connexion à la base de données.
    public AvisService() {connection = MyDatabase.getInstance().getConnection();}
    //*******************************************************************************************
    public void add(AvisTerrain t) throws SQLException {
        String query = "INSERT INTO avis (terrain_id, commentaire, note) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getTerrain_id()); // Utilisation de l'ID du terrain
            ps.setString(2, t.getCommentaire());
            ps.setInt(3, t.getNote());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setIdAvis(rs.getInt(1));}}}}
    //*******************************************************************************************
    public List<AvisTerrain> getAvisByTerrainId(int terrainId) {
        List<AvisTerrain> avisTerrains = new ArrayList<>();
        String query = "SELECT avis.*, terrain.* FROM avis JOIN terrain ON avis.terrain_id = terrain.id WHERE terrain.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, terrainId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AvisTerrain avisTerrain = new AvisTerrain();
                avisTerrain.setIdAvis(rs.getInt("idAvis"));
                avisTerrain.setCommentaire(rs.getString("commentaire"));
                avisTerrain.setNote(rs.getInt("note"));
                Terrain terrain = new Terrain();
                terrain.setId(rs.getInt("id"));
                avisTerrain.setTerrain(terrain);
                avisTerrains.add(avisTerrain);}
        } catch (SQLException e) {
            e.printStackTrace();}return avisTerrains;}

    public int getPhoneNumberForAvis(int avisId) throws SQLException {
        int phoneNumber = 0;
        String query = "SELECT u.Phone " +
                "FROM avis a " +
                "JOIN terrain t ON a.terrain_id = t.id " +
                "JOIN proprietaire_de_terrain pdt ON t.idprop = pdt.proprietaire_de_terrain_id " +
                "JOIN user u ON pdt.proprietaire_de_terrain_id = u.id " +
                "WHERE a.idAvis = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setInt(1, avisId); // Set the avisId parameter in the query
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            phoneNumber = rs.getInt("Phone");
        }
        rs.close();
        pst.close();
        return phoneNumber;
    }
    public List<Terrain> getTerrainsOrderByCommentaires() throws SQLException {
        List<Terrain> terrains = new ArrayList<>();
        String query = "SELECT terrain.id, terrain.nomTerrain, COUNT(avis.idAvis) AS nb_commentaires " +
                "FROM terrain LEFT JOIN avis ON terrain.id = avis.terrain_id " +
                "GROUP BY terrain.id, terrain.nomTerrain " +
                "ORDER BY nb_commentaires ASC"; // ASC pour trier du moins au plus commenté
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Terrain terrain = new Terrain();
                terrain.setId(rs.getInt("id"));
                terrain.setNomTerrain(rs.getString("nomTerrain"));
                terrain.setNbCommentaires(rs.getInt("nb_commentaires"));
                terrains.add(terrain);
            }
        }
        return terrains;
    }


}