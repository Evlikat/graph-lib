package net.evlikat;

import java.util.Objects;

public class SimpleArc<V> implements Arc<V> {

    private final V fromVertex;
    private final V toVertex;

    private SimpleArc(V fromVertex, V toVertex) {
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
    }

    public static <V> SimpleArc<V> between(V fromVertex, V toVertex) {
        return new SimpleArc<>(fromVertex, toVertex);
    }

    @Override
    public V getFromVertex() {
        return fromVertex;
    }

    @Override
    public V getToVertex() {
        return toVertex;
    }

    @Override
    public String toString() {
        return "'" + fromVertex + "' -> '" + toVertex + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleArc<?> simpleArc = (SimpleArc<?>) o;
        return Objects.equals(fromVertex, simpleArc.fromVertex) &&
                Objects.equals(toVertex, simpleArc.toVertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromVertex, toVertex);
    }
}
