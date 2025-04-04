import java.util.*;

public class DynamicKruskalsMST {
    static class Edge implements Comparable<Edge> {
        Object src, dest;
        int weight;

        public Edge(Object src, Object dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
    }

    static class Subset {
        int parent, rank;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Minimum Spanning Tree using Kruskal's Algorithm (Supports Integers and Characters)");

        Set<Object> nodes = new HashSet<>();
        List<Edge> edges = new ArrayList<>();

        while (true) {
            System.out.println("\n--- Add New Edge ---");

            // Get source node
            System.out.print("Enter source node (integer or single character): ");
            Object src = parseNode(scanner.next());

            // Get destination node
            System.out.print("Enter destination node (integer or single character): ");
            Object dest = parseNode(scanner.next());

            // Get weight
            System.out.print("Enter weight (integer): ");
            int weight = scanner.nextInt();

            edges.add(new Edge(src, dest, weight));
            nodes.add(src);
            nodes.add(dest);
            System.out.println("Edge added successfully!");

            System.out.print("\nAdd another edge? (Y/N): ");
            String choice = scanner.next().toUpperCase();
            if (!choice.equals("Y")) {
                break;
            }
        }

        // Create node mapping to indices
        List<Object> uniqueNodes = new ArrayList<>(nodes);
        int V = uniqueNodes.size();
        Map<Object, Integer> nodeToIndex = new HashMap<>();
        for (int i = 0; i < V; i++) {
            nodeToIndex.put(uniqueNodes.get(i), i);
        }

        // Build adjacency matrix
        int[][] adjacencyMatrix = new int[V][V];
        for (Edge edge : edges) {
            int srcIndex = nodeToIndex.get(edge.src);
            int destIndex = nodeToIndex.get(edge.dest);
            adjacencyMatrix[srcIndex][destIndex] = edge.weight;
            adjacencyMatrix[destIndex][srcIndex] = edge.weight; // Undirected graph
        }

        // Display adjacency matrix
        System.out.println("\nAdjacency Matrix:");
        System.out.print("\t");
        for (Object node : uniqueNodes) {
            System.out.print(node + "\t");
        }
        System.out.println();

        for (int i = 0; i < V; i++) {
            System.out.print(uniqueNodes.get(i) + "\t");
            for (int j = 0; j < V; j++) {
                System.out.print(adjacencyMatrix[i][j] + "\t");
            }
            System.out.println();
        }

        // Compute MST using Kruskal's algorithm
        List<Edge> mst = kruskalMST(edges, nodeToIndex, V);

        // Display MST edges and calculate total cost
        System.out.println("\nEdges in the Minimum Spanning Tree:");
        int totalCost = 0;
        for (Edge edge : mst) {
            System.out.println(edge.src + " -- " + edge.dest + " == " + edge.weight);
            totalCost += edge.weight;
        }
        System.out.println("Total minimum cost: " + totalCost);
    }

    private static Object parseNode(String input) {
        // Try to parse as integer first
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // If not an integer, take first character
            return input.charAt(0);
        }
    }

    public static List<Edge> kruskalMST(List<Edge> edges, Map<Object, Integer> nodeToIndex, int V) {
        List<Edge> result = new ArrayList<>();
        Collections.sort(edges);

        Subset[] subsets = new Subset[V];
        for (int i = 0; i < V; i++) {
            subsets[i] = new Subset();
            subsets[i].parent = i;
            subsets[i].rank = 0;
        }

        int e = 0;
        int i = 0;
        while (e < V - 1 && i < edges.size()) {
            Edge nextEdge = edges.get(i++);
            int x = find(subsets, nodeToIndex.get(nextEdge.src));
            int y = find(subsets, nodeToIndex.get(nextEdge.dest));

            if (x != y) {
                result.add(nextEdge);
                union(subsets, x, y);
                e++;
            }
        }

        return result;
    }

    private static int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    private static void union(Subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank) {
            subsets[xroot].parent = yroot;
        } else if (subsets[xroot].rank > subsets[yroot].rank) {
            subsets[yroot].parent = xroot;
        } else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }
}