package algorithms;

import metrics.PerformanceTracker;


public class UnionFind {
    private final int[] parent;
    private final int[] rank;
    private final PerformanceTracker tracker;

    public UnionFind(int size, PerformanceTracker tracker) {
        this.parent = new int[size];
        this.rank = new int[size];
        this.tracker = tracker;

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }


    public int find(int x) {
        tracker.incComparisons();
        if (parent[x] != x) {
            parent[x] = find(parent[x]); // compress path
        }
        return parent[x];
    }


    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        tracker.incComparisons();
        if (rootX == rootY) return false;


        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }


        return true;
    }


    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}
