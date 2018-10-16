package fr.wildcodeschool.gooddeals;

public class Deal {


    //Attributs
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private int icon;
    private int image;
    private String type;

    //constructeur
    public Deal(String name, String description, double latitude, double longitude, int icon, int image, String type) {
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.icon = icon;
        this.image = image;
        this.type = type;
    }

    public Deal() {

    }
    //getters et setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
