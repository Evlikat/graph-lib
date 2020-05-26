package net.evlikat;

import java.util.*;

public class Path<V> {

    private final List<V> pathChunks;

    public Path(Collection<V> pathChunks) {
        this.pathChunks = new ArrayList<>(pathChunks);
    }

    public static <V> Builder<V> builder() {
        return new Builder<>(new LinkedHashSet<>(), null);
    }

    public List<V> getPathChunks() {
        return pathChunks;
    }

    public boolean isEmpty() {
        return pathChunks.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path<?> path = (Path<?>) o;
        return Objects.equals(pathChunks, path.pathChunks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathChunks);
    }

    @Override
    public String toString() {
        return "Path" + pathChunks;
    }


    public static class Builder<V> {

        private final LinkedHashSet<V> buildingPathChunks;
        private V lastElement;

        private Builder(LinkedHashSet<V> buildingPathChunks, V lastElement) {
            this.buildingPathChunks = new LinkedHashSet<>(buildingPathChunks);
            this.lastElement = lastElement;
        }

        public V getLastElement() {
            return lastElement;
        }

        public Builder<V> vertex(V vertex) {
            buildingPathChunks.add(vertex);
            lastElement = vertex;
            return this;
        }

        public Builder<V> copy() {
            return new Builder<>(buildingPathChunks, lastElement);
        }

        public Path<V> build() {
            return new Path<>(buildingPathChunks);
        }

        public boolean contains(V vertex) {
            return buildingPathChunks.contains(vertex);
        }
    }
}