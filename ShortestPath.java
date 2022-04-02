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
		parseTransfers(transfers);
		parseStopTimes(stopTimes);

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
				
				
//			    	int originVertex = input.nextInt();
//			    	int destVertex = input.nextInt();
//			    	double weight = input.nextDouble(); 
//			    	ArrayList<Edge> list = new ArrayList<Edge>();
//			    	if (Vertices.containsKey(originVertex)){
//			    		list = (ArrayList<CompetitionDijkstra.Edge>) 
//			    				Vertices.get(originVertex);
//			    	}
//			 
//			    	Edge workingEdge = new Edge(destVertex, weight);
//			    	list.add(workingEdge);
//			    	
//			    	Vertices.put(originVertex, list);
//			    	
//			    	}		
//				}
		
				// If stopId has negative numbers try change this
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
		ShortestPath SP = new ShortestPath("src/stops.txt","transfers.txt", "src/stop_times.txt");
		

	}
}
