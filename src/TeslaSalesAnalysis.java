import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class TeslaSalesAnalysis {

    public static void main(String[] args) {
        SalesService salesService = new SalesService();


        List<SalesData> model3SalesData = salesService.loadSalesData("model3.csv");
        List<SalesData> modelSSalesData = salesService.loadSalesData("modelS.csv");
        List<SalesData> modelXSalesData = salesService.loadSalesData("modelX.csv");


        Map<YearMonth, Integer> model3AggregatedSales = salesService.aggregateSales(model3SalesData);
        Map<YearMonth, Integer> modelSAggregatedSales = salesService.aggregateSales(modelSSalesData);
        Map<YearMonth, Integer> modelXAggregatedSales = salesService.aggregateSales(modelXSalesData);


        printSalesReport("Model 3", model3AggregatedSales);
        printBestAndWorstMonths(salesService, "Model 3", model3AggregatedSales);

        printSalesReport("Model S", modelSAggregatedSales);
        printBestAndWorstMonths(salesService, "Model S", modelSAggregatedSales);

        printSalesReport("Model X", modelXAggregatedSales);
        printBestAndWorstMonths(salesService, "Model X", modelXAggregatedSales);
    }

    private static void printSalesReport(String modelName, Map<YearMonth, Integer> salesData) {
        System.out.println(modelName + " Yearly Sales Report");
        System.out.println("---------------------------");
        salesData.forEach((yearMonth, salesCount) -> {
            System.out.println(yearMonth + " -> " + salesCount);
        });
        System.out.println();
    }

    private static void printBestAndWorstMonths(SalesService salesService, String modelName, Map<YearMonth, Integer> salesData) {
        YearMonth bestMonth = salesService.findBestMonth(salesData);
        YearMonth worstMonth = salesService.findWorstMonth(salesData);

        System.out.println("The best month for " + modelName + " was: " + (bestMonth != null ? bestMonth : "N/A"));
        System.out.println("The worst month for " + modelName + " was: " + (worstMonth != null ? worstMonth : "N/A"));
        System.out.println();
    }
}