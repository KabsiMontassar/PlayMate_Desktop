package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {

    private Connection connection;
    private static MyDatabase instance;

    private MyDatabase() {
        try {
            String PASSWORD = "";
            String USER = "root";
<<<<<<< HEAD:workshopPidev2324-workshopJDBC/src/main/java/utils/MyDatabase.java
            String URL = "jdbc:mysql://localhost:3306/evenement";
=======
            String URL = "jdbc:mysql://localhost:3306/spartans";
>>>>>>> master:src/main/java/utils/MyDatabase.java
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
//
    public static MyDatabase getInstance() {
        if(instance == null)
            instance = new MyDatabase();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
