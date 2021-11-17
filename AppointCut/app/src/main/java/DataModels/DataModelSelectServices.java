package DataModels;


import java.io.Serializable;

public class DataModelSelectServices implements Serializable {

    private int servicePic;
    private String serviceName;
    private float servicePrice;

    private boolean isChecked = false;

    public DataModelSelectServices(int servicePic, String serviceName, float servicePrice) {
        this.servicePic = servicePic;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;

    }
    public int getServicePic() {
        return servicePic;
    }

    public void setServicePic(int servicePic) {
        this.servicePic = servicePic;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public float getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(float servicePrice) {
        this.servicePrice = servicePrice;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
