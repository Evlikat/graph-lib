package net.evlikat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TableGraph<V> implements Graph<V> {

    private final Map<V, Map<V, Edge>> vertexMap = new HashMap<>();
    private final PathFinder<V, Edge> pathFinder;

    TableGraph(PathFinder<V, Edge> pathFinder) {
        this.pathFinder = pathFinder;
    }

    @Override
    public int order() {
        return vertexMap.size();
    }

    @Override
    public int size() {
        return vertexMap.values().stream().mapToInt(Map::size).sum() / 2;
    }

    @Override
    public void addVertex(V vertex) {
        vertexMap.put(vertex, directionMap());
    }

    @Override
    public void addWeightedEdge(V vertex1, V vertex2, float weight) {
        Edge edge = Edge.of(weight);

        vertexMap
                .computeIfAbsent(vertex1, v -> directionMap())
                .put(vertex2, edge);

        if (!vertex1.equals(vertex2)) {
            vertexMap
                    .computeIfAbsent(vertex2, v -> directionMap())
                    .put(vertex1, edge);
        }
    }

    @Override
    public void addEdge(V vertex1, V vertex2) {
        addWeightedEdge(vertex1, vertex2, 1);
    }

    @Override
    public Optional<StepPath<V>> getAnyPath(V fromVertex, V toVertex) {
        return pathFinder.getPath(fromVertex, toVertex, vertexMap::get);
    }

    private Map<V, Edge> directionMap() {
        return new HashMap<>();
    }
}
