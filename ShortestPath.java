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

import javax.swing.JButton;
import javax.swing.JFrame;



//https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm for dijkstras algorithm
//https://www.youtube.com/watch?v=dUCkwBpg1qM
//https://www.javainterviewpoint.com/iterate-through-hashmap/
//https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/
//https://www.w3schools.com/java/java_user_input.asp

public class ShortestPath {

	HashMap<Integer, List<Edge>> Vertices = new HashMap<Integer, List<Edge>>();
	int NO_PARENT = -1;
	HashMap<Integer, Boolean> Finished = new HashMap<Integer, Boolean>();
	HashMap<Integer, Integer> IndexMap = new HashMap<Integer, Integer>();


	ShortestPath(String stops, String transfers, String stopTimes)
	{	
		parseStops(stops);
		System.out.print(".");
		parseStopTimes(stopTimes);
		System.out.print(".");
		parseTransfers(transfers);
		System.out.print(".\n");
	}

	int min_distance(int[][] dist, boolean checked[]) {
		double min = Integer.MAX_VALUE;
		int index = 0;
		for(int i = 0; i < Vertices.size(); i++) {
			if(dist[0][i] <= min && checked[i] == false) {
				min = dist[0][i];
				index = i;
			}
		}


		return index;
	}	

	int dist[][];
	int prev[];
	private int previousSrc = -1;

	public void dijkstra(int src, int target) 
	{
		if(Vertices.containsKey(src)) {
			if (Vertices.get(src).isEmpty()) {
				System.out.println(src + " is a drop off only point");
				return;
			}
			else if(Vertices.get(src).get(0).destVertex == src){
				System.out.println(src + " is a drop off only point");
				return;
			}

		}
		if(this.previousSrc != src) {
			this.previousSrc = src;
			this.dist = new int[2][Vertices.size()];
			this.prev = new int[Vertices.size()]; 
			boolean[] checked = new boolean[Vertices.size()+1];
			// Initialising distance array and previous stopId array
			Iterator<Integer>  stopIdIterator = Vertices.keySet().iterator();
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
				checked[count] = false;
				count++;
				// Add v to Q
			}
			int sizeOfVertices = Vertices.size();
			for (int i = 0; i < sizeOfVertices -1; i++)  {
				int u = min_distance(dist, checked);
				checked[u] = true;

				List<Edge> workingEdges = Vertices.get(dist[1][u]);
				for(int j =0; j < workingEdges.size(); j++) {

					int v = workingEdges.get(j).destVertex;

					if (!IndexMap.containsKey(v)) {
						System.out.println(v + " does not exist in the IndexMap");
					}
					else {
						if (!checked[IndexMap.get(v)] && IndexMap.containsKey(v)){

							int alt = dist[0][u] + (int)workingEdges.get(j).weight;
							int indexV = IndexMap.get(v);
							// I said --> If alt > 0 and I am not sure if this is correct.
							if(alt < dist[0][indexV] && alt > 0) {
								dist[0][indexV] = alt;
								prev[indexV] = u;
							}
						}
					}
				}
			}
		}
		if (!IndexMap.containsKey(target)) {
			System.out.println(target+" does not exist in this dataset");
			if(!IndexMap.containsKey(src)) {
				System.out.println(src+" does not exist in this dataset");
			}
		}
		else if(!IndexMap.containsKey(src)){
			System.out.println(src+" does not exist in this dataset");
			if (!IndexMap.containsKey(target)) {
				System.out.println(target+" does not exist in this dataset");
			}
		}
		else {
			int currentVertex = IndexMap.get(target);
			this.printPath(currentVertex, prev);
			if(printCount <= 1) {
				System.out.print("and "+src+" do not have a valid path");
			}
		}


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
			//			System.out.println(firstLine);
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
					//					System.out.print(prevNodeName + "->");
					//					workingEdge.toPrint();
				}
				else {
					ArrayList<Edge>list = new ArrayList<Edge>() ;
					if (Vertices.containsKey(prevNodeName)) {
						list = (ArrayList<Edge>)Vertices.get(prevNodeName);

					}
					Edge workingEdge = new Edge(prevNodeName, 0);
					list.add(workingEdge);
					Vertices.put(prevNodeName, list);
					//					System.out.print(prevNodeName + "->");
					//					workingEdge.toPrint();
				}

				prevNodeName = nodeName;
				prevTripId = tripId;
			}
			ArrayList<Edge> list = new ArrayList<Edge>(); 
			if (Vertices.containsKey(prevNodeName)) {
				list = (ArrayList<Edge>)Vertices.get(prevNodeName);

			}
			Edge workingEdge = new Edge(prevNodeName, 0);
			list.add(workingEdge);
			Vertices.put(prevNodeName, list);
			//			System.out.print(prevNodeName + "->");


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

	int printCount = 0;
	private void printPath(int currentVertex,
			int[] parents) {

		// Base case : Source node has
		// been processed
		if (currentVertex == -1)
		{
			return;
		}
		printPath(parents[currentVertex], parents);
		printCount++;
		System.out.print(dist[1][currentVertex] + " ");
	}

	public static void main(String[] args) {

		//		TST<Integer> st = new TST<Integer>();
		//		System.out.println("Its doing something!");
		////        for (int i = 0; !StdIn.isEmpty(); i++) {
		//////        	System.out.println("Its doing something2!");
		//        String word = "short";
		//        st.put("short", 0);
		//        st.put("shoe", 1); 
		//        st.put("shame", 1);
		//        
		//        
		//        System.out.println("keysWithPrefix(\"sh\")");
		//        for (String s : st.keysWithPrefix("sh"))
		//            System.out.println(s);
		//        
		//        
		//        System.out.println("keysWithPrefix(\"shor\")");
		//        for (String s : st.keysWithPrefix("shor"))
		//            System.out.println(s);
		//        
		//
		//        System.out.println("keysThatMatch(\"shoe\")");
		//        for (String s : st.keysThatMatch("shoe"))
		//            System.out.println(s);

		//		TSTSearch st = new TSTSearch("src/stops.txt");
		//		st.printStops("ST");
		//		
		//		TripSearch TS = new TripSearch("src/stop_times.txt");
		//		TS.returnStringOutput(" 5:25:00");

		Scanner readInput = new Scanner(System.in);  // Create a Scanner object
		boolean mainLoop = true;
		while(mainLoop) {
			System.out.println("Choose what you would like to do\n"
					+ "1 - Shortest Path between 2 stops\n"
					+ "2 - Search for a stop\n"
					+ "3 - Find a bus routes for a given arrival time\n"
					+ "0 - To quit");
			int input = -1;
			
			try {
				String input1 = readInput.next();
				input = Integer.parseInt(input1);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input, please input an Integer");
			}
			if(input == 1) {

				System.out.print("Loading");
				ShortestPath SP = new ShortestPath("src/stops.txt","src/transfers.txt", "src/stop_times.txt");

				boolean isInShortestPath = true;
				while(isInShortestPath) {

					int sourceNode = 0;
					int targetNode = 0;
					boolean inSourceNodeLoop = true;
					boolean inTargetNodeLoop = true;

					while(inSourceNodeLoop) {
						System.out.println("Identify your source node");
						try {
							String input1 = readInput.next();
							sourceNode = Integer.parseInt(input1);
							inSourceNodeLoop = false;
						} catch (NumberFormatException e) {
							System.out.println("Invalid input, please input an Integer");
						}
					}

					while(inTargetNodeLoop) {
						System.out.println("Identify your target node");
						try {
							String input1 = readInput.next();
							targetNode = Integer.parseInt(input1);
							inTargetNodeLoop = false;
						} catch (NumberFormatException e) {
							System.out.println("Invalid input, please input an Integer");
						}
					}
					SP.dijkstra(sourceNode, targetNode);
					System.out.println("\nWould you like to find another path - Yes/No");
					boolean inLoop = true;
					while(inLoop) {
						String answer = readInput.next();
						if(answer.equalsIgnoreCase("Yes")) {
							isInShortestPath = true;
							inLoop = false;
						}
						else if(answer.equalsIgnoreCase("No")) {
							isInShortestPath = false;
							inLoop = false;
						}
						else {
							System.out.println("Invalid input - Try again");
							System.out.println("Would you like to find another path - 'Yes' or 'No' - (Return to main menu)");
							inLoop = true;
						}
					}
				}


			}
			else if(input == 2) {
				TSTSearch st = new TSTSearch("src/stops.txt");
				
				boolean stopSearch = true;
				while(stopSearch) {
					System.out.println("\n\nSearch for a stop - Make sure input is in CAPITALS "
							+ "\nOr write 'Quit'");
					String stopToSearch = readInput.next();
					if (stopToSearch.equalsIgnoreCase("quit")) {
						stopSearch = false;
					}
					else {
						st.printStops(stopToSearch);	
					}	
				}
			}
			else if(input == 3){
				boolean timeSearch = true;
				
				TripSearch TS = new TripSearch("src/stop_times.txt");
				while(timeSearch) {
					System.out.println("Find a bus routes for a given arrival time or write 'quit' ");
					String time = readInput.next();
					String[] splitUpTime = time.split(":");
					Boolean invalidatedInput = false;
					
					try {
						int time1 = Integer.parseInt(splitUpTime[0]);
						int time2 = Integer.parseInt(splitUpTime[1]);
						int time3 = Integer.parseInt(splitUpTime[2]);
					} catch (NumberFormatException e) {
						
						invalidatedInput = true;
					}
					
					
					if(time.equalsIgnoreCase("quit")) {
						timeSearch = false;
					}
					
					else if(invalidatedInput == true) {
						System.out.println("Invalid input, please input an Integer");
					}
						
					else if(Integer.parseInt(splitUpTime[0]) > 23 || Integer.parseInt(splitUpTime[1]) > 59 || Integer.parseInt(splitUpTime[2]) > 59
							|| Integer.parseInt(splitUpTime[0]) < 0 || Integer.parseInt(splitUpTime[1]) < 0 || Integer.parseInt(splitUpTime[2]) < 0) {
						System.out.println("Invalid input make sure the input is between 00:00:00 and 23:59:59");
					}
					else {
						time = " " +time;
						TS.returnStringOutput(time);
					}
				}
				
				
				
				
				
				
			}
			else if(input == 0) { 
				mainLoop = false;
			}
			else {
				System.out.println("Invalid input Enter again");
				
			}

		}





		//	}



		//
		//			ShortestPath SP = new ShortestPath("src/stops.txt","", "src/testing_files_stop_times.txt");		
		//			SP.dijkstra(646, 1856);
		//		System.out.println("");
		//		SP.dijkstra(646, 1856);
		//		System.out.println("");
		//		SP.dijkstra(646, 11283);
		//		System.out.println("");
		//		SP.dijkstra(646, 386);
		//		System.out.println("");
		//		SP.dijkstra(646, 2);
		//		System.out.println("");
		//		SP.dijkstra(646, 381);
		//		System.out.println("");
		//		SP.dijkstra(646, 1856);
		//		System.out.println("");
		//		SP.dijkstra(381, 1856);

	}

}
