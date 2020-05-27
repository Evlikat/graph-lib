package net.evlikat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class TableDirectedGraph<V, A extends Arc<V>> implements DirectedGraph<V, A> {

    private final Map<V, Map<V, A>> vertexMap = new HashMap<>();
    private final PathFinder<V, A> pathFinder;

    TableDirectedGraph(PathFinder<V, A> pathFinder) {
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
    public void addArc(A arc) {

        V fromVertex = arc.getFromVertex();
        V toVertex = arc.getToVertex();

        vertexMap
                .computeIfAbsent(fromVertex, v -> directionMap())
                .put(toVertex, arc);
    }

    @Override
    public Optional<StepPath<A>> getAnyPath(V fromVertex, V toVertex) {
        return pathFinder.getPath(fromVertex, toVertex, vertexMap::get);
    }

    private Map<V, A> directionMap() {
        return new HashMap<>();
    }
}
