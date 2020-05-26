package net.evlikat;

import org.junit.Test;

import static net.evlikat.Graphs.newDirectedGraph;
import static org.assertj.core.api.Assertions.assertThat;

public class TableDirectedGraphTest {

    @Test
    public void shouldAddVertex() {
        DirectedGraph<String> graph = newDirectedGraph();

        assertThat(graph.order()).isEqualTo(0);

        graph.addVertex("a");

        assertThat(graph.order()).isEqualTo(1);
    }

    @Test
    public void shouldAddArc() {
        DirectedGraph<String> graph = newDirectedGraph();

        assertThat(graph.size()).isEqualTo(0);

        graph.addArc("a", "b");

        assertThat(graph.size()).isEqualTo(1);
    }

    @Test
    public void shouldCalculateOrder() {
        DirectedGraph<String> graph = newDirectedGraph();

        graph.addArc("a", "b");
        graph.addArc("b", "c");
        graph.addArc("a", "c");
        graph.addArc("c", "d");
        graph.addArc("d", "e");

        assertThat(graph.order()).isEqualTo(5);
    }

    @Test
    public void shouldCalculateSize() {
        DirectedGraph<String> graph = newDirectedGraph();

        graph.addArc("a", "b");
        graph.addArc("b", "c");
        graph.addArc("a", "c");
        graph.addArc("c", "d");

        assertThat(graph.size()).isEqualTo(4);
    }

    @Test
    public void shouldFindPathBetweenNeighbourVertices() {
        DirectedGraph<String> graph = newDirectedGraph();
        graph.addArc("a", "b");

        StepPath<String> path = graph.getAnyPath("a", "b");

        StepPath<String> expectedPath = StepPath.<String>builder()
                .edge("a", "b")
                .build();
        assertThat(path).isEqualTo(expectedPath);
    }

    @Test
    public void shouldFindTransitivePath() {
        DirectedGraph<String> graph = newDirectedGraph();
        graph.addArc("a", "b");
        graph.addArc("b", "c");

        StepPath<String> path = graph.getAnyPath("a", "c");

        StepPath<String> expectedPath = StepPath.<String>builder()
                .edge("a", "b")
                .edge("b", "c")
                .build();
        assertThat(path).isEqualTo(expectedPath);
    }

    @Test(expected = PathDoesNotExistException.class)
    public void shouldNotFindPathWithReversedDirection() {
        DirectedGraph<String> graph = newDirectedGraph();
        graph.addArc("a", "b");
        graph.addArc("b", "c");

        graph.getAnyPath("c", "a");
    }

    @Test(expected = PathDoesNotExistException.class)
    public void shouldNotFindPathWithReversedArc() {
        DirectedGraph<String> graph = newDirectedGraph();
        graph.addArc("a", "b");
        graph.addArc("c", "b");

        graph.getAnyPath("a", "c");
    }

    @Test
    public void shouldFindPathAvoidCycle() {
        DirectedGraph<String> graph = newDirectedGraph();
        graph.addArc("a", "b");
        graph.addArc("b", "c");
        graph.addArc("c", "d");
        graph.addArc("a", "c");

        StepPath<String> path = graph.getAnyPath("a", "d");

        StepPath<String> expectedPath = StepPath.<String>builder()
                .edge("a", "c")
                .edge("c", "d")
                .build();
        assertThat(path).isEqualTo(expectedPath);
    }

    @Test(expected = PathDoesNotExistException.class)
    public void shouldNotFindPathWhenItDoesNotExist() {
        DirectedGraph<String> graph = newDirectedGraph();
        graph.addArc("a", "b");
        graph.addArc("c", "d");

        graph.getAnyPath("a", "c");
    }
}