package services;

import models.Terrain;
import utils.MyDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TerrainService {
    private Connection connection;
    public TerrainService() {
        connection = MyDatabase.getInstance().getConnection();}
    public void add(Terrain t) throws SQLException {
        String query = "INSERT INTO terrain (address, gradin, vestiaire, status, nomTerrain, prix, duree, gouvernorat, image, video) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getAddress());
            ps.setBoolean(2, t.getGradin());
            ps.setBoolean(3, t.getVestiaire());
            ps.setBoolean(4, t.getStatus());
            ps.setString(5, t.getNomTerrain());
            ps.setInt(6, t.getPrix());
            ps.setInt(7, t.getDuree());
            ps.setString(8, t.getGouvernorat());
            ps.setString(9, t.getImage());
            ps.setString(10, t.getVideo());
            ps.executeUpdate();
            // Récupère l'ID généré par la base de données
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getInt(1));}}}}

    public void update(Terrain t) {
        String query = "UPDATE `terrain` SET  `address` = ?, `gradin` = ?, `vestiaire` = ?, `status` = ?, `nomTerrain` = ?, `duree` = ?, `prix` = ?, `gouvernorat` = ?, `image` = ?,  `video` = ? WHERE `id` = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, t.getAddress());
            ps.setBoolean(2, t.getGradin()); // Corrected field name
            ps.setBoolean(3, t.getVestiaire());
            ps.setBoolean(4, t.getStatus());
            ps.setString(5, t.getNomTerrain());
            ps.setInt(6, t.getDuree());
            ps.setInt(7, t.getPrix());
            ps.setString(8, t.getGouvernorat());
            ps.setString(9, t.getImage());
            ps.setString(10, t.getVideo());
            ps.setInt(11,t.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());}}
    public void delete(int id) throws SQLException{
        String query = "DELETE FROM terrain WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();}
    public List<Terrain> getAllTerrains() {
        ObservableList<Terrain> terrains = FXCollections.observableArrayList();
        String query = "SELECT * FROM terrain";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Terrain terrain = new Terrain();
                terrain.setId(rs.getInt("id"));
                terrain.setNomTerrain(rs.getString("nomTerrain"));
                terrain.setAddress(rs.getString("address"));
                terrain.setGradin(rs.getBoolean("gradin"));
                terrain.setVestiaire(rs.getBoolean("vestiaire"));
                terrain.setStatus(rs.getBoolean("status"));
                terrain.setPrix(rs.getInt("prix"));
                terrain.setDuree(rs.getInt("duree"));
                terrain.setGouvernorat(rs.getString("gouvernorat"));
                terrain.setImage(rs.getString("image"));
                terrain.setVideo(rs.getString("video"));
                terrains.add(terrain);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terrains;
    }


    public Terrain getTerrainByNom(String nom) {
        Terrain terrain = null;
        String query = "SELECT * FROM terrain WHERE nomTerrain = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                terrain = new Terrain();
                terrain.setId(rs.getInt("id"));
                terrain.setNomTerrain(rs.getString("nomTerrain"));
                terrain.setAddress(rs.getString("address"));
                terrain.setGradin(rs.getBoolean("gradin"));
                terrain.setVestiaire(rs.getBoolean("vestiaire"));
                terrain.setStatus(rs.getBoolean("status"));
                terrain.setPrix(rs.getInt("prix"));
                terrain.setDuree(rs.getInt("duree"));
                terrain.setGouvernorat(rs.getString("gouvernorat"));
                terrain.setImage((rs.getString("image")));
                terrain.setVideo((rs.getString("video")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terrain;
    }
    public Terrain getTerrainById(int id) {
        Terrain terrain = new Terrain();
        String query = "SELECT * FROM terrain WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                terrain = new Terrain();
                terrain.setId(rs.getInt("id"));
                terrain.setNomTerrain(rs.getString("nomTerrain"));
                terrain.setAddress(rs.getString("address"));
                terrain.setGradin(rs.getBoolean("gradin"));
                terrain.setVestiaire(rs.getBoolean("vestiaire"));
                terrain.setStatus(rs.getBoolean("status"));
                terrain.setPrix(rs.getInt("prix"));
                terrain.setDuree(rs.getInt("duree"));
                terrain.setGouvernorat(rs.getString("gouvernorat"));
                terrain.setImage((rs.getString("image")));
                terrain.setVideo((rs.getString("video")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return terrain;
    }

}
