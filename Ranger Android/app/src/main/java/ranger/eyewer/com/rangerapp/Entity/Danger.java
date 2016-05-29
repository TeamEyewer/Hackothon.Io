package ranger.eyewer.com.rangerapp.Entity;


public class Danger {

    private String ID = "";
    private String Name = "";

    public Danger() {

    }

    public Danger(String ID, String name, String duration) {
        this.ID = ID;
        Name = name;
        Duration = duration;
    }

    private String Duration = "";

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }
}
