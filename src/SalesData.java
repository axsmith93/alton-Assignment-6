import java.time.YearMonth;

public class SalesData {
    private YearMonth yearMonth;
    private int salesCount;

    public SalesData(YearMonth yearMonth, int salesCount) {
        this.yearMonth = yearMonth;
        this.salesCount = salesCount;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public int getSalesCount() {
        return salesCount;
    }
}