package services;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class UserActivityLogger {
    private static final int MAX_ACTIONS = 20;
    private  Deque<String> actions = new ArrayDeque<>();



    public void logAction(String Email, String action) {
        System.out.println("written");
        String s =  "Utilisateur avec courriel " + Email +" "+action+".";
        String formattedAction = "[" + getCurrentTimeStamp() + "] " + s;
        actions.addLast(formattedAction);
        trimActions();
        writeToFile();
    }

    private String getCurrentTimeStamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public void writeToFile() {
        try {
            // Create the file if it doesn't exist
            File file = new File("D://Github//pidev_spartans_3a29//Pidev//src//main//resourcesuser_activity_log.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            // Read existing lines from the file
            List<String> existingLines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    existingLines.add(line);
                }
            }

            // Append new actions to the end of the list
            existingLines.addAll(actions);

            // Trim the list if it exceeds the maximum allowed lines
            while (existingLines.size() > MAX_ACTIONS) {
                existingLines.remove(0); // Remove the oldest line
            }

            // Write the updated lines to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String line : existingLines) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void trimActions() {
        while (actions.size() > MAX_ACTIONS) {
            actions.removeFirst();
        }
    }


}
