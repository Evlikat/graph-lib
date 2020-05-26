package net.evlikat;

import org.junit.Test;

import static net.evlikat.Graphs.newGraph;
import static org.assertj.core.api.Assertions.assertThat;

public class TableGraphTest {

    @Test
    public void shouldAddVertex() {
        Graph<String> graph = newGraph();

        assertThat(graph.order()).isEqualTo(0);

        graph.addVertex("a");

        assertThat(graph.order()).isEqualTo(1);
    }

    @Test
    public void shouldAddEdge() {
        Graph<String> graph = newGraph();

        assertThat(graph.size()).isEqualTo(0);

        graph.addEdge("a", "b");

        assertThat(graph.size()).isEqualTo(1);
    }

    @Test
    public void shouldCalculateOrder() {
        Graph<String> graph = newGraph();

        graph.addEdge("a", "b");
        graph.addEdge("b", "c");
        graph.addEdge("a", "c");
        graph.addEdge("c", "d");
        graph.addEdge("d", "e");

        assertThat(graph.order()).isEqualTo(5);
    }

    @Test
    public void shouldCalculateSize() {
        Graph<String> graph = newGraph();

        graph.addEdge("a", "b");
        graph.addEdge("b", "c");
        graph.addEdge("a", "c");
        graph.addEdge("c", "d");

        assertThat(graph.size()).isEqualTo(4);
    }

    @Test
    public void shouldFindPathBetweenNeighbourVertices() {
        Graph<String> graph = newGraph();
        graph.addEdge("a", "b");

        StepPath<String> path = graph.getAnyPath("a", "b");

        StepPath<String> expectedPath = StepPath.<String>builder()
                .edge("a", "b")
                .build();
        assertThat(path).isEqualTo(expectedPath);
    }

    @Test
    public void shouldFindTransitivePath() {
        Graph<String> graph = newGraph();
        graph.addEdge("a", "b");
        graph.addEdge("b", "c");

        StepPath<String> path = graph.getAnyPath("a", "c");

        StepPath<String> expectedPath = StepPath.<String>builder()
                .edge("a", "b")
                .edge("b", "c")
                .build();
        assertThat(path).isEqualTo(expectedPath);
    }

    @Test
    public void shouldFindPathAvoidCycle() {
        Graph<String> graph = newGraph();
        graph.addEdge("a", "b");
        graph.addEdge("b", "c");
        graph.addEdge("c", "d");
        graph.addEdge("a", "c");

        StepPath<String> path = graph.getAnyPath("a", "d");

        StepPath<String> expectedPath = StepPath.<String>builder()
                .edge("a", "c")
                .edge("c", "d")
                .build();
        assertThat(path).isEqualTo(expectedPath);
    }

    @Test(expected = PathDoesNotExistException.class)
    public void shouldNotFindPathWhenItDoesNotExist() {
        Graph<String> graph = newGraph();
        graph.addEdge("a", "b");
        graph.addEdge("c", "d");

        graph.getAnyPath("a", "c");
    }
}