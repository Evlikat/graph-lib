package net.evlikat;

public interface DirectedGraph<V> {

    int order();

    int size();

    void addVertex(V vertex);

    void addArc(V fromVertex, V toVertex);

    void addWeightedArc(V fromVertex, V toVertex, float weight);

    StepPath<V> getAnyPath(V fromVertex, V toVertex);
}
