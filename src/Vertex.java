import java.util.ArrayList;
public class Vertex {
    char name;
    boolean explored;
    int pathDistanceFromSourceVertex;
    ArrayList<Vertex> adjacentVertices;
    Vertex pathAdjacentSourceVertex;
    public Vertex (char name){
        this.name = name;
        explored = false;
        pathDistanceFromSourceVertex = Integer.MAX_VALUE;
        adjacentVertices = new ArrayList<>();
        pathAdjacentSourceVertex = null;
    }
}
