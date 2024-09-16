package galaxyEd2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class GalaxyLog {
	
	private static File logFile;
	private static String basepath = "C:\\eclipse-workspace\\GalaxyEd\\src\\galaxyEd2\\";
	
	static {
		GalaxyLog.logFile = new File(basepath+ "galaxy.log");
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	String error1 = "";
	
//	public GalaxyLog (String fallacy) {
//		try {
//			textFileWriter(fallacy);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
	public static void exceptionFileWriter(Exception error) throws IOException {
		
		String line = "";
		
		FileWriter writer = new FileWriter(logFile, true);
		BufferedWriter realWriter = new BufferedWriter(writer);
//		FileReader reader = new FileReader(logFile);
		PrintWriter pw  = new PrintWriter( realWriter );
		
		Date date = new Date();
		
//		while (line != null) {
//			line = realReader.readLine();
//		}
		
		pw.append("Date: " + date.getDate());
		error.printStackTrace( pw );
		pw.flush();
        pw.close();
		realWriter.close();
		writer.close();
	}	
}
