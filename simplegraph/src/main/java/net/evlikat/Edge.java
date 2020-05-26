package net.evlikat;

public class Edge {

    private final float weight;

    public static Edge of(float weight) {
        return new Edge(weight);
    }

    public Edge(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }
}
