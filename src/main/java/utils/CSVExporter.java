package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class CSVExporter {

    /**
     * Writes MST results to a CSV file.
     *
     * @param filePath path to output file (e.g., "src/main/resources/output/results.csv")
     * @param rows     list of string arrays; each array is one row
     */
    public static void writeCSV(String filePath, List<String[]> rows) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String[] row : rows) {
                writer.write(String.join(",", row));
                writer.write("\n");
            }
            System.out.println("CSV exported to " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to write CSV: " + e.getMessage());
        }
    }
}
