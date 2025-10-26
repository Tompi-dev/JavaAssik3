package cli;

import algorithms.Prim;
import algorithms.Kruskal;
import algorithms.Kruskal.Edge;
import metrics.PerformanceTracker;
import utils.CSVExporter;
import java.util.*;

public class TestGraph {
    public static void main(String[] args) {
        // Example small graph
        List<List<Prim.Edge>> adjacency = new ArrayList<>();
        for (int i = 0; i < 5; i++) adjacency.add(new ArrayList<>());
        addEdge(adjacency, 0, 1, 10);
        addEdge(adjacency, 0, 2, 5);
        addEdge(adjacency, 1, 3, 7);
        addEdge(adjacency, 3, 4, 2);

        // Convert to edge list for Kruskal
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 10),
                new Edge(0, 2, 5),
                new Edge(1, 3, 7),
                new Edge(3, 4, 2)
        );

        // Run both algorithms
        PerformanceTracker p1 = new PerformanceTracker();
        PerformanceTracker p2 = new PerformanceTracker();

        Prim.Result prim = Prim.runPrim(adjacency, 5, p1);
        Kruskal.Result kruskal = Kruskal.runKruskal(edges, 5, p2);

        // Display to console
        System.out.println("Prim cost: " + prim.totalCost + " | time: " + prim.timeMs + " ms");
        System.out.println("Kruskal cost: " + kruskal.totalCost + " | time: " + kruskal.timeMs + " ms");

        // --- Write to CSV ---
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"Dataset", "Algorithm", "Vertices", "Edges", "TotalCost", "TimeMs", "Comparisons"});
        rows.add(new String[]{"CityNetwork", "Prim", "5", String.valueOf(edges.size()),
                String.valueOf(prim.totalCost), String.valueOf(prim.timeMs), String.valueOf(p1.getComparisons())});
        rows.add(new String[]{"CityNetwork", "Kruskal", "5", String.valueOf(edges.size()),
                String.valueOf(kruskal.totalCost), String.valueOf(kruskal.timeMs), String.valueOf(p2.getComparisons())});

        CSVExporter.writeCSV("src/main/resources/output/results.csv", rows);
    }

    private static void addEdge(List<List<Prim.Edge>> graph, int src, int dest, int w) {
        graph.get(src).add(new Prim.Edge(src, dest, w));
        graph.get(dest).add(new Prim.Edge(dest, src, w));
    }
}
