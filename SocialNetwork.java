import java.util.*;
import java.io.*;

public class SocialNetwork {

    private Map<String, List<String>> graph;

    public SocialNetwork() {
        graph = new HashMap<>();
    }

    public void addUser(String user) {
        graph.putIfAbsent(user, new ArrayList<>());
    }

    public void addFollow(String from, String to) {
        graph.get(from).add(to);
    }

    public void printNetwork(PrintWriter writer) {
        writer.println("NETWORK STRUCTURE:");
        for (String user : graph.keySet()) {
            writer.println(user + " -> " + graph.get(user));
        }
    }

    public void degreeCentrality(PrintWriter writer) {
        writer.println("\nDEGREE CENTRALITY:");
        for (String user : graph.keySet()) {
            writer.println(user + ": " + graph.get(user).size());
        }
    }

    public void shortestPath(String start, String end, PrintWriter writer) {

        Queue<String> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(end)) {
                break;
            }

            for (String neighbor : graph.get(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<String> path = new ArrayList<>();
        String step = end;

        while (step != null) {
            path.add(step);
            step = parent.get(step);
        }

        Collections.reverse(path);

        writer.println("\nSHORTEST PATH (" + start + " -> " + end + "):");
        writer.println(path);
    }

    public static void main(String[] args) throws IOException {

        SocialNetwork sn = new SocialNetwork();

        sn.addUser("Alice");
        sn.addUser("Bob");
        sn.addUser("Carol");
        sn.addUser("David");

        sn.addFollow("Alice", "Bob");
        sn.addFollow("Alice", "Carol");
        sn.addFollow("Bob", "David");
        sn.addFollow("Carol", "Bob");

        PrintWriter writer = new PrintWriter(new FileWriter("output.txt"));

        writer.println("THREADS SOCIAL NETWORK ANALYSIS\n");

        sn.printNetwork(writer);
        sn.degreeCentrality(writer);
        sn.shortestPath("Alice", "David", writer);

        writer.close();

        System.out.println("Analysis complete. Results saved to output.txt");
    }
}
