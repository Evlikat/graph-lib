package net.evlikat;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface PathFinder<V, E> {

    /**
     * Tries to find a path between specified vertices.
     *
     * @param fromVertex          starting vertex
     * @param toVertex            ending vertex
     * @param getAdjacentVertices a function representing adjacent matrix cell
     * @return a path or {@link Optional#empty()} if there is no path between vertices
     */
    Optional<StepPath<E>> getPath(V fromVertex, V toVertex, Function<V, Map<V, E>> getAdjacentVertices);
}
