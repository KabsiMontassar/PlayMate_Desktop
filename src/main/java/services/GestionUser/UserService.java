package services.GestionUser;

import models.*;
import services.Encryption;
import services.UserActivityLogger;
import utils.MyDatabase;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

public class UserService implements IService<User> {

    private final Connection connection;

    public UserService() {
        connection = MyDatabase.getInstance().getConnection();
    }

     UserActivityLogger UAL = new UserActivityLogger();




    public User getByEmail(String e) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
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

    public User getByid(int id) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        User user = new User(); // Initialize user as null
        String query = "SELECT * FROM user WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
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




    public void addJoueur(Joueur J ) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String QueryToUser = "INSERT INTO user (email , password , name ,Age ,Phone, role,Status , DatedeCreation ,VerificationCode,isVerified,address ) VALUES (?,?,?,?,?,?,?,?, ?, ?, ?)";
        PreparedStatement psUser = connection.prepareStatement(QueryToUser);
        psUser.setString(1, J.getEmail());
        psUser.setString(2,  Encryption.encrypt(J.getPassword()));
        psUser.setString(3, J.getName());

        psUser.setInt(4, J.getAge());
        psUser.setInt(5, J.getPhone());
        psUser.setString(6, J.getRole().toString());
        psUser.setBoolean(7,J.getStatus());
        psUser.setString(8,J.getDate_de_Creation());
        psUser.setString(9,J.getVerificationCode());
        psUser.setBoolean(10,J.getVerified());
        psUser.setString(11,"");


        UAL.logAction(J.getEmail() ,  "Creer un Compte autant que Joueur");
        psUser.executeUpdate();

        String QueryToJoueur = "INSERT INTO Membre (JoueurId ) VALUES (?)";
        int id = getByEmail(J.getEmail()).getId();
        PreparedStatement psJoueur = connection.prepareStatement(QueryToJoueur);
        psJoueur.setInt(1, id);
        psJoueur.executeUpdate();
    }
    public void addFournisseur(Fournisseur F) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String QueryToUser = "INSERT INTO user (email , password , name ,Age ,Phone, role,Status , DatedeCreation ,VerificationCode,isVerified,address ) VALUES (?,?,?,?,?,?,?,?, ?, ?, ?)";
        PreparedStatement psUser = connection.prepareStatement(QueryToUser);
        psUser.setString(1, F.getEmail());
        psUser.setString(2,  Encryption.encrypt(F.getPassword()));
        psUser.setString(3, F.getName());
        psUser.setInt(4, F.getAge());
        psUser.setInt(5, F.getPhone());
        psUser.setString(6, F.getRole().toString());
        psUser.setBoolean(7,F.getStatus());
        psUser.setString(8,F.getDate_de_Creation());
        psUser.setString(9,F.getVerificationCode());
        psUser.setBoolean(10,F.getVerified());
        psUser.setString(11,"");
        psUser.executeUpdate();
        String QueryToFournisseur = "INSERT INTO fournisseur (Fournisseur_id , Nom_Sociéte ) VALUES (?,?)";
        int id = getByEmail(F.getEmail()).getId();
        PreparedStatement psFournisseur = connection.prepareStatement(QueryToFournisseur);
        psFournisseur.setInt(1, id);
        psFournisseur.setString(2, F.getNom_Societe());
        UAL.logAction(F.getEmail() ,  "Creer un Compte autant que Fournisseur");
        psFournisseur.executeUpdate();
    }
    public void addOrganisateur(Organisateur O) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String QueryToUser = "INSERT INTO user (email , password , name ,Age ,Phone, role,Status , DatedeCreation ,VerificationCode,isVerified ,address) VALUES (?,?,?,?,?,?,?,?, ?, ?, ?)";
        PreparedStatement psUser = connection.prepareStatement(QueryToUser);
        psUser.setString(1, O.getEmail());
        psUser.setString(2,  Encryption.encrypt(O.getPassword()));
        psUser.setString(3, O.getName());
        psUser.setInt(4, O.getAge());
        psUser.setInt(5, O.getPhone());
        psUser.setString(6, O.getRole().toString());
        psUser.setBoolean(7,O.getStatus());
        psUser.setString(8,O.getDate_de_Creation());
        psUser.setString(9,O.getVerificationCode());
        psUser.setBoolean(10,O.getVerified());
        psUser.setString(11,"");
        psUser.executeUpdate();
        UAL.logAction(O.getEmail() ,  "Creer un Compte autant que Organisateur");

        String QueryToOrganisateur = "INSERT INTO organisateur (Organisateur_id , Nom_Organisation ) VALUES (?,?)";
        int id = getByEmail(O.getEmail()).getId();
        PreparedStatement psOrganisateur = connection.prepareStatement(QueryToOrganisateur);
        psOrganisateur.setInt(1, id);
        psOrganisateur.setString(2, O.getNom_Organisation());

        psOrganisateur.executeUpdate();
    }
    public void addProprietairedeTerarin(Proprietaire_de_terrain P) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String QueryToUser = "INSERT INTO user (email , password , name ,Age ,Phone, role,Status , DatedeCreation ,VerificationCode,isVerified,address ) VALUES (?,?,?,?,?,?,?,?, ?, ?, ?)";
        PreparedStatement psUser = connection.prepareStatement(QueryToUser);
        psUser.setString(1, P.getEmail());
        psUser.setString(2,  Encryption.encrypt(P.getPassword()));
        psUser.setString(3, P.getName());
        psUser.setInt(4, P.getAge());
        psUser.setInt(5, P.getPhone());
        psUser.setString(6, P.getRole().toString());
        psUser.setBoolean(7,P.getStatus());
        psUser.setString(8,P.getDate_de_Creation());
        psUser.setString(9,P.getVerificationCode());
        psUser.setBoolean(10,P.getVerified());
        psUser.setString(11,"");
        UAL.logAction(P.getEmail() ,  "Creer un Compte autant que Propriétaire de terrain");

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



    public void update(User t ) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        String query = "UPDATE user SET age = ?, name = ?  , address = ? , password = ? , phone = ?  WHERE email = ?";


        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, t.getAge());
        ps.setString(2, t.getName());
        ps.setString(3, t.getAddress());
        ps.setString(4,  Encryption.encrypt(t.getPassword()));
        ps.setInt(5, t.getPhone());
        ps.setString(6, t.getEmail());
        UAL.logAction(t.getEmail() ,  "effectué de la mise à jour à son Compte");

        ps.executeUpdate();
    }

    public void UpdateNom_Organisation(int id , String nom ) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UAL.logAction(getByid(id).getEmail() ,  "effectué de la mise à jour à son Compte");
        String query = "UPDATE Organisateur SET Nom_Organisation = ?  WHERE Organisateur_id  = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nom);
        ps.setInt(2, id);
        ps.executeUpdate();
        System.out.println( ps);
    }

    public Organisateur getOrganisateurbyid(int id) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        Organisateur org = new Organisateur(); // Initialize user as null
        String query = "SELECT * FROM organisateur WHERE Organisateur_id  = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            org.setId(rs.getInt("Organisateur_id"));
            org.setNom_Organisation(rs.getString("Nom_Organisation"));

        }
        return org;
    }
    public Fournisseur getFournisseurbyid(int id)throws SQLException{
        Fournisseur four = new Fournisseur(); // Initialize user as null
        String query = "SELECT * FROM fournisseur WHERE Fournisseur_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            four.setId(rs.getInt("Fournisseur_id"));
            four.setNom_Societe(rs.getString("Nom_Sociéte"));

        }
        return four;
    }

    public void UpdateNom_Societe(int id , String nom ) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UAL.logAction(getByid(id).getEmail() ,  "effectué de la mise à jour à son Compte");
        String query = "UPDATE fournisseur SET Nom_Sociéte = ?  WHERE Fournisseur_id  = ?";

        System.out.println(nom);
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nom);
        ps.setInt(2,id);

        System.out.println( id + nom);
        ps.executeUpdate();
    }

    public void updatePhoto(String Photo , String email) throws SQLException {
        UAL.logAction(email ,  "effectué de la mise à jour à son Compte");
        String query = "UPDATE user SET Image = ?  WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, Photo);
        ps.setString(2,email);
        System.out.println("done image uploaded");
        ps.executeUpdate();
    }

    public void InvertStatus(String email) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        String query = "UPDATE user SET Status = ?  WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        if (getByEmail(email).getStatus()) {
            ps.setBoolean(1, false);
            UAL.logAction(email ,  "Desactiver son compte");
        } else {
            ps.setBoolean(1, true);
            UAL.logAction(email ,  "activer son compte");
        }
        ps.setString(2,email);
        ps.executeUpdate();
    }



    public List<User> getAll() throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
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

    public boolean Login(String e, String P) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        User U1 = getByEmail(e);
        if (U1 == null || U1.getPassword() == null || U1.getPassword().isEmpty()) {
            return false;
        }

        return U1.getPassword().equals(P);
    }

    public int CountActive() throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(*) AS count FROM user WHERE Status = 1"; // Query pour compter le nombre d'utilisateurs actifs
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            count = rs.getInt("count");
        }
        return count;
    }
    public int CountInactive() throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(*) AS count FROM user WHERE Status = 0"; // Query pour compter le nombre d'utilisateurs actifs
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            count = rs.getInt("count");
        }
        return count;
    }
    public Map<String, Integer> getUsersCreatedPerMonth() throws SQLException {
        Map<String, Integer> userData = initializeMonthMap();

        String query = "SELECT DATE_FORMAT(DatedeCreation, '%Y-%m') AS Month, COUNT(*) AS UserCount " +
                "FROM user " +
                "GROUP BY DATE_FORMAT(Datedecreation, '%Y-%m') " +
                "ORDER BY Month";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                String month = rs.getString("Month");
                int userCount = rs.getInt("UserCount");
                userData.put(month, userCount);
            }
        }

        return new TreeMap<>(userData);
    }

    private Map<String, Integer> initializeMonthMap() {
        Map<String, Integer> monthMap = new TreeMap<>(Comparator.comparingInt(o -> Integer.parseInt(o.split("-")[1])));
        // Initialize the map with all months set to 0 initially
        for (int month = 1; month <= 12; month++) {
            String monthString = String.format("%02d", month);
            monthMap.put("2024-" + monthString, 0);
        }
        return monthMap;
    }
}

