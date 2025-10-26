package cli;

import algorithms.Kruskal;
import algorithms.Prim;
import metrics.PerformanceTracker;
import utils.JsonGraphReader;
import utils.JsonGraphReader.GraphData;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MSTBenchmarkRunner {

    public static void main(String[] args) {
        String inputPath = "src/main/resources/input/ass_3_input.json";
        String outputPath = "src/main/resources/output/ass_3_output.json";

        List<GraphData> graphs = JsonGraphReader.readGraphs(inputPath);
        List<Map<String, Object>> results = new ArrayList<>();

        for (GraphData graphData : graphs) {
            System.out.println("\n🚦 Running MST for: " + graphData.name);

            // --- PRIM ---
            PerformanceTracker trackerPrim = new PerformanceTracker();
            Prim.Result primRes = Prim.runPrim(graphData.adjacency, graphData.vertices, trackerPrim);

            // --- KRUSKAL ---
            PerformanceTracker trackerKruskal = new PerformanceTracker();
            Kruskal.Result kruskalRes = Kruskal.runKruskal(graphData.edges, graphData.vertices, trackerKruskal);


            Map<String, Object> record = new LinkedHashMap<>();
            record.put("Graph", graphData.name);
            record.put("Vertices", graphData.vertices);
            record.put("Prim_TotalCost", primRes.totalCost);
            record.put("Kruskal_TotalCost", kruskalRes.totalCost);
            record.put("Prim_TimeMs", primRes.timeMs);
            record.put("Kruskal_TimeMs", kruskalRes.timeMs);
            record.put("Prim_Comparisons", primRes.comparisons);
            record.put("Kruskal_Unions", kruskalRes.unions);
            record.put("Prim_ArrayAccesses", primRes.arrayAccesses);
            record.put("SameCost", primRes.totalCost == kruskalRes.totalCost);

            results.add(record);

            System.out.println("Done: Prim=" + primRes.totalCost + " Kruskal=" + kruskalRes.totalCost);
        }

        writeOutputJson(results, outputPath);
        System.out.println("\n Results written to " + outputPath);
    }

    private static void writeOutputJson(List<Map<String, Object>> results, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(results, writer);
        } catch (IOException e) {
            System.err.println("Error writing output JSON: " + e.getMessage());
        }
    }
}
