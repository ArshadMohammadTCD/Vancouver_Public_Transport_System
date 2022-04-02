import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;






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
    
    int min_distance(double[] dist, boolean[] finished) {
    	double min = Integer.MAX_VALUE;
    	// possible problem to note when debugging	
    	int index = 0;
    	for(int i = 0; i < Vertices.size(); i++) {
    		if(dist[i] <= min && finished[i] == false) {
    			min = dist[i];
    			index = i;
    		}
    	}
    	return index;
    }	
    
    
    
    private double dijkstra(int src){ 
    	boolean[] finished;
    	finished = new boolean[Vertices.size()];
	    
	    for (int i = 0; i < Vertices.size(); i++) {
	    	finished[i] = false;
 	    }
    	double[] dist;
    	dist = new double[Vertices.size()];
    	for (int i = 0; i < Vertices.size(); i++) {
    		dist[i] = Integer.MAX_VALUE;
    	}
    	dist[src] = 0;
    	
    	
    	
    	for (int i = 0; i < Vertices.size()-1; i++) {
    		int u = min_distance(dist, finished);
    		finished[u] = true;
    		List<Edge> workingEdges = Vertices.get(u);
    		
    		
    		
    		if (workingEdges != null) {
    			for(int j =0; j < workingEdges.size(); j++) {
        			if(!finished[workingEdges.get(j).destVertex]) {
        				int v = workingEdges.get(j).destVertex;
        				double alt = dist[u] + workingEdges.get(j).weight;
        				if (alt < dist[v]) {
        					dist[v] = alt; 
        				}
        			}
        		}
    		}	
    	}
    	return 0;
    }
	
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
				
				if (tripId == prevTripId || prevTripId == -1) {
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
			System.out.println(firstLine);
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
				System.out.print(originVertex + "->");
				workingEdge.toPrint();
					
				
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
		ShortestPath SP = new ShortestPath("src/stops.txt","src/transfers.txt", "src/stop_times.txt");
		

	}
}
