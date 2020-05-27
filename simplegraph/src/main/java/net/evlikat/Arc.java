package net.evlikat;

public class Arc {

    private final float weight;

    public static Arc of(float weight) {
        return new Arc(weight);
    }

    public Arc(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }
}
