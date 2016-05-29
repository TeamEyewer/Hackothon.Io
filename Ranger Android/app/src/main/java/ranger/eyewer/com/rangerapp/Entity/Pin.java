package ranger.eyewer.com.rangerapp.Entity;

import ranger.eyewer.com.rangerapp.Entity.Danger;


public class Pin {

    private String ID = "";
    private String Latitude = "";
    private String Longitude = "";
    private String Distance = "";
    private String Group = "";
    private Danger danger = new Danger();
    private int PinCount = 0;

    public Pin() {

    }

    public Pin(String ID, String latitude, String longitude, String distance, String group, Danger danger, int pinCount) {
        this.ID = ID;
        Latitude = latitude;
        Longitude = longitude;
        Distance = distance;
        Group = group;
        this.danger = danger;
        PinCount = pinCount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public Danger getDanger() {
        return danger;
    }

    public void setDanger(Danger danger) {
        this.danger = danger;
    }

    public int getPinCount() {
        return PinCount;
    }

    public void setPinCount(int pinCount) {
        this.PinCount = pinCount;
    }
}
