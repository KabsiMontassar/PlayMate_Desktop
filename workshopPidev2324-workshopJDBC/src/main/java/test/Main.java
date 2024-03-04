package test;

import com.mailjet.client.errors.MailjetException;
import controllers.AvisController;
import controllers.JavaMailJett;
import controllers.smsAPi;
import entity.Terrain;
import controllers.TerrainController;
import services.AvisService;
import services.TerrainService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//*******************************************************************************************
public class Main{
    public static void main(String[] args) throws SQLException, MailjetException {
        AvisService as = new AvisService();
        System.out.println(as.getPhoneNumberForAvis(49));
        JavaMailJett.send("bendjemia.malek30@gmail.com","hgj");
       // smsAPi.sendSms(0,"tataaatta");



    }}