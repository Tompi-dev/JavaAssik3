package algorithms;

import metrics.PerformanceTracker;
import java.util.*;

public class Kruskal {
    public static class Edge {
        public int src;
        public int dest;
        public int weight;

        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return String.format("(%d -- %d, w=%d)", src, dest, weight);
        }
    }


    public static MSTResult runKruskal(int vertices, List<Edge> edges, PerformanceTracker tracker) {

        edges.sort(Comparator.comparingInt(e -> e.weight));

        List<Edge> mst = new ArrayList<>();
        UnionFind uf = new UnionFind(vertices, tracker);
        int totalCost = 0;

        for (Edge e : edges) {
            tracker.incComparisons();
            if (uf.union(e.src, e.dest)) {
                mst.add(e);
                totalCost += e.weight;
            }


            if (mst.size() == vertices - 1) break;
        }

        return new MSTResult(mst, totalCost, tracker);
    }


    public static class MSTResult {
        public List<Edge> mstEdges;
        public int totalCost;
        public PerformanceTracker tracker;

        public MSTResult(List<Edge> mstEdges, int totalCost, PerformanceTracker tracker) {
            this.mstEdges = mstEdges;
            this.totalCost = totalCost;
            this.tracker = tracker;
        }

        public void printSummary() {
            System.out.println("Kruskal MST Edges:");
            mstEdges.forEach(System.out::println);
            System.out.println("Total MST Cost: " + totalCost);
            System.out.println(tracker.report());
        }
    }
}
