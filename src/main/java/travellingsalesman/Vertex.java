package travellingsalesman;

public class Vertex {
    private String id;
    private double latitude;
    private double longitude;

    private double x;
    private double y;


    public Vertex(String id, double longitude, double latitude, double x, double y) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.x = x;
        this.y = y;

    }

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return id;
    }
}
