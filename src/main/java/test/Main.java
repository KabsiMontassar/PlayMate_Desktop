package test;

import com.mailjet.client.errors.MailjetException;
import services.JavaMailJett;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws MailjetException {

      JavaMailJett.send("ahmedadem7788@gmail.com","playmatepidev@gmail.com","ahmedlm3alm");
    }
}
