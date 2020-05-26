package net.evlikat;

import java.util.Objects;

class ConnectedVertex<V> {
    final V vertex;
    final Edge edge;

    ConnectedVertex(V vertex, Edge edge) {
        this.vertex = vertex;
        this.edge = edge;
    }

    ConnectedVertex(ConnectedVertex<V> another) {
        this.vertex = another.vertex;
        this.edge = another.edge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectedVertex<?> that = (ConnectedVertex<?>) o;
        return Objects.equals(vertex, that.vertex) &&
                Objects.equals(edge, that.edge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex, edge);
    }
}
