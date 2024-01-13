import java.util.ArrayList;
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

        System.out.println("--------------------------Bellman--------------------- Example 1 below");

        Vertex A1 = new Vertex('0');
        Vertex B1 = new Vertex('1');
        Vertex C1 = new Vertex('2');

        A1.adjacentVertices.add(B1);
        B1.adjacentVertices.add(C1);
        C1.adjacentVertices.add(A1);

        EdgeArrayList edgeArrayList1 = new EdgeArrayList();
        edgeArrayList1.addEdge(A1,B1,2);
        edgeArrayList1.addEdge(B1,C1,1);
        edgeArrayList1.addEdge(C1,A1,-4);

        Bellman_Ford(A1,C1,edgeArrayList1);


        System.out.println("--------------------------Bellman--------------------- Example 2 below");
        Vertex A2 = new Vertex('1');
        Vertex B2 = new Vertex('2');
        Vertex C2 = new Vertex('3');
        Vertex D2 = new Vertex('4');
        Vertex E2 = new Vertex('5');

        A2.adjacentVertices.add(B2);
        A2.adjacentVertices.add(C2);
        B2.adjacentVertices.add(D2);
        C2.adjacentVertices.add(B2);
        C2.adjacentVertices.add(D2);
        C2.adjacentVertices.add(E2);
        D2.adjacentVertices.add(E2);

        EdgeArrayList edgeArrayList2 = new EdgeArrayList();
        edgeArrayList2.addEdge(A2,B2,5);
        edgeArrayList2.addEdge(A2,C2,-1);
        edgeArrayList2.addEdge(B2,D2,-3);
        edgeArrayList2.addEdge(C2,B2,-2);
        edgeArrayList2.addEdge(C2,D2,6);
        edgeArrayList2.addEdge(D2,E2,2);
        edgeArrayList2.addEdge(C2,E2,3);

        Bellman_Ford(A2,E2,edgeArrayList2);


        System.out.println("--------------------------Prims Spanning Tree--------------------- Example below");

        Vertex A3 = new Vertex('A');
        Vertex B3 = new Vertex('B');
        Vertex C3 = new Vertex('C');
        Vertex D3 = new Vertex('D');
        Vertex E3 = new Vertex('E');

        EdgeArrayList edgeArrayList3 = new EdgeArrayList();
        edgeArrayList3.addEdge(A3,B3,5);
        edgeArrayList3.addEdge(B3,A3,5);
        edgeArrayList3.addEdge(A3,C3,-1);
        edgeArrayList3.addEdge(C3,A3,-1);
        edgeArrayList3.addEdge(B3,C3,-2);
        edgeArrayList3.addEdge(C3,B3,-2);
        edgeArrayList3.addEdge(B3,D3,-3);
        edgeArrayList3.addEdge(D3,B3,-3);
        edgeArrayList3.addEdge(C3,D3,6);
        edgeArrayList3.addEdge(D3,C3,6);
        edgeArrayList3.addEdge(C3,E3,3);
        edgeArrayList3.addEdge(E3,C3,3);
        edgeArrayList3.addEdge(D3,E3,2);
        edgeArrayList3.addEdge(E3,D3,2);

        SpanningTreeByPrims(A3,5,edgeArrayList3);

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

    public static void Bellman_Ford(Vertex source,Vertex destination,EdgeArrayList edgeArrayList){
        source.pathDistanceFromSourceVertex = 0;
        source.pathAdjacentSourceVertex = source;

        // Relaxing all the edges for (v-1)times. v is no of vertices
        for (int i=1;i<edgeArrayList.edgeArrayList.size();i++){
            for (var edge : edgeArrayList.edgeArrayList){
                Vertex s = edge.a;
                Vertex d = edge.b;
                if (d.pathDistanceFromSourceVertex > (s.pathDistanceFromSourceVertex + edge.weight)){
                    d.pathDistanceFromSourceVertex = (s.pathDistanceFromSourceVertex + edge.weight);
                    d.pathAdjacentSourceVertex = s;
                }
            }
        }

        // Relaxing one for time to check whether the graph contains -ve cycle
        for (var edge : edgeArrayList.edgeArrayList){
            Vertex s = edge.a;
            Vertex d = edge.b;
            if (d.pathDistanceFromSourceVertex > (s.pathDistanceFromSourceVertex + edge.weight)){
                System.out.println("This graph contains -ve cycle");
                return;
            }
        }

        // If the graph passes -ve cycle check
        Vertex c = destination;
        StringBuilder path = new StringBuilder(c.name + "(Cost to reach here " + c.pathDistanceFromSourceVertex + ") <- ");
        c = c.pathAdjacentSourceVertex;
        while (c!=source){
            path.append(c.name).append(" <- ");
            c = c.pathAdjacentSourceVertex;
        }
        path.append(c.name);
        System.out.println(path);
    }

    public static void SpanningTreeByPrims(Vertex root,int vertices, EdgeArrayList edgeArrayList){
        PriorityQueue<EdgeArrayList.EdgeArrayListNode> priorityQueue = new PriorityQueue<>(new Comparator<EdgeArrayList.EdgeArrayListNode>(){

            @Override
            public int compare(EdgeArrayList.EdgeArrayListNode o1, EdgeArrayList.EdgeArrayListNode o2) {
                return o1.weight-o2.weight;
            }
        });

        // Spanning Tree Edges
        ArrayList<EdgeArrayList.EdgeArrayListNode> spanningTreeEdges = new ArrayList<>();

        priorityQueue.addAll(getPathsFrom(root,edgeArrayList));
        root.explored = true;
        int vertexExplored = 1;
        while (vertexExplored < vertices){  // As the vertexExplored becomes vertexExplored==vertices, exploration will be stopped
            EdgeArrayList.EdgeArrayListNode c = priorityQueue.remove();
            Vertex edgeEndVertex = c.b;
            if (!edgeEndVertex.explored){
                priorityQueue.addAll(getPathsFrom(edgeEndVertex,edgeArrayList));
                edgeEndVertex.explored = true;
                vertexExplored++;

                // Adding Edge and building spanning tree
                spanningTreeEdges.add(c);
            }
        }

        // Printing Spanning Tree
        for (EdgeArrayList.EdgeArrayListNode edge : spanningTreeEdges){
            System.out.println(edge.a.name + ", " + edge.b.name + ", " + edge.weight);
        }
    }
    public static ArrayList<EdgeArrayList.EdgeArrayListNode> getPathsFrom(Vertex root, EdgeArrayList edgeArrayList){
        ArrayList<EdgeArrayList.EdgeArrayListNode> list = new ArrayList<>();
        for (EdgeArrayList.EdgeArrayListNode edge : edgeArrayList.edgeArrayList){
            if (edge.a.equals(root)){
                list.add(edge);
            }
        }
        return list;
    }
}