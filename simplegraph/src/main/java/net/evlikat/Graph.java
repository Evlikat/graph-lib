package net.evlikat;

import java.util.Optional;

public interface Graph<V> {

    int order();

    int size();

    void addVertex(V vertex);

    void addEdge(V vertex1, V vertex2);

    void addWeightedEdge(V vertex1, V vertex2, float weight);

    Optional<StepPath<V>> getAnyPath(V fromVertex, V toVertex);
}
