package net.evlikat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class TableDirectedGraph<V> implements DirectedGraph<V> {

    private final Map<V, Map<V, Arc>> vertexMap = new HashMap<>();
    private final PathFinder<V, Arc> pathFinder;

    TableDirectedGraph(PathFinder<V, Arc> pathFinder) {
        this.pathFinder = pathFinder;
    }

    @Override
    public int order() {
        return (int) Stream.concat(
                vertexMap.keySet().stream(),
                vertexMap.values().stream().flatMap(t -> t.keySet().stream())
        ).distinct().count();
    }

    @Override
    public int size() {
        return vertexMap.values().stream().mapToInt(Map::size).sum();
    }

    @Override
    public void addVertex(V vertex) {
        vertexMap.put(vertex, directionMap());
    }

    @Override
    public void addArc(V fromVertex, V toVertex) {
        addWeightedArc(fromVertex, toVertex, 1);
    }

    @Override
    public void addWeightedArc(V fromVertex, V toVertex, float weight) {
        Arc edge = Arc.of(weight);

        vertexMap
                .computeIfAbsent(fromVertex, v -> directionMap())
                .put(toVertex, edge);
    }

    @Override
    public Optional<StepPath<V>> getAnyPath(V fromVertex, V toVertex) {
        return pathFinder.getPath(fromVertex, toVertex, vertexMap::get);
    }

    private Map<V, Arc> directionMap() {
        return new HashMap<>();
    }
}
