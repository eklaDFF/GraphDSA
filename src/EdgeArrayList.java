import java.util.ArrayList;
public class EdgeArrayList {
    class EdgeArrayListNode {
        Vertex a,b;
        int weight;
        public EdgeArrayListNode(Vertex a,Vertex b,int weight){
            this.a = a;
            this.b = b;
            this.weight = weight;
        }
    }
    ArrayList<EdgeArrayListNode> edgeArrayList = new ArrayList<>();
    public void addEdge(Vertex s,Vertex d,int w){
        edgeArrayList.add(new EdgeArrayListNode(s,d,w));
    }
    public int getWeight(Vertex s,Vertex d){
        for (EdgeArrayListNode node : edgeArrayList){
            if ((node.a.name == s.name) && (node.b.name==d.name)){
                return node.weight;
            }
        }
        return -1;
    }
}
