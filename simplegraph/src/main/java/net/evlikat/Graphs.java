package net.evlikat;

public final class Graphs {

    public static <V, E extends Edge<V>> Graph<V, E> newGraph() {
        return new TableGraph<>(new NaivePathFinder<>());
    }

    public static <V, A extends Arc<V>> DirectedGraph<V, A> newDirectedGraph() {
        return new TableDirectedGraph<>(new NaivePathFinder<>());
    }
}
