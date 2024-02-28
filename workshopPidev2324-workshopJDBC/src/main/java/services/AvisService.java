package services;

import entity.AvisTerrain;
import entity.Terrain;
import utils.MyDatabase;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
//*******************************************************************************************
public class AvisService implements ITerrain<AvisTerrain> {
    private Connection connection; //Déclaration d'une variable de connexion à la base de données.
    //*******************************************************************************************
    public AvisService() {
        connection = MyDatabase.getInstance().getConnection();//Constructeur qui initialise la connexion à la base de données en utilisant MyDatabase.
    }
    //*******************************************************************************************
    public void add(AvisTerrain t) throws SQLException {
        String query = "INSERT INTO avis (terrain_id, commentaire, note, date_avis) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getTerrain().getId()); // Utilisation de l'ID du terrain
            ps.setString(2, t.getCommentaire());
            ps.setInt(3, t.getNote());
            ps.setString(4, t.getDate_avis());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setIdAvis(rs.getInt(1));}}}}
    //*******************************************************************************************
    public void update(AvisTerrain t) {
        String query = "UPDATE `avis` SET  `commentaire` = ?, `note` = ?, `date_avis` = ? WHERE `idAvis` = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, t.getCommentaire());
            ps.setInt(2, t.getNote());
            ps.setString(3, t.getDate_avis());
            ps.setInt(4, t.getIdAvis());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());}}
    //*******************************************************************************************
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM avis WHERE idAvis = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();}}
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
                avisTerrains.add(avisTerrain);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avisTerrains;}}