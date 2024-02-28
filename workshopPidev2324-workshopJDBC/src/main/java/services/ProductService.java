package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Product;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IService<Product> {

    private Connection connection;

    public ProductService() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void add(Product product) throws SQLException {
        String sql = "insert into product (nom,description,prix,image,categorie) VALUES (?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, product.getNom());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setInt(3, product.getPrix());
        preparedStatement.setString(4, product.getImage());
        preparedStatement.setInt(5,product.getCategorie());
        preparedStatement.executeUpdate();
    }


    @Override
    public void update(Product product, int id) throws SQLException {
        String sql = "UPDATE product SET nom = ?,  description = ?,  prix = ?, image = ?,categorie = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, product.getNom());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setInt(3, product.getPrix());
        preparedStatement.setString(4, product.getImage());
        preparedStatement.setInt(5, product.getCategorie());
        preparedStatement.setInt(6, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from product where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Product> getAll() throws SQLException {
        String sql = "select * from product";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Product> products = FXCollections.observableArrayList();
        while (rs.next()) {
            Product u = new Product();
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setDescription(rs.getString("description"));
            u.setPrix(rs.getInt("prix"));
            u.setImage(rs.getString("image"));
            u.setCategorie(rs.getInt("categorie"));
            products.add(u);
        }
        return products;
    }

    @Override
    public Product getById(int idproduct) throws SQLException {
        String sql = "SELECT `nom`, `prix` FROM `product` WHERE `id` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, idproduct);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String nom = resultSet.getString("nom");
            int prix = resultSet.getInt("prix");

            return new Product(idproduct, nom, prix);
        } else {
            // Handle the case when no matching record is found
            return null;
        }

    }

}
