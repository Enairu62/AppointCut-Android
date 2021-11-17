package DataModels;

import java.util.Collection;

public class DataModelAppointmentList {

    private int barbershopPic;
    private String barbershopName, barbershopSchedule, barberShopService, status;

    public DataModelAppointmentList(int barbershopPic, String barbershopName, String barberShopService, String barbershopSchedule, String status) {
        this.barbershopPic = barbershopPic;
        this.barbershopName = barbershopName;
        this.barberShopService = barberShopService;
        this.barbershopSchedule = barbershopSchedule;
        this.status = status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
