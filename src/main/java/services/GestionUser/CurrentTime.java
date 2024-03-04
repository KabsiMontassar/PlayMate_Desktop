package services.GestionUser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CurrentTime {


    public static String GetCurrentTime(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }


}
