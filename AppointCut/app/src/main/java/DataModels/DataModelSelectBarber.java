package DataModels;

public class DataModelSelectBarber {

    private int profilePic;
    private String names, specialty;

    public DataModelSelectBarber(int profilePic, String names, String specialty) {
        this.profilePic = profilePic;
        this.names = names;
        this.specialty = specialty;

    }
    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    public String getNamess() {
        return names;
    }

    public void setNamess(String names) {
        this.names = names;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }


}
