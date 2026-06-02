package assignment;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Mailer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter political affiliation (Democrat, Independent, Republican): ");
        String affiliation = scanner.nextLine();
        scanner.close();

        String outputFile = affiliation + "_records.csv";

        try (
            FileReader fileReader = new FileReader("voters.csv");
            BufferedReader buffReader = new BufferedReader(fileReader);
            FileWriter fw = new FileWriter(outputFile);
            PrintWriter writer = new PrintWriter(fw)
        ) {
            buffReader.lines()
                .map(line -> line.split(",", 4))
                .filter(parts -> parts.length == 4 && parts[2].equals(affiliation))
                .sorted((a, b) -> {
                    String lastA = a[1].trim().contains(" ")
                        ? a[1].trim().split(" ")[1] : a[1].trim();
                    String lastB = b[1].trim().contains(" ")
                        ? b[1].trim().split(" ")[1] : b[1].trim();
                    return lastA.compareTo(lastB);
                })
                .forEach(parts -> writer.println(parts[1] + ", " + parts[3]));

            writer.flush();
            System.out.println("Done. Output written to " + outputFile);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}