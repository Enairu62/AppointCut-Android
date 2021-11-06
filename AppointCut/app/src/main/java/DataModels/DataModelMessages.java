package DataModels;

public class DataModelMessages {

    private int profilePic;
    private String names, message, time;

    public DataModelMessages(int profilePic, String names, String message, String time) {
        this.profilePic = profilePic;
        this.names = names;
        this.message = message;
        this.time = time;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
