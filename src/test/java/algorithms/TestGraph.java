package cli;

import graph.Graph;

public class TestGraph {
    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 10);
        g.addEdge(0, 2, 5);
        g.addEdge(1, 3, 7);
        g.addEdge(3, 4, 2);

        System.out.println(g);
    }
}
