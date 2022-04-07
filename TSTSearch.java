import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TSTSearch {
	
	TSTSearch(String stopsFilename){		
		parseStops(stopsFilename);
		
		
		
		
	}

	private void parseStops(String stopsFilename) {
		if (stopsFilename == null) {
			return;
		}
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
				String times = input.nextLine();
				String[] data = times.split(",");
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
				
				
				
				System.out.println(data[2]);
				
			}	


		}	
		
	}
	
	
}
//git add -A; git commit -m "made changes to parseStops in the TST Class p1" ; git push origin master