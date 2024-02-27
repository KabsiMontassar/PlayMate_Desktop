package services;
import entity.AvisTerrain;
import entity.Terrain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDatabase;
import java.sql.*;
import java.util.List;

//*******************************************************************************************
public class AvisService  implements ITerrain<AvisTerrain>{
    private Connection connection;
    //*******************************************************************************************
    public AvisService() {connection = MyDatabase.getInstance().getConnection();}
    //*******************************************************************************************
    public void add(AvisTerrain t) throws SQLException {
        String query = "INSERT INTO avis (id, commentaire, note, date_avis) VALUES (?, ?, ?, ?)";
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
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
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
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();}
    //*******************************************************************************************
   public List<AvisTerrain> getAvisByTerrainId(int terrainId) {

       List<AvisTerrain> avisTerrains = FXCollections.observableArrayList();
        String query = "SELECT avis.*, terrain.* FROM avis JOIN terrain ON avis.id = id WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, terrainId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AvisTerrain avisTerrain = new AvisTerrain();
                avisTerrain.setIdAvis(rs.getInt("idAvis"));
                avisTerrain.setCommentaire(rs.getString("commentaire"));
                avisTerrain.setNote(rs.getInt("note"));
                avisTerrain.setDate_avis(rs.getString("date_avis"));
                // Créer un objet Terrain avec les données de la jointure
                Terrain terrain = new Terrain();
                terrain.setId(rs.getInt("id"));
                terrain.setNomTerrain(rs.getString("nomTerrain"));
                // Assigner l'objet Terrain à l'objet AvisTerrain
                avisTerrain.setTerrain(terrain);
                avisTerrains.add(avisTerrain);}
        } catch (SQLException e) {
            e.printStackTrace();}
        return avisTerrains;
    }
}