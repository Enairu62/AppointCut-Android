package DataModels;

public class DataModelIncome {

    private double incomeAmount, incomeServicePrice, incomeTotalPrice;
    private String incomeService, incomeDate;

    public DataModelIncome(String incomeDate, String incomeService, double incomeServicePrice, double incomeTotalPrice, double incomeAmount) {
        this.incomeDate = incomeDate;
        this.incomeService = incomeService;
        this.incomeServicePrice = incomeServicePrice;
        this.incomeTotalPrice = incomeTotalPrice;
        this.incomeAmount = incomeAmount;

    }

    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }

    public String getIncomeService() {
        return incomeService;
    }

    public void setIncomeService(String incomeService) {
        this.incomeService = incomeService;
    }

    public double getIncomeServicePrice() {
        return incomeServicePrice;
    }

    public void setIncomeServicePrice(double incomeServicePrice) {
        this.incomeServicePrice = incomeServicePrice;
    }

    public double getIncomeTotalPrice() {
        return incomeTotalPrice;
    }

    public void setIncomeTotalPrice(double incomeTotalPrice) {
        this.incomeTotalPrice = incomeTotalPrice;
    }

    public double getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(double incomeAmount) {
        this.incomeAmount = incomeAmount;
    }


}
