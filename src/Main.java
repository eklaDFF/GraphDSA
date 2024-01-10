import java.util.Comparator;
import java.util.PriorityQueue;
public class Main {
    public static void main(String[] args) {
        Vertex A = new Vertex('A');
        Vertex B = new Vertex('B');
        Vertex C = new Vertex('C');
        Vertex D = new Vertex('D');
        Vertex E = new Vertex('E');

        A.adjacentVertices.add(B);
        A.adjacentVertices.add(C);
        B.adjacentVertices.add(E);
        C.adjacentVertices.add(B);
        C.adjacentVertices.add(D);
        D.adjacentVertices.add(E);

        EdgeArrayList edgeArrayList = new EdgeArrayList();
        edgeArrayList.addEdge(A,B,4);
        edgeArrayList.addEdge(A,C,1);
        edgeArrayList.addEdge(B,E,4);
        edgeArrayList.addEdge(C,B,2);
        edgeArrayList.addEdge(C,D,4);
        edgeArrayList.addEdge(D,E,4);

        DijkstraAlgo(A,E,edgeArrayList);
    }
    public static void DijkstraAlgo(Vertex source,Vertex destination,EdgeArrayList edgeArrayList){
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.pathDistanceFromSourceVertex));
        source.pathAdjacentSourceVertex = source;
        source.pathDistanceFromSourceVertex = 0;
        priorityQueue.add(source);
        while(!priorityQueue.isEmpty()){
            Vertex c = priorityQueue.remove();
            if (c.explored){continue;}
            for (Vertex adj : c.adjacentVertices){
                if (adj.explored){continue;}
                if (adj.pathDistanceFromSourceVertex>(c.pathDistanceFromSourceVertex + edgeArrayList.getWeight(c,adj))){
                    adj.pathDistanceFromSourceVertex = c.pathDistanceFromSourceVertex + edgeArrayList.getWeight(c,adj);
                    adj.pathAdjacentSourceVertex = c;
                }
                priorityQueue.add(adj);
            }
            c.explored = true;
        }

        // Printing the Shortest Path
        Vertex c = destination;
        StringBuilder path = new StringBuilder(c.name + "(Cost to reach here " + destination.pathDistanceFromSourceVertex +") <- ");
        c = c.pathAdjacentSourceVertex;
        while (c.name != source.name){
            path.append(c.name).append(" <- ");
            c = c.pathAdjacentSourceVertex;
        }
        path.append(c.name);
        System.out.println(path);
    }
}