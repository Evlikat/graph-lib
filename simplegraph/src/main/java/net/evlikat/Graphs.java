package net.evlikat;

import java.util.function.IntFunction;

public final class Graphs {

    public static <V> Graph<V> newGraph() {
        return new TableGraph<>();
    }

    public static <V> DirectedGraph<V> newDirectedGraph() {
        return new TableDirectedGraph<>();
    }

    public static <V> Graph<V> newFullGraph(int vertexNumber, IntFunction<V> getVertex) {
        TableGraph<V> graph = new TableGraph<>();
        for (int i = 0; i < vertexNumber; i++) {
            for (int ix = i + 1; ix < vertexNumber; ix++) {
                graph.addEdge(getVertex.apply(i), getVertex.apply(ix));
            }
        }
        return graph;
    }
}
