package algorithms;

import metrics.PerformanceTracker;
import java.util.*;

public class Prim {

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
        public long arrayAccesses;

        public Result(int totalCost, long timeMs, long comparisons, long arrayAccesses) {
            this.totalCost = totalCost;
            this.timeMs = timeMs;
            this.comparisons = comparisons;
            this.arrayAccesses = arrayAccesses;
        }
    }

    public static Result runPrim(List<List<Edge>> graph, int vertices, PerformanceTracker tracker) {
        long start = System.currentTimeMillis();

        boolean[] inMST = new boolean[vertices];
        int[] key = new int[vertices];
        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{0, 0});
        int totalCost = 0;

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            if (inMST[u]) continue;

            inMST[u] = true;
            totalCost += curr[1];

            for (Edge e : graph.get(u)) {
                tracker.incComparisons();
                if (!inMST[e.dest] && e.weight < key[e.dest]) {
                    key[e.dest] = e.weight;
                    pq.offer(new int[]{e.dest, e.weight});
                    tracker.incArrayAccesses(2);
                }
            }
        }

        long end = System.currentTimeMillis();
        return new Result(totalCost, end - start, tracker.getComparisons(), tracker.getArrayAccesses());
    }
}
