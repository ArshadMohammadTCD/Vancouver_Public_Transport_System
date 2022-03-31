import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ShortestPath {


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
			while (input.hasNextLine()) {
				String times = input.nextLine();
				String[] data = times.split(",");
				System.out.println(data[0]);
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
