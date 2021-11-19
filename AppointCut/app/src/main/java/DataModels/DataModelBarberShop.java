package DataModels;

import java.math.BigInteger;

public class DataModelBarberShop {

    private String barberShopName, barberShopDesc, barberAddress, barberSchedule;
    private int barberShopImage;
    private long barberContact;
    private float barberRating;
    private float barberShopDistance;

    public DataModelBarberShop(int barberShopImage, String barberShopName, String barberAddress,
                               long barberContact, String barberSchedule, float barberRating) {
        this.barberShopImage = barberShopImage;
        this.barberShopName = barberShopName;
        this.barberAddress = barberAddress;
        this.barberContact = barberContact;
        this.barberSchedule = barberSchedule;
        this.barberRating = barberRating;

    }

    public String getBarberShopName() {
        return barberShopName;
    }

    public void setBarberShopName(String barberShopName) {
        this.barberShopName = barberShopName;
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
}
