package travellingsalesman;

import org.junit.Assert;
import org.junit.Test;

public class TSPTest {

    @Test
    public void testComputeDistance() {
        Assert.assertEquals(8240, Graph.computeDistance(new Vertex("1", -0.009691, 51.483548,0.0,0.0), new Vertex("2", -0.118888, 51.513075,0.0,0.0)), 10);
    }
    @Test
    public void testMST01() {
        Graph graph = new Graph();
        graph.addVertex(new Vertex("0",0,0,0,0));
        graph.addVertex(new Vertex("1",0,0,0,0));
        graph.addVertex(new Vertex("2",0,0,0,0));
        graph.addVertex(new Vertex("3",0,0,0,0));
        graph.addVertex(new Vertex("4",0,0,0,0));
        graph.addEdge(graph.getVertex(0),graph.getVertex(1),1);
        graph.addEdge(graph.getVertex(0),graph.getVertex(2),1);
        graph.addEdge(graph.getVertex(0),graph.getVertex(3),1);
        graph.addEdge(graph.getVertex(0),graph.getVertex(4),1);
        graph.addEdge(graph.getVertex(4),graph.getVertex(1),1);
        graph.addEdge(graph.getVertex(1),graph.getVertex(2),1);
        graph.addEdge(graph.getVertex(2),graph.getVertex(3),1);
        graph.addEdge(graph.getVertex(3),graph.getVertex(4),1);
        graph.addEdge(graph.getVertex(1),graph.getVertex(3),2);
        graph.addEdge(graph.getVertex(2),graph.getVertex(4),2);
        Assert.assertEquals(4, MST.computeMinimumWeightSpanningTree(graph,null).getWeight(),0);

    }
}
