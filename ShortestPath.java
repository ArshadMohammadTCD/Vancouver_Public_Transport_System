import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



// https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm for dijkstras algorithm
// https://www.youtube.com/watch?v=dUCkwBpg1qM
//https://www.javainterviewpoint.com/iterate-through-hashmap/


public class ShortestPath {



	public class Edge{
		double weight;
		int	destVertex;
		Edge(int destVertex, double weight){
			this.destVertex = destVertex;
			this.weight = weight;
		}

		public void toPrint() {
			System.out.println( destVertex + " -> " + weight);
		}
	}

	HashMap<Integer, List<Edge>> Vertices = new HashMap<Integer, List<Edge>>();

	ShortestPath(String stops, String transfers, String stopTimes)
	{	
		parseStops(stops);
		parseStopTimes(stopTimes);
		parseTransfers(transfers);

	}

	HashMap<Integer, Boolean> Finished = new HashMap<Integer, Boolean>();
	HashMap<Integer, Integer> IndexMap = new HashMap<Integer, Integer>();
	
	
	int min_distance(int[][] dist, ArrayList<Integer> checked) {
		double min = Integer.MAX_VALUE;
		// possible problem to note when debugging	
		int index = 0;
		//    	for(int i = 0; i < Vertices.size(); i++) {
	
		for(int i = 0; i < checked.size(); i++) {
    		if(dist[0][i] <= min && checked.contains(i) == true) {
    			min = dist[0][i];
    			index = i;
    		}
    	}
			

		return index;
	}	


	public void dijkstra(int src) 
	{

		int dist[][] = new int[2][Vertices.size()];
		int prev[] = new int[Vertices.size()]; 
		ArrayList<Integer> checked = new ArrayList();


		// Initialising distance array and previous stopId array
		Iterator stopIdIterator = Vertices.keySet().iterator();
		int count = 0;


		//dist[v] ← INFINITY                 
		//prev[v] ← UNDEFINED 
		while(stopIdIterator.hasNext()) {
			int stopId = (int) stopIdIterator.next();
			if (stopId == src) {
				dist[0][count] = 0;
			}
			else {
				dist[0][count] = Integer.MAX_VALUE;
			}
			
			dist[1][count] = stopId;
			prev[count] = -1;	
			
			IndexMap.put(stopId, count);
			checked.add(count);
			count++;
			// Add v to Q
			
			
		}
		int sizeOfVertices = checked.size();
		for (int i = 0; i < sizeOfVertices-1; i++)  {
			int u = min_distance(dist, checked);
			checked.remove(u);
		
			List<Edge> workingEdges = Vertices.get(dist[1][u]);
			for(int j =0; j < workingEdges.size(); j++) {
//				if (checked.contains(IndexMap.get(workingEdges.get(j).destVertex))){
					int v = workingEdges.get(j).destVertex;
					int alt = dist[0][u] + (int)workingEdges.get(j).weight;
					// Problem is V not having the right index
					int indexV = IndexMap.get(v);
					if(alt < dist[0][indexV]) {
						dist[0][indexV] = alt;
						System.out.println("Distance from " + src + " to " + v + " is " + alt);
//					}
				}
			}
		}
	}  

	//    public double[] dijkstra(int src){ 😎
	//    	Iterator vertexIterator = Vertices.keySet().iterator();
	//	    while(vertexIterator.hasNext()) {
	//	    	int stopId = (int) vertexIterator.next();
	//	    	Finished.put(stopId, false);
	//	    	System.out.println(stopId);
	//	    	dist[i] = Integer.MAX_VALUE;
	//	    }
	//    	
	//    	
	//	    
	//	    
	//    	
	//    	for (int i = 0; i < Vertices.size(); i++) {
	//    		
	//    	}
	//    	
	//    	
	//    	
	//    	
	//    	for (int i = 0; i < Vertices.size()-1; i++) {
	//    		int u = min_distance(dist, Finished);
	//    		Finished.remove(u);
	//    		Finished.put(u, true);
	//    		List<Edge> workingEdges = Vertices.get(u);
	//    		
	//    		
	//    		
	//    		if (workingEdges != null) {
	////    			for(int j =0; j < workingEdges.size(); j++) {
	//    		    int j = 0;
	//    			while(vertexIterator.hasNext()) {	
	//    		    	int stopId = (int) vertexIterator.next();
	//    		    	
	//        			if(!Finished.get(workingEdges.get(j).destVertex)) {
	//        				int v = workingEdges.get(j).destVertex;
	//        				double alt = dist[u] + workingEdges.get(j).weight;
	//        				if (alt < dist[v]) {
	//        					dist[v] = alt; 
	//        				}
	//        			}
	//        			j++;
	//        		}
	//    		}	
	//    	}
	//    	return dist;	
//	private int findItemInDist(int dist[][], int item)
//	{
//		for(int i = 0; i < Vertices.size(); i++) {
//			if (dist[0][i] == item) {
//				return dist[1][i];
//			}
//			
//		}
//		System.out.println("Something went wrong in findItemInDist function: ShortestPath Class");
//		return item;
//		
//		
//		
//	}
	private void parseStopTimes(String stopTimes) {
		if (stopTimes == null) {
			return;
		}
		File a = new File(stopTimes);
		if (a.length() != 0){
			Scanner input = null;
			try {
				input = new Scanner(a);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int prevTripId = -1;
			int prevNodeName = -1;
			String firstLine = input.nextLine();
			System.out.println(firstLine);
			while (input.hasNextLine()) {
				String times = input.nextLine();
				String[] data = times.split(",");
				int tripId = Integer.parseInt(data[0]);
				int nodeName = Integer.parseInt(data[3]);

				if (tripId == prevTripId && prevTripId != -1) {
					ArrayList<Edge> list = new ArrayList<Edge>(); 

					if (Vertices.containsKey(prevNodeName)) {
						list = (ArrayList<Edge>)Vertices.get(prevNodeName);
					}
					Edge workingEdge = new Edge(nodeName, 1);
					list.add(workingEdge);
					Vertices.put(prevNodeName, list);
					System.out.print(prevNodeName + "->");
					workingEdge.toPrint();
				}		
				prevNodeName = nodeName;
				prevTripId = tripId;
			}	
		}	
	}
	private void parseTransfers(String transfers) {
		if (transfers == null) {
			return;
		}
		File a = new File(transfers);
		if (a.length() != 0){
			Scanner input = null;
			try {
				input = new Scanner(a);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String firstLine = input.nextLine();
//			System.out.println(firstLine);
			while (input.hasNextLine()) {
				String times = input.nextLine();
				String[] data = times.split(",");

				double weight;
				int originVertex = Integer.parseInt(data[0]);
				int destVertex = Integer.parseInt(data[1]);
				if (Integer.parseInt(data[2]) == 0) {
					weight = 2; 
				}
				else {
					weight = (Integer.parseInt(data[3])/100);
				}

				// If stopId has negative numbers try change this

				ArrayList<Edge> list = new ArrayList<Edge>(); 

				if (Vertices.containsKey(originVertex)) {
					list = (ArrayList<Edge>)Vertices.get(originVertex);
				}

				Edge workingEdge = new Edge(destVertex, weight);

				list.add(workingEdge);
				Vertices.put(originVertex, list);
//				System.out.print(originVertex + "->");
//				workingEdge.toPrint();


			}	
		}	
	}
	private void parseStops(String stops) 
	{
		int numOfVertices = 0 ;
		if (stops == null) {
			return;
		}
		File a = new File(stops);
		if (a.length() != 0){
			Scanner input = null;
			try {
				input = new Scanner(a);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

	public static void main(String[] args) {


		ShortestPath SP = new ShortestPath("src/stops.txt","", "src/testing_files_stop_times.txt");
		SP.dijkstra(646);


		//		System.out.println(dijkstra[5]);

	}
}
