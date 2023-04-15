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

	public static Graph findMinimumWeightPerfectMatching(HashSet<Integer> oddVertices,Graph graph) {
		Graph matching = new Graph();

        // If there are no odd degree vertices, return an empty matching
        if (oddVertices.isEmpty()) {
            return matching;
        }

        // Create a subgraph containing only the odd degree vertices and their edges
        Graph oddGraph = new Graph();
        for(int v:oddVertices){
            oddGraph.addVertex(graph.getVertex(v));
            matching.addVertex(graph.getVertex(v));
        }
        for(Edge e: graph.getEdges()){
            if(oddVertices.contains(graph.getIndex(e.getU().getId())) &&
                    oddVertices.contains(graph.getIndex(e.getV().getId())))
                oddGraph.addEdge(e);
        }
        System.out.println(oddGraph.getNumEdges() +" "+oddGraph.getNumVertices());
        for(Edge e:oddGraph.getEdges()){
//            System.out.println(e);
        }
        List<Edge> oddEdges = oddGraph.getEdges();
        Collections.sort(oddEdges);
        HashSet<Vertex> oddVer = new HashSet<Vertex>();
        for(Vertex v: oddGraph.getVertices()){
            oddVer.add(v);
        }
        for(Edge e:oddEdges){
            if(oddVer.contains(e.getU()) && oddVer.contains(e.getV())){
                matching.addEdge(e);
                oddVer.remove(e.getU());
                oddVer.remove(e.getV());
            }
        }
        return matching;
	}
}
