import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TeslaSalesAnalysis {

    public static void main(String[] args) {
        
        Map<Integer, Integer> model3SalesData = loadSalesData("model3.csv");
        Map<Integer, Integer> modelSSalesData = loadSalesData("modelS.csv");
        Map<Integer, Integer> modelXSalesData = loadSalesData("modelX.csv");


        generateSalesReport("Model 3", model3SalesData, 2017);
        displayBestAndWorstMonths("model3.csv", "Model 3");

        generateSalesReport("Model S", modelSSalesData, 2016);
        displayBestAndWorstMonths("modelS.csv", "Model S");

        generateSalesReport("Model X", modelXSalesData, 2016);
        displayBestAndWorstMonths("modelX.csv", "Model X");
    }

    private static Map<Integer, Integer> loadSalesData(String fileName) {
        Map<Integer, Integer> yearlySalesMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                TeslaSalesAnalysis.class.getClassLoader().getResourceAsStream(fileName)))) {
            if (reader == null) throw new IOException("Unable to find file: " + fileName);
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] salesRecord = line.split(",");
                if (salesRecord.length < 2) continue;

                int salesCount = Integer.parseInt(salesRecord[1].trim());
                int year = 2000 + Integer.parseInt(salesRecord[0].split("-")[1]); // Extract year directly
                yearlySalesMap.put(year, yearlySalesMap.getOrDefault(year, 0) + salesCount);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }

        return yearlySalesMap;
    }

    private static void generateSalesReport(String modelName, Map<Integer, Integer> salesData, int startYear) {
        System.out.println(modelName + " Yearly Sales Report");
        System.out.println("---------------------------");
        for (int year = startYear; year <= 2019; year++) {
            System.out.println(year + " -> " + salesData.getOrDefault(year, 0));
        }
        System.out.println();
    }

    private static void displayBestAndWorstMonths(String fileName, String modelName) {
        try {
            List<String[]> salesRecords = Files.lines(Paths.get("src/" + fileName))
                    .skip(1)
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());

            String bestSalesMonth = salesRecords.stream()
                    .max((record1, record2) -> Integer.compare(Integer.parseInt(record1[1]), Integer.parseInt(record2[1])))
                    .map(record -> record[0])
                    .orElse("N/A");

            String worstSalesMonth = salesRecords.stream()
                    .min((record1, record2) -> Integer.compare(Integer.parseInt(record1[1]), Integer.parseInt(record2[1])))
                    .map(record -> record[0])
                    .orElse("N/A");

            System.out.println(modelName + " Best Month: " + bestSalesMonth);
            System.out.println(modelName + " Worst Month: " + worstSalesMonth);
            System.out.println();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}