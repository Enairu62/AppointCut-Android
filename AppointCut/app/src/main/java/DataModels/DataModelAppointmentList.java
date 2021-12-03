package DataModels;

public class DataModelAppointmentList {

    private int barbershopPic;
    private String barbershopName, barbershopSchedule, barberShopService;

    public DataModelAppointmentList(int barbershopPic, String barbershopName, String barberShopService, String barbershopSchedule) {
        this.barbershopPic = barbershopPic;
        this.barbershopName = barbershopName;
        this.barberShopService = barberShopService;
        this.barbershopSchedule = barbershopSchedule;

    }
    public int getBarbershopPic() {
        return barbershopPic;
    }

    public void setBarbershopPic(int barbershopPic) {
        this.barbershopPic = barbershopPic;
    }

    public String getBarbershopName() {
        return barbershopName;
    }

    public void setBarbershopName(String barbershopName) {
        this.barbershopName = barbershopName;
    }

    public String getBarberShopService() {
        return barberShopService;
    }

    public void setBarberShopService(String barberShopService) {
        this.barberShopService = barberShopService;
    }

    public String getBarbershopSchedule() {
        return barbershopSchedule;
    }

    public void setBarbershopSchedule(String barbershopSchedule) {
        this.barbershopSchedule = barbershopSchedule;
    }


}
