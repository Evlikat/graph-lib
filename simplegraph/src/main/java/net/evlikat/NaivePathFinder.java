package net.evlikat;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

class NaivePathFinder<V, E> implements PathFinder<V, E> {

    @Override
    public Optional<StepPath<E>> getPath(V fromVertex, V toVertex, Function<V, Map<V, E>> getAdjacentVertices) {
        Deque<Path.Builder<ConnectedVertex<V, E>>> walkStack = new ArrayDeque<>();
        Path.Builder<ConnectedVertex<V, E>> startingPath = Path.builder();
        ConnectedVertex<V, E> startingConnectedVertex = new ConnectedVertex<>(fromVertex, null);
        startingPath.vertex(startingConnectedVertex);
        walkStack.add(startingPath);

        Path.Builder<ConnectedVertex<V, E>> pathBuilder = find(toVertex, walkStack, getAdjacentVertices);
        if (pathBuilder == null) {
            return Optional.empty();
        }
        Path<ConnectedVertex<V, E>> path = pathBuilder.build();
        if (path.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                new StepPath<>(
                        path.getPathChunks().stream().map(cv -> cv.edge).filter(Objects::nonNull).collect(toList())
                )
        );
    }

    private Path.Builder<ConnectedVertex<V, E>> find(
            V toVertex,
            Deque<Path.Builder<ConnectedVertex<V, E>>> walkStack,
            Function<V, Map<V, E>> getAdjacentVertices
    ) {
        while (!walkStack.isEmpty()) {
            Path.Builder<ConnectedVertex<V, E>> pathCandidate = walkStack.pop();
            V currentVertex = pathCandidate.getLastElement().vertex;

            if (currentVertex.equals(toVertex)) {
                return pathCandidate;
            }

            Map<V, E> connectedVertices = getAdjacentVertices.apply(currentVertex);
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
}
