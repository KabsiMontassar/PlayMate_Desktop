package test;

import com.mailjet.client.errors.MailjetException;
import services.JavaMailJett;
import services.ValidateEmail;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws MailjetException {
        String sender = "playmatepidev@gmail.com";
        String Receipient = "aziztaraji1@gmail.com";
        String contenue = "sa77a el 0  git ya zekri";

        JavaMailJett.send(Receipient,sender,contenue);



    }
}
