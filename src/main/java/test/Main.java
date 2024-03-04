package test;

import com.mailjet.client.errors.MailjetException;
import services.*;
import services.GestionUser.UserService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

           UserActivityLogger UAL = new UserActivityLogger();
           UserService us = new UserService();

        System.out.println(   us.getByEmail(UserActivityLogger.extractEmail(UAL.getLastLineOfFile())) );

        }


}
