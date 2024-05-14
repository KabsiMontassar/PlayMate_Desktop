package services.GestionUser;

import com.mailjet.client.errors.MailjetException;
import models.*;
import org.mindrot.jbcrypt.BCrypt;
import services.JavaMailJett;
import services.UserActivityLogger;
import utils.MyDatabase;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
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
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            user.setRole(rs.getString("role"));
            user.setImage(rs.getString("Image"));
            user.setStatus(rs.getBoolean("Status"));
            user.setDate_de_Creation(rs.getString("DatedeCreation"));
            user.setVerificationCode(rs.getString("VerificationCode"));
            user.setVerified(rs.getBoolean("is_Verified"));



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
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            user.setRole(rs.getString("role"));
            user.setImage(rs.getString("Image"));
            user.setStatus(rs.getBoolean("Status"));
            user.setDate_de_Creation(rs.getString("DatedeCreation"));
            user.setVerificationCode(rs.getString("VerificationCode"));
            user.setVerified(rs.getBoolean("is_Verified"));



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



    public void addUser(User u) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());

        String queryToUser = "INSERT INTO user (email, password, name, Age, Phone, role, Status, DatedeCreation, VerificationCode, is_Verified, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement psUser = connection.prepareStatement(queryToUser);
        psUser.setString(1, u.getEmail());
        psUser.setString(2, hashedPassword); // Store the hashed password
        psUser.setString(3, u.getName());
        psUser.setInt(4, u.getAge());
        psUser.setInt(5, u.getPhone());
        psUser.setString(6, u.getRole().toString());
        psUser.setBoolean(7, u.getStatus());
        psUser.setString(8, u.getDate_de_Creation());
        psUser.setString(9, u.getVerificationCode());
        psUser.setBoolean(10, u.getVerified());
        psUser.setString(11, u.getAddress());
        psUser.executeUpdate();
    }







    public void update(User t ) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        String query = "UPDATE user SET age = ?, name = ?  , address = ? , password = ? , phone = ?  WHERE email = ?";


        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, t.getAge());
        ps.setString(2, t.getName());
        ps.setString(3, t.getAddress());
        ps.setString(4,  t.getPassword());
        ps.setInt(5, t.getPhone());
        ps.setString(6, t.getEmail());
        UAL.logAction(t.getEmail() ,  "effectué de la mise à jour à son Compte");

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

    public void InvertStatus(String email) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException, InterruptedException, MailjetException {

        String query = "UPDATE user SET Status = ?  WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        if (getByEmail(email).getStatus()) {
            ps.setBoolean(1, false);

            UAL.logAction(email ,  "Desactiver son compte");
        } else {
            JavaMailJett.send2(email);
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
            user.setPhone(rs.getString("phone"));
            user.setRole(rs.getString("role"));
            user.setDate_de_Creation(rs.getString("DatedeCreation"));
            user.setImage(rs.getString("Image"));
            user.setStatus(rs.getBoolean("Status"));

            users.add(user);
        }
        return users;
    }

    public boolean Login(String e, String P) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        User user = getByEmail(e);
        if (user == null || user.getPassword() == null || user.getPassword().isEmpty()) {
            return false; // User not found or password not set
        }
        // Verify the entered password against the hashed password using BCrypt
        System.out.println(BCrypt.checkpw(P, user.getPassword()));
        System.out.println(P + user.getPassword());
        return BCrypt.checkpw(P, user.getPassword());
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

