package test;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Email;
import models.Historique;
import models.Terrain;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import services.*;
import services.GestionReservation.HistoriqueService;
import services.GestionReservation.PaiementService;
import services.GestionTerrain.TerrainService;
import services.GestionUser.UserService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserService us = new UserService();
       System.out.println(us.Login("ahmedd@gmail.com" , "ahmed123" ));
        System.out.println(us.Login("ahmedd@gmail.com" , "$2a$10$NVFr551pnPJjJO1wmD7uLeTXmgCP1fwVLo7G9vTobSbG4mobu0EMK" ));
        System.out.println(BCrypt.checkpw("ahmed123", "$2a$10$NVFr551pnPJjJO1wmD7uLeTXmgCP1fwVLo7G9vTobSbG4mobu0EMK"));
        TerrainService t = new TerrainService();
        System.out.println(t.getAllTerrains());

    }


}
