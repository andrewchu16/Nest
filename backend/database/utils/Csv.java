package database.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Csv {
    public static final String INVALID_CHARACTERS = ",\t";

    public static boolean validateData(String data) {
        for (int i = 0, length = data.length(); i < length; i++) {
            char chr = data.charAt(i);

            if (INVALID_CHARACTERS.contains("" + chr)) {
                return false;
            }
        }

        return true;
    }

    public static ArrayList<String[]> read(String fileName) {
        BufferedReader input = null;

        try {
            input = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException ex) {
            return new ArrayList<String[]>();
        }

        ArrayList<String[]> lines = new ArrayList<String[]>();

        try {
            String line = input.readLine();
            while (line != null) {
                lines.add(line.split(","));
                line = input.readLine();
            }
            input.close();
        } catch (IOException ex) {
            System.out.println("Could not read CSV file " + fileName + ".");
        }

        return lines;
    }

    public static void write(String fileName, ArrayList<String> data) {
        PrintWriter output;
        try {
            output = new PrintWriter(new File(fileName));
        } catch (FileNotFoundException ex) {
            System.out.println("Could not write CSV file " + fileName + ".");
            return;
        }

        for (String line : data) {
            output.println(line);
        }

        output.close();
    }
}
