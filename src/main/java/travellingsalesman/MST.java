package travellingsalesman;

import java.util.*;


public class MST {
	 public static Graph computeMinimumWeightSpanningTree(Graph graph) {
	        // Use Kruskal's algorithm to compute minimum weight spanning tree
	        UnionFind uf = new UnionFind(graph.getNumVertices());

	        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
	        Collections.sort(sortedEdges);


	        Graph mst = new Graph();
	        Map<String, Integer> nameToIndexMap = new HashMap<>();
	        int index = 0;
	        for (Edge edge : sortedEdges) {
	            String uName = edge.getU().getId();
	            String vName = edge.getV().getId();
	            int uIndex, vIndex;

	            if (nameToIndexMap.containsKey(uName)) {
	                uIndex = nameToIndexMap.get(uName);
	            } else {
	                uIndex = index++;
	                nameToIndexMap.put(uName, uIndex);
	                mst.addVertex(edge.getU());
	            }

	            if (nameToIndexMap.containsKey(vName)) {
	                vIndex = nameToIndexMap.get(vName);
	            } else {
	                vIndex = index++;
	                nameToIndexMap.put(vName, vIndex);
	                mst.addVertex(edge.getV());
	            }

	            if (uf.find(uIndex) != uf.find(vIndex)) {
	                uf.union(uIndex, vIndex);
	                mst.addEdge(mst.getVertex(uIndex), mst.getVertex(vIndex), edge.getWeight());
	            }
	        }

	        return mst;
	    }

	public static HashSet<Integer> findOddDegreeVertices(Graph mst,Graph graph) {
		HashSet<Integer> oddVertices = new HashSet<>();
		for (int i = 0; i < mst.getNumVertices(); i++) {
//            System.out.println("-------------------------------------------");
			Vertex v=mst.getVertex(i);
			int deg= mst.getDegree(v.getId());
			if (mst.getDegree(mst.getVertex(i).getId()) % 2 == 1) {
//                System.out.println("Degree " +i+" "+mst.getDegree(mst.getVertex(i).getId()));
				oddVertices.add(graph.getIndex(mst.getVertex(i).getId()));
			}
		}
		return oddVertices;
	}
}
