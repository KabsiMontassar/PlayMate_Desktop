package test;

import com.mailjet.client.errors.MailjetException;
import models.Historique;
import models.Terrain;
import models.User;
import services.*;
import services.GestionReservation.HistoriqueService;
import services.GestionReservation.PaiementService;
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

        System.out.println(us.getByEmail("maroua.douiri@esprit.tn"));

    }


}
