package utils;

import algorithms.Kruskal;
import algorithms.Prim;
import com.google.gson.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JsonGraphReader {

    public static List<GraphData> readGraphs(String filePath) {
        List<GraphData> graphs = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray jsonGraphs = root.getAsJsonArray("graphs");

            for (JsonElement g : jsonGraphs) {
                JsonObject obj = g.getAsJsonObject();
                String name = obj.get("name").getAsString();
                int vertices = obj.get("vertices").getAsInt();
                JsonArray edgesArray = obj.getAsJsonArray("edges");

                List<Kruskal.Edge> kruskalEdges = new ArrayList<>();
                List<List<Prim.Edge>> adjacency = new ArrayList<>();
                for (int i = 0; i < vertices; i++) adjacency.add(new ArrayList<>());

                for (JsonElement e : edgesArray) {
                    JsonObject edgeObj = e.getAsJsonObject();
                    int src = edgeObj.get("src").getAsInt();
                    int dest = edgeObj.get("dest").getAsInt();
                    int weight = edgeObj.get("weight").getAsInt();


                    kruskalEdges.add(new Kruskal.Edge(src, dest, weight));
                    adjacency.get(src).add(new Prim.Edge(src, dest, weight));
                    adjacency.get(dest).add(new Prim.Edge(dest, src, weight));
                }

                graphs.add(new GraphData(name, vertices, kruskalEdges, adjacency));
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON: " + e.getMessage());
        }

        return graphs;
    }

    public static class GraphData {
        public final String name;
        public final int vertices;
        public final List<Kruskal.Edge> edges;
        public final List<List<Prim.Edge>> adjacency;

        public GraphData(String name, int vertices, List<Kruskal.Edge> edges, List<List<Prim.Edge>> adjacency) {
            this.name = name;
            this.vertices = vertices;
            this.edges = edges;
            this.adjacency = adjacency;
        }
    }
}
