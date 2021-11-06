package DataModels;

import java.math.BigInteger;

public class DataModelBarberShop {

    private String barberShopName, barberShopDesc, barberAddress, barberSchedule;
    private int barberShopImage;
    private long barberContact;
    private float barberRating;
    private float barberShopDistance;

    public DataModelBarberShop(int barberShopImage, String barberShopName, String barberShopDesc, String barberAddress, long barberContact, String barberSchedule, float barberRating, float barberShopDistance) {
        this.barberShopImage = barberShopImage;
        this.barberShopName = barberShopName;
        this.barberShopDesc = barberShopDesc;
        this.barberAddress = barberAddress;
        this.barberContact = barberContact;
        this.barberSchedule = barberSchedule;
        this.barberRating = barberRating;
        this.barberShopDistance = barberShopDistance;

    }

    public String getBarberShopName() {
        return barberShopName;
    }

    public void setBarberShopName(String barberShopName) {
        this.barberShopName = barberShopName;
    }

    public String getBarberShopDesc() {
        return barberShopDesc;
    }

    public void setBarberShopDesc(String barberShopDesc) {
        this.barberShopDesc = barberShopDesc;
    }

    public int getBarberShopImage() {
        return barberShopImage;
    }

    public void setBarberShopImage(int barberShopImage) {
        this.barberShopImage = barberShopImage;
    }

    public String getBarberAddress() {
        return barberAddress;
    }

    public void setBarberAddress(String barberAddress) {
        this.barberAddress = barberAddress;
    }

    public long getBarberContact() {
        return barberContact;
    }

    public void setBarberContact(long barberContact) {
        this.barberContact = barberContact;
    }

    public String getBarberSchedule() {
        return barberSchedule;
    }

    public void setBarberSchedule(String barberSchedule) {
        this.barberSchedule = barberSchedule;
    }

    public float getBarberRating() {
        return barberRating;
    }

    public void setBarberRating(float barberRating) {
        this.barberRating = barberRating;
    }

    public float getBarberShopDistance() {
        return barberShopDistance;
    }

    public void setBarberShopDistance(float barberShopDistance) {
        this.barberShopDistance = barberShopDistance;
    }
}
