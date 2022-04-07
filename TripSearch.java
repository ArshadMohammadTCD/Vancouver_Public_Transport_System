import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TripSearch {
	private String stopFilename;
	private int length;
	TripSearch(String stopTimes){
		
		this.stopFilename = stopTimes;
		
		
	}
	private int[] parseStopTimes(String target){
		if (this.stopFilename == null) {
			return null;
		}
		this.length = findLength();
		int[] TripId = new int[length];
		int count =0;
		File a = new File(this.stopFilename);
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
				String nextInput = input.nextLine();
				String[] data = nextInput.split(",");
				if (data[1].equalsIgnoreCase(target)) {
					TripId[count] =Integer.parseInt( data[0]);
				}
				count++;
			}
			
		}
		Arrays.sort(TripId);
		return TripId;
	}
	
	private int findLength() {
		if (this.stopFilename == null) {
			return 0;
		}
		int count =0;
		File a = new File(this.stopFilename);
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
				String nextInput = input.nextLine();
				count++;
			}	
		}
		return count;
	}
	
	public void returnStringOutput(String target) {
		int[] TripId = parseStopTimes(target);
		int count =this.length -1;
		File a = new File(this.stopFilename);
		if (a.length() != 0){
			Scanner input = null;
			
			
			//			System.out.println(firstLine);\
			
			while(TripId[count] != 0) {
				int count2 = 0;
				try {
					input = new Scanner(a);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String firstLine = input.nextLine();
				System.out.println(firstLine);
				while (input.hasNextLine()) {
					String nextInput = input.nextLine();
					String[] data = nextInput.split(",");
					if (Integer.parseInt(data[0]) == TripId[count]) {
						System.out.println(nextInput);
					}
					count2++;
				}
				count--;
			}
			
			
		}
	}	
	
}
