package ranger.eyewer.com.rangerapp.Entity;


public class PanicData {

    private String UserID = "";
    private String Lat = "";
    private String Lon = "";

    public PanicData() {

    }

    public PanicData(String userID, String lat, String lon) {
        UserID = userID;
        Lat = lat;
        Lon = lon;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLon() {
        return Lon;
    }

    public void setLon(String lon) {
        Lon = lon;
    }
}
