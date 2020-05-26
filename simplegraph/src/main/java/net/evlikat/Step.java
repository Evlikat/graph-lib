package net.evlikat;

import java.util.Objects;
import java.util.stream.Stream;

public class Step<V> {

    private final V vertex1;
    private final V vertex2;

    public Step(V vertex1, V vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    public V getVertex1() {
        return vertex1;
    }

    public V getVertex2() {
        return vertex2;
    }

    public Stream<V> vertices() {
        return Stream.of(vertex1, vertex2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step<?> step = (Step<?>) o;
        return Objects.equals(vertex1, step.vertex1) &&
                Objects.equals(vertex2, step.vertex2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex1, vertex2);
    }

    @Override
    public String toString() {
        return "'" + vertex1 + "' -> " + vertex2 + "'";
    }
}
