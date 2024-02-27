package services.GestionUser;

import models.*;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {

    private final Connection connection;

    public UserService() {
        connection = MyDatabase.getInstance().getConnection();
    }



    public User getByEmail(String e) throws SQLException {
        User user = new User(); // Initialize user as null
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, e);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {


            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setAge(rs.getInt("age"));
            user.setPhone(rs.getInt("phone"));

            user.setAddress(rs.getString("address"));


            user.setRole(rs.getString("role"));
            user.setImage(rs.getString("Image"));
            user.setStatus(rs.getBoolean("Status"));
            user.setDate_de_Creation(rs.getString("DatedeCreation"));
            user.setVerificationCode(rs.getString("VerificationCode"));
            user.setVerified(rs.getBoolean("isVerified"));



        }
        return user;
    }




    public boolean userExist(String e) throws SQLException{
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, e);
        ResultSet rs = ps.executeQuery();

        return rs.next() && rs.getInt(1) > 0;
        


    }




    public void addJoueur(Joueur J ) throws SQLException{
        String QueryToUser = "INSERT INTO user (email , password , name ,Age ,Phone, role,Status , DatedeCreation ,VerificationCode,isVerified ) VALUES (?,?,?,?,?,?,?, ?, ?, ?)";
        PreparedStatement psUser = connection.prepareStatement(QueryToUser);
        psUser.setString(1, J.getEmail());
        psUser.setString(2, J.getPassword());
        psUser.setString(3, J.getName());
        psUser.setInt(4, J.getAge());
        psUser.setInt(5, J.getPhone());
        psUser.setString(6, J.getRole().toString());
        psUser.setBoolean(7,J.getStatus());
        psUser.setString(8,J.getDate_de_Creation());
        psUser.setString(9,J.getVerificationCode());
        psUser.setBoolean(10,J.getVerified());
        


        psUser.executeUpdate();

        String QueryToJoueur = "INSERT INTO Joueur (JoueurId ) VALUES (?)";
        int id = getByEmail(J.getEmail()).getId();
        PreparedStatement psJoueur = connection.prepareStatement(QueryToJoueur);
        psJoueur.setInt(1, id);
        psJoueur.executeUpdate();
    }
    public void addFournisseur(Fournisseur F) throws SQLException{
        String QueryToUser = "INSERT INTO user (email , password , name ,Age ,Phone, role,Status , DatedeCreation ,VerificationCode,isVerified ) VALUES (?,?,?,?,?,?,?, ?, ?, ?)";
        PreparedStatement psUser = connection.prepareStatement(QueryToUser);
        psUser.setString(1, F.getEmail());
        psUser.setString(2, F.getPassword());
        psUser.setString(3, F.getName());
        psUser.setInt(4, F.getAge());
        psUser.setInt(5, F.getPhone());
        psUser.setString(6, F.getRole().toString());
        psUser.setBoolean(7,F.getStatus());
        psUser.setString(8,F.getDate_de_Creation());
        psUser.setString(9,F.getVerificationCode());
        psUser.setBoolean(10,F.getVerified());
        psUser.executeUpdate();
        String QueryToFournisseur = "INSERT INTO fournisseur (Fournisseur_id , Nom_Sociéte ) VALUES (?,?)";
        int id = getByEmail(F.getEmail()).getId();
        PreparedStatement psFournisseur = connection.prepareStatement(QueryToFournisseur);
        psFournisseur.setInt(1, id);
        psFournisseur.setString(2, F.getNom_Societe());

        psFournisseur.executeUpdate();
    }
    public void addOrganisateur(Organisateur O) throws SQLException{
        String QueryToUser = "INSERT INTO user (email , password , name ,Age ,Phone, role,Status , DatedeCreation ,VerificationCode,isVerified ) VALUES (?,?,?,?,?,?,?, ?, ?, ?)";
        PreparedStatement psUser = connection.prepareStatement(QueryToUser);
        psUser.setString(1, O.getEmail());
        psUser.setString(2, O.getPassword());
        psUser.setString(3, O.getName());
        psUser.setInt(4, O.getAge());
        psUser.setInt(5, O.getPhone());
        psUser.setString(6, O.getRole().toString());
        psUser.setBoolean(7,O.getStatus());
        psUser.setString(8,O.getDate_de_Creation());
        psUser.setString(9,O.getVerificationCode());
        psUser.setBoolean(10,O.getVerified());
        psUser.executeUpdate();
        String QueryToOrganisateur = "INSERT INTO organisateur (Organisateur_id , Nom_Organisation ) VALUES (?,?)";
        int id = getByEmail(O.getEmail()).getId();
        PreparedStatement psOrganisateur = connection.prepareStatement(QueryToOrganisateur);
        psOrganisateur.setInt(1, id);
        psOrganisateur.setString(2, O.getNom_Organisation());

        psOrganisateur.executeUpdate();
    }
    public void addProprietairedeTerarin(Proprietaire_de_terrain P) throws SQLException{
        String QueryToUser = "INSERT INTO user (email , password , name ,Age ,Phone, role,Status , DatedeCreation ,VerificationCode,isVerified ) VALUES (?,?,?,?,?,?,?, ?, ?, ?)";
        PreparedStatement psUser = connection.prepareStatement(QueryToUser);
        psUser.setString(1, P.getEmail());
        psUser.setString(2, P.getPassword());
        psUser.setString(3, P.getName());
        psUser.setInt(4, P.getAge());
        psUser.setInt(5, P.getPhone());
        psUser.setString(6, P.getRole().toString());
        psUser.setBoolean(7,P.getStatus());
        psUser.setString(8,P.getDate_de_Creation());
        psUser.setString(9,P.getVerificationCode());
        psUser.setBoolean(10,P.getVerified());
        psUser.executeUpdate();
        String QueryToProprietairedeTerarin = "INSERT INTO proprietaire_de_terrain (Proprietaire_de_terrain_id ) VALUES (?)";
        int id = getByEmail(P.getEmail()).getId();
        PreparedStatement psProprietairedeTerarin = connection.prepareStatement(QueryToProprietairedeTerarin);
        psProprietairedeTerarin.setInt(1, id);
        psProprietairedeTerarin.executeUpdate();
    }



//    public void delete(String email) throws SQLException{
//        if(!userExist(email)){
//            System.out.println("User does not exist");
//            return;
//        }
//        String query = "DELETE FROM user WHERE email = ?";
//        PreparedStatement ps = connection.prepareStatement(query);
//        ps.setString(1, email);
//        ps.executeUpdate();
//    }



    public void update(User t ) throws SQLException{

        String query = "UPDATE user SET age = ?, name = ?  , address = ? , password = ? , phone = ?  WHERE email = ?";


        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, t.getAge());
        ps.setString(2, t.getName());
        ps.setString(3, t.getAddress());
        ps.setString(4, t.getPassword());
        ps.setInt(5, t.getPhone());
        ps.setString(6, t.getEmail());
        ps.executeUpdate();
    }
    public void UpdateNom_Organisation(Organisateur t ) throws SQLException{

        String query = "UPDATE Organisateur SET Nom_Organisation = ?  WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, t.getNom_Organisation());
        ps.setString(2, t.getEmail());
        ps.executeUpdate();
    }

    public void UpdateNom_Societe(Fournisseur t ) throws SQLException{

        String query = "UPDATE fournisseur SET Nom_Sociéte = ?  WHERE email = ?";


        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, t.getNom_Societe());

        ps.setString(2, t.getEmail());
        ps.executeUpdate();
    }

    public void updatePhoto(String Photo , String email) throws SQLException {
        String query = "UPDATE user SET Image = ?  WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, Photo);
        ps.setString(2,email);
        System.out.println("done image uploaded");
        ps.executeUpdate();
    }

    public void InvertStatus(String email) throws SQLException {
        String query = "UPDATE user SET Status = ?  WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        if (getByEmail(email).getStatus()) {
            ps.setBoolean(1, false);
        } else {
            ps.setBoolean(1, true);
        }
        ps.setString(2,email);
        ps.executeUpdate();
    }



    public List<User> getAll() throws SQLException{
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {

            User user = new User();
            user.setId(rs.getInt("id"));
            user.setAge(rs.getInt("age"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setAddress(rs.getString("address"));
            user.setPassword(rs.getString("password"));
            user.setPhone(rs.getInt("phone"));
            user.setRole(rs.getString("role"));
            user.setDate_de_Creation(rs.getString("DatedeCreation"));
            user.setImage(rs.getString("Image"));
            user.setStatus(rs.getBoolean("Status"));

            users.add(user);
        }
        return users;
    }

    public boolean Login(String e, String P) throws SQLException {
        User U1 = getByEmail(e);
        if (U1 == null || U1.getPassword() == null || U1.getPassword().isEmpty()) {
            return false;
        }
        return U1.getPassword().equals(P);
    }


}
