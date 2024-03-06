package services.GestionProduit;

import javafx.collections.FXCollections;
import models.Categorie;
import services.GestionProduit.IService;
import utils.MyDatabase;

import java.sql.*;
import java.util.List;

public class CategorieService implements IService<Categorie> {

    private Connection connection;

    public CategorieService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Categorie categorie) throws SQLException {
        String sql = "insert into categorie (nom,description) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, categorie.getNom());
        preparedStatement.setString(2, categorie.getDescription());
        preparedStatement.executeUpdate();

    }

    @Override
    public void update(Categorie categorie, int id) throws SQLException {
        String sql = "UPDATE categorie SET nom = ?,  description = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, categorie.getNom());
        preparedStatement.setString(2, categorie.getDescription());
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from categorie where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Categorie> getAll() throws SQLException {
        String sql = "select * from categorie";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Categorie> categories = FXCollections.observableArrayList();
        while (rs.next()) {
            Categorie u = new Categorie();
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setDescription(rs.getString("description"));


            categories.add(u);
        }
        return categories;
    }


    @Override
    public Categorie getById(int id) throws SQLException {
        String sql = "SELECT `nom`, `description` FROM `categorie` WHERE `id` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String nom = resultSet.getString("nom");
            String description = resultSet.getString("description");

            return new Categorie(id, nom, description);
        } else {
            // Handle the case when no matching record is found
            return null;
        }
    }


}
