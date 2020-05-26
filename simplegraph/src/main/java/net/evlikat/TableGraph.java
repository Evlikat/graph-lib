package net.evlikat;

import com.google.common.collect.Streams;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class TableGraph<V> implements Graph<V> {

    private final Map<V, Map<V, Edge>> vertexMap = new HashMap<>();

    TableGraph() {
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
    public StepPath<V> getAnyPath(V fromVertex, V toVertex) {
        Deque<Path.Builder<ConnectedVertex<V>>> walkStack = new ArrayDeque<>();
        Path.Builder<ConnectedVertex<V>> startingPath = Path.builder();
        ConnectedVertex<V> startingConnectedVertex = new ConnectedVertex<>(fromVertex, null);
        startingPath.vertex(startingConnectedVertex);
        walkStack.add(startingPath);

        Path.Builder<ConnectedVertex<V>> pathBuilder = find(toVertex, walkStack);
        if (pathBuilder == null) {
            throw new PathDoesNotExistException();
        }
        Path<ConnectedVertex<V>> path = pathBuilder.build();
        if (path.isEmpty()) {
            throw new PathDoesNotExistException();
        }
        return new StepPath<>(
                Streams.zip(
                        path.getPathChunks().stream(),
                        path.getPathChunks().stream().skip(1),
                        (cv1, cv2) -> new Step<>(cv1.vertex, cv2.vertex)
                ).collect(toList())
        );
    }

    private Path.Builder<ConnectedVertex<V>> find(V toVertex, Deque<Path.Builder<ConnectedVertex<V>>> walkStack) {
        while (!walkStack.isEmpty()) {
            Path.Builder<ConnectedVertex<V>> pathCandidate = walkStack.pop();
            V currentVertex = pathCandidate.getLastElement().vertex;

            if (currentVertex.equals(toVertex)) {
                return pathCandidate;
            }

            Map<V, Edge> connectedVertices = vertexMap.get(currentVertex);
            if (connectedVertices != null) {
                walkStack.addAll(
                        connectedVertices.entrySet().stream()
                                .map(entry -> new ConnectedVertex<>(entry.getKey(), entry.getValue()))
                                .filter(cv -> !pathCandidate.contains(cv))
                                .map(cv -> pathCandidate.copy().vertex(cv))
                                .collect(toList())
                );
            }
        }
        return null;
    }

    private Map<V, Edge> directionMap() {
        return new HashMap<>();
    }
}
