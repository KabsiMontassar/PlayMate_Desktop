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
    private Deque<String> actions = new ArrayDeque<>();

    public void logAction(String Email, String action) {
        System.out.println("written");
        String s =  "Utilisateur avec courriel " + Email +" "+action+".";
        String formattedAction = "[" + getCurrentTimeStamp() + "] " + s;
        actions.addLast(formattedAction);
        trimActions();
        writeToFile();
    }

    public String getLastLineOfFile() {
        String lastLine = null;
        try {
            File file = new File("D://Github//pidev_spartans_3a29//Pidev//src//main//resourcesuser_activity_log.txt");
            if (file.exists()) {
                try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
                    long length = raf.length();
                    if (length != 0) {
                        long position = length - 1;
                        raf.seek(position);
                        int c = -1;
                        StringBuilder sb = new StringBuilder();
                        // Read each character backwards until a newline character is found
                        while (position >= 0) {
                            raf.seek(position);
                            c = raf.read();
                            if (c == '\n' || c == '\r') {
                                break;
                            }
                            sb.insert(0, (char) c);
                            position--;
                        }
                        lastLine = sb.toString();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastLine;
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
    public static String extractEmail(String logEntry) {
        // Find the index of the substring "courriel"
        int startIndex = logEntry.indexOf("courriel");
        if (startIndex == -1) {
            // If "courriel" is not found, return null or throw an exception
            return null;
        }

        // The email starts after the substring "courriel" and a space
        startIndex += "courriel".length() + 1;

        // Find the index of the next space after the email
        int endIndex = logEntry.indexOf(" ", startIndex);
        if (endIndex == -1) {
            // If no space is found, the email extends to the end of the string
            endIndex = logEntry.length();
        }

        // Extract the email substring
        return logEntry.substring(startIndex, endIndex);
    }
}
