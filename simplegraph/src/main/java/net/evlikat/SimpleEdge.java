package net.evlikat;

import java.util.Objects;

public class SimpleEdge<V> implements Edge<V> {

    private final V vertex1;
    private final V vertex2;

    private SimpleEdge(V vertex1, V vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    public static <V> SimpleEdge<V> between(V vertex1, V vertex2) {
        return new SimpleEdge<>(vertex1, vertex2);
    }

    @Override
    public V getVertex1() {
        return vertex1;
    }

    @Override
    public V getVertex2() {
        return vertex2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleEdge<?> that = (SimpleEdge<?>) o;
        return Objects.equals(vertex1, that.vertex1) && Objects.equals(vertex2, that.vertex2)
                || Objects.equals(vertex1, that.vertex2) && Objects.equals(vertex2, that.vertex1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex1) ^ Objects.hash(vertex2);
    }
}
