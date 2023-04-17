package travellingsalesman;

import org.junit.Assert;
import org.junit.Test;

import travellingsalesman.MST.MST;
import travellingsalesman.MST.UnionFind;
import travellingsalesman.graph.Graph;
import travellingsalesman.graph.Vertex;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TSPTest {

    @Test
    public void testComputeDistance() {
        assertEquals(8240, Graph.computeDistance(new Vertex("1", -0.009691, 51.483548,0.0,0.0), new Vertex("2", -0.118888, 51.513075,0.0,0.0)), 10);
    }
    @Test
    public void testMST01() {
        Graph graph = new Graph();
        Vertex v0 = new Vertex("0");
        Vertex v1 = new Vertex("1");
        Vertex v2 = new Vertex("2");
        Vertex v3 = new Vertex("3");
        Vertex v4 = new Vertex("4");
        graph.addVertex(v0);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addEdge(v0,v1,1);
        graph.addEdge(v0,v2,1);
        graph.addEdge(v0,v3,1);
        graph.addEdge(v0,v4,1);
        graph.addEdge(v1,v2,1);
        graph.addEdge(v2,v3,1);
        graph.addEdge(v3,v4,1);
        graph.addEdge(v4,v1,1);
        graph.addEdge(v1,v3,2);
        graph.addEdge(v2,v4,2);
        assertEquals(4, MST.computeMinimumWeightSpanningTree(graph,null).getWeight(),0);

    }
    @Test
    public void testMST02(){
        Graph graph = new Graph();
        graph.addVertex(new Vertex("0",0,0,0,0));
        graph.addVertex(new Vertex("1",0,0,0,0));
        graph.addVertex(new Vertex("2",0,0,0,0));
        graph.addVertex(new Vertex("3",0,0,0,0));
        graph.addVertex(new Vertex("4",0,0,0,0));
        graph.addEdge(graph.getVertex(0),graph.getVertex(1),2);
        graph.addEdge(graph.getVertex(0),graph.getVertex(2),3);
        graph.addEdge(graph.getVertex(0),graph.getVertex(3),4657);
        graph.addEdge(graph.getVertex(0),graph.getVertex(4),45);
        graph.addEdge(graph.getVertex(4),graph.getVertex(1),6);
        graph.addEdge(graph.getVertex(1),graph.getVertex(2),3);
        graph.addEdge(graph.getVertex(2),graph.getVertex(3),1);
        graph.addEdge(graph.getVertex(3),graph.getVertex(4),123);
        graph.addEdge(graph.getVertex(1),graph.getVertex(3),56);
        graph.addEdge(graph.getVertex(2),graph.getVertex(4),456);
        assertEquals(12, MST.computeMinimumWeightSpanningTree(graph,null).getWeight(),0);

    }

    @Test
    public void TestUF01() {
        UnionFind uf = new UnionFind(7);
        uf.union(1, 2);
        uf.union(3, 4);
        uf.union(5, 6);
        uf.union(1, 4);
        uf.union(5, 2);
        uf.union(6, 1);
        assertEquals(uf.find(1), uf.find(2));
        assertEquals(uf.find(2), uf.find(4));
        assertEquals(uf.find(3), uf.find(4));
        assertEquals(uf.find(5), uf.find(6));
        assertEquals(uf.find(1), uf.find(4));
        assertEquals(uf.find(2), uf.find(5));
        assertEquals(uf.find(1), uf.find(6));
        assertEquals(uf.find(3), uf.find(5));
        assertEquals(uf.find(3), uf.find(6));
    }

    @Test
    public void TestUF02() {
        UnionFind uf = new UnionFind(7);
        uf.union(1, 2);
        uf.union(2, 3);
        uf.union(4, 5);
        uf.union(5, 6);
        uf.union(2, 5);
        assertTrue(uf.find(1) == uf.find(4));
        assertTrue(uf.find(0) != uf.find(6));
    }

}
