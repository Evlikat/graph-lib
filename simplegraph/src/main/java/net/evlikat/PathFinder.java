package net.evlikat;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface PathFinder<V, E> {

    Optional<StepPath<V>> getPath(V fromVertex, V toVertex, Function<V, Map<V, E>> getAdjacentVertices);
}
