package DataModels;

import java.io.Serializable;

public class Services implements Serializable {
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
