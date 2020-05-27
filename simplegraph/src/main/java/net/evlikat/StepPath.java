package net.evlikat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StepPath<E> {

    private final List<E> edges;

    public StepPath(List<E> edges) {
        this.edges = edges;
    }

    public static <E> Builder<E> builder() {
        return new Builder<>(new ArrayList<>());
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

    @Override
    public String toString() {
        return "StepPath" + edges;
    }

    public static class Builder<E> {

        private final List<E> buildingPathChunks;

        private Builder(List<E> buildingPathChunks) {
            this.buildingPathChunks = new ArrayList<>(buildingPathChunks);
        }

        public Builder<E> edge(E edge) {
            buildingPathChunks.add(edge);
            return this;
        }

        public StepPath<E> build() {
            return new StepPath<>(buildingPathChunks);
        }
    }
}
