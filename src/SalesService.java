import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalesService {

    public List<SalesData> loadSalesData(String fileName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(fileName)))) {
            if (reader == null) throw new IOException("Unable to find file: " + fileName);
            reader.readLine();

            return reader.lines()
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length >= 2)
                    .map(parts -> {
                        YearMonth yearMonth = YearMonth.parse(parts[0], java.time.format.DateTimeFormatter.ofPattern("MMM-yy"));
                        int salesCount = Integer.parseInt(parts[1].trim());
                        return new SalesData(yearMonth, salesCount);
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            return List.of();
        }
    }

    public Map<YearMonth, Integer> aggregateSales(List<SalesData> salesData) {
        return salesData.stream()
                .collect(Collectors.groupingBy(SalesData::getYearMonth,
                        Collectors.summingInt(SalesData::getSalesCount)));
    }

    public YearMonth findBestMonth(Map<YearMonth, Integer> salesData) {
        return salesData.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public YearMonth findWorstMonth(Map<YearMonth, Integer> salesData) {
        return salesData.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}