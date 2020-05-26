package net.evlikat;

public interface Graph<V> {

    int order();

    int size();

    void addVertex(V vertex);

    void addEdge(V vertex1, V vertex2);

    void addWeightedEdge(V vertex1, V vertex2, float weight);

    StepPath<V> getAnyPath(V fromVertex, V toVertex);
}
