package net.evlikat;

import java.util.Optional;

public interface DirectedGraph<V, A extends Arc<V>> {

    /**
     * @return a number of vertices in the graph
     */
    int order();

    /**
     * @return a number of edges in the graph
     */
    int size();

    /**
     * Registers a vertex in the graph. The vertex is not connected to any other vertex.
     * Use {@link DirectedGraph#addArc(A)} to connect existing vertices.
     */
    void addVertex(V vertex);

    /**
     * Creates an arc from one vertex to another one specified by the parameter.
     */
    void addArc(A arc);

    /**
     * Finds a path from one vertex to another.
     * The path contains both vertices if it exists.
     *
     * @param fromVertex starting vertex
     * @param toVertex   ending vertex
     * @return a path or {@link Optional#empty()} if there is no path between vertices
     */
    Optional<StepPath<A>> getAnyPath(V fromVertex, V toVertex);
}
