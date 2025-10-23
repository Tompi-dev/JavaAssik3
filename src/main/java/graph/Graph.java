package graph;

import java.util.*;

public class Graph {
    private final int vertices;
    private final List<Edge> edges;
    private final Map<Integer, List<Edge>> adj;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
        this.adj = new HashMap<>();

        // Initialize adjacency list
        for (int i = 0; i < vertices; i++) {
            adj.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int src, int dest, int weight) {
        Edge edge = new Edge(src, dest, weight);
        edges.add(edge);

        // Since the graph is undirected, add both directions
        adj.get(src).add(new Edge(src, dest, weight));
        adj.get(dest).add(new Edge(dest, src, weight));
    }

    public int getVertices() { return vertices; }
    public List<Edge> getEdges() { return edges; }
    public List<Edge> getAdj(int v) { return adj.get(v); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Graph:\n");
        for (int i = 0; i < vertices; i++) {
            sb.append(i).append(" → ").append(adj.get(i)).append("\n");
        }
        return sb.toString();
    }
}
