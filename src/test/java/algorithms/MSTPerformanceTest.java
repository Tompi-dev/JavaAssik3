package algorithms;

import metrics.PerformanceTracker;
import utils.JsonGraphReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MSTPerformanceTest {

    private static final String INPUT_PATH = "src/main/resources/input/ass_3_input.json";

    @Test
    public void testExecutionTimeAndOperationsNonNegative() {
        // читаем графы из JSON
        List<JsonGraphReader.GraphData> graphs = JsonGraphReader.readGraphs(INPUT_PATH);
        Assertions.assertNotNull(graphs, "Graph list should not be null");
        Assertions.assertFalse(graphs.isEmpty(), "Input file must contain at least one graph");

        for (JsonGraphReader.GraphData graph : graphs) {
            PerformanceTracker primTracker = new PerformanceTracker();
            PerformanceTracker kruskalTracker = new PerformanceTracker();

            // === Prim ===
            Prim.Result primResult = Prim.runPrim(graph.adjacency, graph.vertices, primTracker);

            // === Kruskal ===
            Kruskal.Result kruskalResult = Kruskal.runKruskal(graph.edges, graph.vertices, kruskalTracker);

            // Проверяем время выполнения
            Assertions.assertTrue(primResult.timeMs >= 0, "Prim time must be non-negative");
            Assertions.assertTrue(kruskalResult.timeMs >= 0, "Kruskal time must be non-negative");

            // Проверяем количество операций
            Assertions.assertTrue(primTracker.getComparisons() >= 0, "Prim comparisons ≥ 0");
            Assertions.assertTrue(primTracker.getArrayAccesses() >= 0, "Prim array accesses ≥ 0");
            Assertions.assertTrue(kruskalResult.comparisons >= 0, "Kruskal comparisons ≥ 0");
            Assertions.assertTrue(kruskalResult.unions >= 0, "Kruskal unions ≥ 0");
        }
    }

    @Test
    public void testReproducibleResults() {
        List<JsonGraphReader.GraphData> graphs = JsonGraphReader.readGraphs(INPUT_PATH);
        if (graphs == null || graphs.isEmpty()) return;

        JsonGraphReader.GraphData g = graphs.get(0);

        PerformanceTracker t1 = new PerformanceTracker();
        PerformanceTracker t2 = new PerformanceTracker();

        Prim.Result r1 = Prim.runPrim(g.adjacency, g.vertices, t1);
        Prim.Result r2 = Prim.runPrim(g.adjacency, g.vertices, t2);

        Assertions.assertEquals(r1.totalCost, r2.totalCost, "Prim MST total cost must be reproducible");
        Assertions.assertTrue(r1.timeMs >= 0 && r2.timeMs >= 0, "Execution times must be non-negative");
    }
}
