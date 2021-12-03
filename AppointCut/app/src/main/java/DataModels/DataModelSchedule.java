package DataModels;

public class DataModelSchedule {

    private double schedPrice;
    private String schedName, schedService, schedTime;

    public DataModelSchedule(String schedTime, String schedName, String schedService, double schedPrice) {
        this.schedTime = schedTime;
        this.schedName = schedName;
        this.schedService = schedService;
        this.schedPrice = schedPrice;

    }
    public String getSchedTime() {
        return schedTime;
    }

    public void setSchedTime(String schedTime) {
        this.schedTime = schedTime;
    }

    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    public String getSchedService() {
        return schedService;
    }

    public void setSchedService(String schedService) {
        this.schedService = schedService;
    }

    public double getSchedPrice() {
        return schedPrice;
    }

    public void setSchedPrice(double schedPrice) {
        this.schedPrice = schedPrice;
    }


}
