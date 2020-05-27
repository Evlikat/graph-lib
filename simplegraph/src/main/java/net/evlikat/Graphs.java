package net.evlikat;

public final class Graphs {

    public static <V> Graph<V> newGraph() {
        return new TableGraph<>(new NaivePathFinder<>());
    }

    public static <V> DirectedGraph<V> newDirectedGraph() {
        return new TableDirectedGraph<>(new NaivePathFinder<>());
    }
}
