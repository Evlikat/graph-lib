package net.evlikat;

import org.junit.Test;

import java.util.Optional;

import static net.evlikat.Graphs.newDirectedGraph;
import static net.evlikat.SimpleArc.between;
import static org.assertj.core.api.Assertions.assertThat;

public class TableDirectedGraphTest {

    @Test
    public void shouldAddVertex() {
        DirectedGraph<String, SimpleArc<String>> graph = newDirectedGraph();

        assertThat(graph.order()).isEqualTo(0);

        graph.addVertex("a");

        assertThat(graph.order()).isEqualTo(1);
    }

    @Test
    public void shouldaddArc() {
        DirectedGraph<String, SimpleArc<String>> graph = newDirectedGraph();

        assertThat(graph.size()).isEqualTo(0);

        graph.addArc(between("a", "b"));

        assertThat(graph.size()).isEqualTo(1);
    }

    @Test
    public void shouldCalculateOrder() {
        DirectedGraph<String, SimpleArc<String>> graph = newDirectedGraph();

        graph.addArc(between("a", "b"));
        graph.addArc(between("b", "c"));
        graph.addArc(between("a", "c"));
        graph.addArc(between("c", "d"));
        graph.addArc(between("d", "e"));

        assertThat(graph.order()).isEqualTo(5);
    }

    @Test
    public void shouldCalculateSize() {
        DirectedGraph<String, SimpleArc<String>> graph = newDirectedGraph();

        graph.addArc(between("a", "b"));
        graph.addArc(between("b", "c"));
        graph.addArc(between("a", "c"));
        graph.addArc(between("c", "d"));

        assertThat(graph.size()).isEqualTo(4);
    }

    @Test
    public void shouldFindPathBetweenNeighbourVertices() {
        DirectedGraph<String, SimpleArc<String>> graph = newDirectedGraph();
        graph.addArc(between("a", "b"));

        Optional<StepPath<SimpleArc<String>>> path = graph.getAnyPath("a", "b");

        StepPath<SimpleArc<String>> expectedPath = StepPath.<SimpleArc<String>>builder()
                .edge(between("a", "b"))
                .build();
        assertThat(path).contains(expectedPath);
    }

    @Test
    public void shouldFindTransitivePath() {
        DirectedGraph<String, SimpleArc<String>> graph = newDirectedGraph();
        graph.addArc(between("a", "b"));
        graph.addArc(between("b", "c"));

        Optional<StepPath<SimpleArc<String>>> path = graph.getAnyPath("a", "c");

        StepPath<SimpleArc<String>> expectedPath = StepPath.<SimpleArc<String>>builder()
                .edge(between("a", "b"))
                .edge(between("b", "c"))
                .build();
        assertThat(path).contains(expectedPath);
    }

    @Test
    public void shouldNotFindPathWithReversedDirection() {
        DirectedGraph<String, SimpleArc<String>> graph = newDirectedGraph();
        graph.addArc(between("a", "b"));
        graph.addArc(between("b", "c"));

        Optional<StepPath<SimpleArc<String>>> path = graph.getAnyPath("c", "a");

        assertThat(path).isEmpty();
    }

    @Test
    public void shouldNotFindPathWithReversedArc() {
        DirectedGraph<String, SimpleArc<String>> graph = newDirectedGraph();
        graph.addArc(between("a", "b"));
        graph.addArc(between("c", "b"));

        Optional<StepPath<SimpleArc<String>>> path = graph.getAnyPath("a", "c");

        assertThat(path).isEmpty();
    }

    @Test
    public void shouldFindPathAvoidCycle() {
        DirectedGraph<String, SimpleArc<String>> graph = newDirectedGraph();
        graph.addArc(between("a", "b"));
        graph.addArc(between("b", "c"));
        graph.addArc(between("c", "d"));
        graph.addArc(between("a", "c"));

        Optional<StepPath<SimpleArc<String>>> path = graph.getAnyPath("a", "d");

        StepPath<SimpleArc<String>> expectedPath = StepPath.<SimpleArc<String>>builder()
                .edge(between("a", "c"))
                .edge(between("c", "d"))
                .build();
        assertThat(path).contains(expectedPath);
    }

    @Test
    public void shouldNotFindPathWhenItDoesNotExist() {
        DirectedGraph<String, SimpleArc<String>> graph = newDirectedGraph();
        graph.addArc(between("a", "b"));
        graph.addArc(between("c", "d"));

        Optional<StepPath<SimpleArc<String>>> path = graph.getAnyPath("a", "c");

        assertThat(path).isEmpty();
    }
}