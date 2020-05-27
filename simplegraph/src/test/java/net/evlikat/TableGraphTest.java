package net.evlikat;

import org.junit.Test;

import java.util.Optional;

import static net.evlikat.Graphs.newGraph;
import static net.evlikat.SimpleEdge.between;
import static org.assertj.core.api.Assertions.assertThat;

public class TableGraphTest {

    @Test
    public void shouldAddVertex() {
        Graph<String, SimpleEdge<String>> graph = newGraph();

        assertThat(graph.order()).isEqualTo(0);

        graph.addVertex("a");

        assertThat(graph.order()).isEqualTo(1);
    }

    @Test
    public void shouldAddEdge() {
        Graph<String, SimpleEdge<String>> graph = newGraph();

        assertThat(graph.size()).isEqualTo(0);

        graph.addEdge(between("a", "b"));

        assertThat(graph.size()).isEqualTo(1);
    }

    @Test
    public void shouldCalculateOrder() {
        Graph<String, SimpleEdge<String>> graph = newGraph();

        graph.addEdge(between("a", "b"));
        graph.addEdge(between("b", "c"));
        graph.addEdge(between("a", "c"));
        graph.addEdge(between("c", "d"));
        graph.addEdge(between("d", "e"));

        assertThat(graph.order()).isEqualTo(5);
    }

    @Test
    public void shouldCalculateSize() {
        Graph<String, SimpleEdge<String>> graph = newGraph();

        graph.addEdge(between("a", "b"));
        graph.addEdge(between("b", "c"));
        graph.addEdge(between("a", "c"));
        graph.addEdge(between("c", "d"));

        assertThat(graph.size()).isEqualTo(4);
    }

    @Test
    public void shouldFindPathBetweenNeighbourVertices() {
        Graph<String, SimpleEdge<String>> graph = newGraph();
        graph.addEdge(between("a", "b"));

        Optional<StepPath<SimpleEdge<String>>> path = graph.getAnyPath("a", "b");

        StepPath<SimpleEdge<String>> expectedPath = StepPath.<SimpleEdge<String>>builder()
                .edge(between("a", "b"))
                .build();
        assertThat(path).contains(expectedPath);
    }

    @Test
    public void shouldFindTransitivePath() {
        Graph<String, SimpleEdge<String>> graph = newGraph();
        graph.addEdge(between("a", "b"));
        graph.addEdge(between("b", "c"));

        Optional<StepPath<SimpleEdge<String>>> path = graph.getAnyPath("a", "c");

        StepPath<SimpleEdge<String>> expectedPath = StepPath.<SimpleEdge<String>>builder()
                .edge(between("a", "b"))
                .edge(between("b", "c"))
                .build();
        assertThat(path).contains(expectedPath);
    }

    @Test
    public void shouldFindPathAvoidCycle() {
        Graph<String, SimpleEdge<String>> graph = newGraph();
        graph.addEdge(between("a", "b"));
        graph.addEdge(between("b", "c"));
        graph.addEdge(between("c", "d"));
        graph.addEdge(between("a", "c"));

        Optional<StepPath<SimpleEdge<String>>> path = graph.getAnyPath("a", "d");

        StepPath<SimpleEdge<String>> expectedPath = StepPath.<SimpleEdge<String>>builder()
                .edge(between("a", "c"))
                .edge(between("c", "d"))
                .build();
        assertThat(path).contains(expectedPath);
    }

    @Test
    public void shouldNotFindPathWhenItDoesNotExist() {
        Graph<String, SimpleEdge<String>> graph = newGraph();
        graph.addEdge(between("a", "b"));
        graph.addEdge(between("c", "d"));

        Optional<StepPath<SimpleEdge<String>>> path = graph.getAnyPath("a", "c");

        assertThat(path).isEmpty();
    }
}