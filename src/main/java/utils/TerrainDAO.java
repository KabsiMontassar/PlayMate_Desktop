package utils;
import models.Terrain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TerrainDAO {
    private Connection connection;
    public TerrainDAO(Connection connection) {this.connection = connection;}
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
                    terrains.add(terrain);}
            } catch (SQLException e) {
                e.printStackTrace();}
            return terrains;}
}