package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ActivityLogger {
    private static final String FILE_NAME = "activity_log.txt";

    public static void log(String message) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            pw.println(message);
        } catch (IOException e) {
            System.out.println("Could not write to activity log: " + e.getMessage());
        }
    }
}
