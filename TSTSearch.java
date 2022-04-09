import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TSTSearch {
	TST<Integer> st = new TST<Integer>();
	HashMap<String, String> stringIndex = new HashMap<String, String>(); 
	TSTSearch(String stopsFilename){		
		parseStops(stopsFilename);
		
		
		
		
	}
	
	
	public void printStops(String target) {
		
		for (String s : st.keysWithPrefix(target)) {
          System.out.println(s);
		  String toPrint = stringIndex.get(s);
		  System.out.println(toPrint);
		}
	}
	
	
	private void parseStops(String stopsFilename) {
		if (stopsFilename == null) {
			return;
		}
		int count =0;
		File a = new File(stopsFilename);
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
				String[] location = data[2].split(" ");
				
				if(location[0].equals("WB")) {
					data[2] = data[2].replace("WB ", "");
					data[2] = data[2] + " WB";
				}	
				if(location[0].equals("SB")) {
					data[2] = data[2].replace("SB ", "");
					data[2] = data[2] + " SB";
				}
				if(location[0].equals("NB")) {
					data[2] = data[2].replace("NB ", "");
					data[2] = data[2] + " NB";
				}
				if(location[0].equals("EB")) {
					data[2] = data[2].replace("EB ", "");
					data[2] = data[2] + " EB";
				}
				if(location[0].equals("FLAGSTOP")) {
					data[2] = data[2].replace("FLAGSTOP ", "");
					data[2] = data[2] + " FLAGSTOP";
					if(location[1].equals("WB")) {
						data[2] = data[2].replace("WB ", "");
						data[2] = data[2] + " WB";
					}	
					if(location[1].equals("SB")) {
						data[2] = data[2].replace("SB ", "");
						data[2] = data[2] + " SB";
					}
					if(location[1].equals("NB")) {
						data[2] = data[2].replace("NB ", "");
						data[2] = data[2] + " NB";
					}
					if(location[1].equals("EB")) {
						data[2] = data[2].replace("EB ", "");
						data[2] = data[2] + " EB";
					}
				}
				
				st.put(data[2], count);
				stringIndex.put(data[2], nextInput);
				count++;
			}	


		}	
		
	}
	
	
}
//git add -A; git commit -m "Added dijkstra functions" ; git push origin master