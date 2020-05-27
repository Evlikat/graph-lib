package net.evlikat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TableGraph<V, E extends Edge<V>> implements Graph<V, E> {

    private final Map<V, Map<V, E>> vertexMap = new HashMap<>();
    private final PathFinder<V, E> pathFinder;

    TableGraph(PathFinder<V, E> pathFinder) {
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
    public void addEdge(E edge) {

        V vertex1 = edge.getVertex1();
        V vertex2 = edge.getVertex2();

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
    public Optional<StepPath<E>> getAnyPath(V fromVertex, V toVertex) {
        return pathFinder.getPath(fromVertex, toVertex, vertexMap::get);
    }

    private Map<V, E> directionMap() {
        return new HashMap<>();
    }
}
