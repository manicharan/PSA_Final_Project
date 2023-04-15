package travellingsalesman;

public class Vertex {
    private String id;
    private double latitude;
    private double longitude;

    public Vertex(String id, double longitude, double latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
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

    @Override
    public String toString() {
        return id;
    }
}
