package travellingsalesman;

class Edge implements Comparable<Edge> {
    private Vertex u;
    private Vertex v;
    private double weight;

    public Edge(Vertex u, Vertex v, double weight) {
        this.u = u;
        this.v = v;
        this.weight = weight;
    }

    public Vertex getU() {
        return u;
    }

    public Vertex getV() {
        return v;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Double.compare(weight, other.weight);
    }

    @Override
    public String toString() {
        return u.getId() + " - " + v.getId() + " : " + weight;
    }
}
