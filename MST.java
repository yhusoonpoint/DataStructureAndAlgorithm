import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
public class MST
{
    static Graph getRandomGraph(int n)
    {
        Graph g = new MatrixGraph(n,false);
        Random weight = new Random();
        for(int x = 0; x < n; x++)
            for(int y = 0; y < n; y++)
                if (g.isValidEdge(x, y) && !g.isEdge(x, y))
                    g.addEdge(x, y, weight.nextDouble() * 10.0);
        return g;
    }
    static double getTotalEdgeWeight(Graph g)
    {
        double totalWeight = 0.0;
        for(int x = 0; x < g.numVertices(); x++)
            for(int y = 0; y < g.numVertices(); y++)
                if(g.isEdge(x,y))
                    totalWeight += g.weight(x,y);
        return g.isDirected() ? totalWeight : totalWeight / 2;
    }
    static boolean isConnected(Graph g)
    {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        int currentValue;
        boolean[] visited = new boolean[g.numVertices()];
        boolean[] queueCheck = new boolean[g.numVertices()];
        queue.add(0);
        queueCheck[0] = true;
        while (!queue.isEmpty() && !visited[currentValue = queue.poll()])
        {
            visited[currentValue] = true;
            for(int currentNeighbour : g.outNeighbours(currentValue))
                if(!visited[currentNeighbour] && !queueCheck[currentNeighbour])
                    queueCheck[currentNeighbour] = queue.add(currentNeighbour);
        }
        for(boolean eachVisit : visited)
            if(!eachVisit)
                return false;
        return true;
    }
    static void makeMST(Graph g)
    {
        List<Edge> edges = new ArrayList<>();
        boolean[][] addedEdge = new boolean[g.numVertices][g.numVertices];
        for(int x = 0; x < g.numVertices(); x++)
            for(int y = 0; y < g.numVertices(); y++)
                if(g.isEdge(x,y) && !addedEdge[x][y])
                    addedEdge[x][y] = addedEdge[y][x] = edges.add(new Edge(x, y, g.weight(x, y)));
        Collections.sort(edges);
        for (Edge edge : edges)
        {
            g.deleteEdge(edge.x, edge.y);
            if (!isConnected(g))
                g.addEdge(edge.x, edge.y, edge.w);
        }
    }
    public static void main(String[] args)
    {
        long startTime = System.currentTimeMillis();
        double totalOfAllGraph = 0.0;
        Graph g;
        g = GraphOfEssex.getGraph();
        makeMST(g);
        System.out.println("Exercise 4a.");
        System.out.println("Total weight of map of Essex: " + getTotalEdgeWeight(g));
        for(int i = 0; i < 20; i++)
        {
            g = getRandomGraph(100);
            makeMST(g);
            totalOfAllGraph += getTotalEdgeWeight(g);
        }
        System.out.println("\nExercise 4b.");
        System.out.println("Average weight of 20 random MST graph with 100 vertices is: " + (totalOfAllGraph / 20));
        System.out.println("\nElapsed time in seconds: "+ ((System.currentTimeMillis() - startTime) / 1000F));
    }
}
class Edge implements Comparable<Edge>
{
    int x, y;
    double w;
    Edge (int x, int y, double w)
    {
        this.x = x;  this.y = y;  this.w = w;
    }
    @Override
    public int compareTo (Edge that)
    {
        return Double.compare(that.w,this.w);
    }
}

