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

        @Override
        public String toString() {
            return String.format("(%d -- %d, w=%d)", src, dest, weight);
        }
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
            System.out.println("Prim MST Edges:");
            mstEdges.forEach(System.out::println);
            System.out.println("Total MST Cost: " + totalCost);
            System.out.println(tracker.report());
        }
    }

    public static MSTResult runPrim(int vertices, List<List<Edge>> adj, PerformanceTracker tracker) {
        boolean[] visited = new boolean[vertices];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        List<Edge> mst = new ArrayList<>();
        int totalCost = 0;


        visited[0] = true;
        tracker.incArrayAccesses(1);


        for (Edge e : adj.get(0)) {
            pq.offer(e);
            tracker.incArrayAccesses(1);
        }

        while (!pq.isEmpty() && mst.size() < vertices - 1) {
            Edge e = pq.poll();
            tracker.incArrayAccesses(1);
            int v = e.dest;

            tracker.incComparisons();
            if (visited[v]) continue;


            visited[v] = true;
            tracker.incArrayAccesses(1);
            mst.add(e);
            totalCost += e.weight;


            for (Edge next : adj.get(v)) {
                tracker.incComparisons();
                if (!visited[next.dest]) {
                    pq.offer(next);
                    tracker.incArrayAccesses(1);
                }
            }
        }

        return new MSTResult(mst, totalCost, tracker);
    }
}
