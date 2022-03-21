package customevents.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LogCustomEventsUtil {
	
private static String path = null;
	
	static {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		URL resource = contextClassLoader.getResource("logs/");
		path = resource.getPath();
	}
	
	public static List<String> getEventsListFromLogs() {
		List<String> eventsList = new LinkedList<>();
		
		try {
			File file = new File(path + "logFile.txt"); // creates a new file instance
			FileReader fr = new FileReader(file); // reads the file
			BufferedReader br = new BufferedReader(fr); // creates a buffering character input stream
			String line;
			
			while ((line = br.readLine()) != null) {
				eventsList.add(line);
			}
			fr.close(); // closes the stream and release the resources
			
			return eventsList;
		} catch (IOException e) {
			return new ArrayList<>();
		}
	}

}
