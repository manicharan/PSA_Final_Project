package travellingsalesman;

import org.junit.Assert;
import org.junit.Test;

import travellingsalesman.MST.MST;
import travellingsalesman.MST.UnionFind;
import travellingsalesman.Optimizations.TacticalOptimizations;
import travellingsalesman.TSP.Eulerian;
import travellingsalesman.TSP.TSP;
import travellingsalesman.graph.Graph;
import travellingsalesman.graph.Vertex;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TSPTest {
    Vertex v0, v1, v2, v3, v4, v5, v6, v7, v8, v9;

    public TSPTest() {
        v0 = new Vertex("0", -0.009691, 51.483548);
        v1 = new Vertex("1", -0.118888, 51.513075);
        v2 = new Vertex("2", 0.076327, 51.540042);
        v3 = new Vertex("3", -0.418139, 51.500839);
        v4 = new Vertex("4", -0.134987, 51.46327);
        v5 = new Vertex("5", 0.063946, 51.492689);
        v6 = new Vertex("6", -0.198751, 51.542493);
        v7 = new Vertex("7", 0.01742, 51.49214);
        v8 = new Vertex("8", 0.108427, 51.575913);
        v9 = new Vertex("9", -0.097732, 51.559728);
    }

    @Test
    public void testComputeDistance() {
        assertEquals(8241, Graph.computeDistance(v0, v1), 1);
        assertEquals(8241, Graph.computeDistance(v1, v0), 1);
        assertEquals(2106, Graph.computeDistance(v0, v7), 1);
        assertEquals(3221, Graph.computeDistance(v7, v5), 1);
        assertEquals(5334, Graph.computeDistance(v5, v2), 1);
        assertEquals(4564, Graph.computeDistance(v2, v8), 1);
        assertEquals(15869, Graph.computeDistance(v3, v6), 1);
        assertEquals(6420, Graph.computeDistance(v6, v1), 1);
        assertEquals(5389, Graph.computeDistance(v1, v9), 1);
        assertEquals(5649, Graph.computeDistance(v1, v4), 1);
        assertEquals(0, Graph.computeDistance(v1, v1), 0);
    }

    // Test for MST graph and it's included methods
    @Test
    public void testMST01() {
        Graph graph = new Graph();
        Vertex v00 = new Vertex("0");
        Vertex v01 = new Vertex("1");
        Vertex v02 = new Vertex("2");
        Vertex v03 = new Vertex("3");
        Vertex v04 = new Vertex("4");
        graph.addVertex(v00);
        graph.addVertex(v01);
        graph.addVertex(v02);
        graph.addVertex(v03);
        graph.addVertex(v04);
        graph.addEdge(v00, v01, 1);
        graph.addEdge(v00, v02, 1);
        graph.addEdge(v00, v03, 1);
        graph.addEdge(v00, v04, 1);
        graph.addEdge(v01, v02, 1);
        graph.addEdge(v02, v03, 1);
        graph.addEdge(v03, v04, 1);
        graph.addEdge(v04, v01, 1);
        graph.addEdge(v01, v03, 2);
        graph.addEdge(v02, v04, 2);
        Graph mst = MST.computeMinimumWeightSpanningTree(graph, null);
        assertEquals(5, graph.getNumVertices(), 0);
        assertEquals(10, graph.getNumEdges(), 0);
        assertEquals(3, graph.getIndex("3"), 0);
        assertEquals(4, graph.getDegree("0"), 0);
        assertEquals(4, mst.getWeight(), 0);
        assertEquals(0, MST.findOddDegreeVertices(mst, graph).size() % 2, 0);
    }

    @Test
    public void testMST02() {
        Graph graph = new Graph();
        Vertex v00 = new Vertex("0");
        Vertex v01 = new Vertex("1");
        Vertex v02 = new Vertex("2");
        Vertex v03 = new Vertex("3");
        Vertex v04 = new Vertex("4");
        graph.addVertex(v00);
        graph.addVertex(v01);
        graph.addVertex(v02);
        graph.addVertex(v03);
        graph.addVertex(v04);
        graph.addEdge(v00, v01, 345);
        graph.addEdge(v00, v02, 874);
        graph.addEdge(v00, v03, 238);
        graph.addEdge(v00, v04, 192);
        graph.addEdge(v01, v02, 958);
        graph.addEdge(v02, v03, 218);
        graph.addEdge(v03, v04, 753);
        graph.addEdge(v04, v01, 139);
        graph.addEdge(v01, v03, 534);
        graph.addEdge(v02, v04, 238);
        Graph mst = MST.computeMinimumWeightSpanningTree(graph, null);
        assertNotEquals(1, MST.findOddDegreeVertices(mst, graph).size() % 2, 0);
        assertEquals(787, mst.getWeight(), 0);
    }

    @Test
    public void testMST03() {
        Graph graph = new Graph();
        graph.addVertex(v0);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addEdge(v0, v1, Graph.computeDistance(v0, v1));
        graph.addEdge(v0, v2, Graph.computeDistance(v0, v2));
        graph.addEdge(v0, v3, Graph.computeDistance(v0, v3));
        graph.addEdge(v0, v4, Graph.computeDistance(v0, v4));
        graph.addEdge(v1, v2, Graph.computeDistance(v1, v2));
        graph.addEdge(v1, v3, Graph.computeDistance(v1, v3));
        graph.addEdge(v1, v4, Graph.computeDistance(v1, v4));
        graph.addEdge(v2, v3, Graph.computeDistance(v2, v3));
        graph.addEdge(v2, v4, Graph.computeDistance(v2, v4));
        graph.addEdge(v3, v4, Graph.computeDistance(v3, v4));
        Graph mst = MST.computeMinimumWeightSpanningTree(graph, null);
        assertEquals(5, graph.getNumVertices(), 0);
        assertEquals(10, graph.getNumEdges(), 0);
        assertEquals(3, graph.getIndex("3"), 0);
        assertEquals(4, graph.getDegree("0"), 0);
        assertEquals(42592, mst.getWeight(), 1);
        assertEquals(0, MST.findOddDegreeVertices(mst, graph).size() % 2, 0);
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

    @Test
    public void testTSP01() {
        Graph graph = new Graph();
        graph.addVertex(v0);
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addEdge(v0, v1, Graph.computeDistance(v0, v1));
        graph.addEdge(v0, v2, Graph.computeDistance(v0, v2));
        graph.addEdge(v0, v3, Graph.computeDistance(v0, v3));
        graph.addEdge(v0, v4, Graph.computeDistance(v0, v4));
        graph.addEdge(v1, v2, Graph.computeDistance(v1, v2));
        graph.addEdge(v1, v3, Graph.computeDistance(v1, v3));
        graph.addEdge(v1, v4, Graph.computeDistance(v1, v4));
        graph.addEdge(v2, v3, Graph.computeDistance(v2, v3));
        graph.addEdge(v2, v4, Graph.computeDistance(v2, v4));
        graph.addEdge(v3, v4, Graph.computeDistance(v3, v4));
        Graph mst = MST.computeMinimumWeightSpanningTree(graph, null);
        Graph matching = MST.findMinimumWeightPerfectMatching(MST.findOddDegreeVertices(mst, graph), graph);
        Graph eulerian = Eulerian.combineGraphs(mst, matching, graph);
        List<Integer> eulerianPath = Eulerian.findEulerianCycle(eulerian);
        double distance = TSP.calculateDistance(eulerianPath, eulerian);
        assertEquals(5, graph.getNumVertices(), 0);
        assertEquals(10, graph.getNumEdges(), 0);
        assertEquals(42592, mst.getWeight(), 1);
        assertEquals(2, MST.findOddDegreeVertices(mst, graph).size(), 0);
        assertEquals(5, eulerian.getNumEdges(), 0);
        assertEquals(5, eulerian.getNumVertices(), 0);
        assertEquals(6, eulerianPath.size(), 0);
        //Testing TSP tour distance for whi
        assertEquals(1,72527/distance, 0.25);
        assertEquals(1,72527/TacticalOptimizations.twoOpt(eulerianPath, distance, graph),0.10);
    }

}