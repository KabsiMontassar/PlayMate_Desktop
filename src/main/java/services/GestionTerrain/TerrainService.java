package services.GestionTerrain;

import models.Terrain;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TerrainService {
    private final Connection connection;
    //*******************************************************************************************
    public TerrainService() {
        connection = MyDatabase.getInstance().getConnection();
    }
    //*******************************************************************************************
    public void add(Terrain t) throws SQLException {
        String query = "INSERT INTO terrain (address, gradin, vestiaire, status, nomTerrain, prix, duree, gouvernorat, image, video) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getAddress());
            ps.setBoolean(2, t.getGradin());
            ps.setBoolean(3, t.getVestiaire());
            ps.setBoolean(4, t.getStatus());
            ps.setString(5, t.getNomTerrain());
            ps.setFloat(6, t.getPrix());
            ps.setInt(7, t.getDuree());
            ps.setString(8, t.getGouvernorat());
            ps.setString(9, t.getImage());
            ps.setString(10, t.getVideo());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getInt(1));}}}}
    //*******************************************************************************************
    public void update(Terrain t) {
        String query = "UPDATE `terrain` SET  `address` = ?, `gradin` = ?, `vestiaire` = ?, `status` = ?, `nomTerrain` = ?, `duree` = ?, `prix` = ?, `gouvernorat` = ?, `image` = ?,  `video` = ? WHERE `id` = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, t.getAddress());
            ps.setBoolean(2, t.getGradin());
            ps.setBoolean(3, t.getVestiaire());
            ps.setBoolean(4, t.getStatus());
            ps.setString(5, t.getNomTerrain());
            ps.setInt(6, t.getDuree());
            ps.setFloat(7, t.getPrix());
            ps.setString(8, t.getGouvernorat());
            ps.setString(9, t.getImage());
            ps.setString(10, t.getVideo());
            ps.setInt(11, t.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée
            e.printStackTrace();}}
    //*******************************************************************************************
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM avis WHERE terrain_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();}
        String query1 = "DELETE FROM terrain WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query1)) {
            ps.setInt(1, id);
            ps.executeUpdate();}}
    //*******************************************************************************************
    public List<Terrain> getAllTerrains() {
        List<Terrain> terrains = new ArrayList<>();
        String query = "SELECT * FROM terrain";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Terrain terrain = createTerrainFromResultSet(rs);
                terrains.add(terrain);}
        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée
            e.printStackTrace();
        }return terrains;}
    //*******************************************************************************************
    public Optional<Terrain> getTerrainByNom(String nom) {
        String query = "SELECT * FROM terrain WHERE nomTerrain = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nom);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(createTerrainFromResultSet(rs));
                }}
        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée
            e.printStackTrace();}
        return Optional.empty();}
    //*******************************************************************************************
    public Optional<Terrain> getTerrainById(int id) {
        String query = "SELECT * FROM terrain WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(createTerrainFromResultSet(rs));
                }}} catch (SQLException e) {
            // Gérer l'exception de manière appropriée
            e.printStackTrace();}return Optional.empty();}
    //*******************************************************************************************
    private Terrain createTerrainFromResultSet(ResultSet rs) throws SQLException {
        Terrain terrain = new Terrain();
        terrain.setId(rs.getInt("id"));
        terrain.setNomTerrain(rs.getString("nomTerrain"));
        terrain.setAddress(rs.getString("address"));
        terrain.setGradin(rs.getBoolean("gradin"));
        terrain.setVestiaire(rs.getBoolean("vestiaire"));
        terrain.setStatus(rs.getBoolean("status"));
        terrain.setPrix(rs.getFloat("prix"));
        terrain.setDuree(rs.getInt("duree"));
        terrain.setGouvernorat(rs.getString("gouvernorat"));
        terrain.setImage(rs.getString("image"));
        terrain.setVideo(rs.getString("video"));
        return terrain;}
    //*******************************************************************************************
    public List<Terrain> getAllTerrainsByAddress(String address) {
        List<Terrain> terrainsByAddress = new ArrayList<>();
        String query = "SELECT * FROM terrain WHERE address = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, address);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Terrain terrain = createTerrainFromResultSet(rs);
                    terrainsByAddress.add(terrain);}}
        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée
            e.printStackTrace();}
        return terrainsByAddress;}
    public List<Terrain> getAllTerrainsByGouvernorat(String gouvernorat) {
        List<Terrain> terrainsByGouvernorat = new ArrayList<>();
        String query = "SELECT * FROM terrain WHERE gouvernorat = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, gouvernorat);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Terrain terrain = createTerrainFromResultSet(rs);
                    terrainsByGouvernorat.add(terrain);}}
        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée
            e.printStackTrace();}
        return terrainsByGouvernorat;}
   /* public List<String> getNamesOfTerrain() {
        List<String> names = new ArrayList<>();
        String query = "SELECT nomTerrain FROM terrain"; // Assurez-vous que le nom de la table est correct
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                names.add(rs.getString("nomTerrain"));
            }
        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée
            e.printStackTrace();
        }
        return names;
    }
*/
}