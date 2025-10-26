package algorithms;

import metrics.PerformanceTracker;
import java.util.*;

public class Kruskal {

    public static class Edge {
        public int src, dest, weight;
        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    public static class Result {
        public int totalCost;
        public long timeMs;
        public long comparisons;
        public long unions;

        public Result(int totalCost, long timeMs, long comparisons, long unions) {
            this.totalCost = totalCost;
            this.timeMs = timeMs;
            this.comparisons = comparisons;
            this.unions = unions;
        }
    }

    public static Result runKruskal(List<Edge> edges, int vertices, PerformanceTracker tracker) {
        long start = System.currentTimeMillis();

        edges.sort(Comparator.comparingInt(e -> e.weight));
        tracker.incComparisons();

        UnionFind uf = new UnionFind(vertices);
        int mstCost = 0;
        int edgeCount = 0;

        for (Edge e : edges) {
            int root1 = uf.find(e.src);
            int root2 = uf.find(e.dest);
            tracker.incComparisons();

            if (root1 != root2) {
                uf.union(root1, root2);
                tracker.incArrayAccesses(2);
                mstCost += e.weight;
                edgeCount++;
            }
            if (edgeCount == vertices - 1) break;
        }

        long end = System.currentTimeMillis();
        return new Result(mstCost, end - start, tracker.getComparisons(), uf.getUnionCount());
    }
}
