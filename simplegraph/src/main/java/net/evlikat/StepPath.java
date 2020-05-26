package net.evlikat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StepPath<V> {

    private final List<Step<V>> edges;

    public StepPath(List<Step<V>> edges) {
        this.edges = edges;
    }

    public static <V> Builder<V> builder() {
        return new Builder<>(new ArrayList<>());
    }

    @Override
    public String toString() {
        return "StepPath" + edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepPath<?> stepPath = (StepPath<?>) o;
        return Objects.equals(edges, stepPath.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(edges);
    }

    public static class Builder<V> {

        private final List<Step<V>> buildingPathChunks;

        private Builder(List<Step<V>> buildingPathChunks) {
            this.buildingPathChunks = new ArrayList<>(buildingPathChunks);
        }

        public Builder<V> edge(V vertex1, V vertex2) {
            buildingPathChunks.add(new Step<>(vertex1, vertex2));
            return this;
        }

        public StepPath<V> build() {
            return new StepPath<>(buildingPathChunks);
        }
    }
}
