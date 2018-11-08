package ui_stdlib.dialogwindows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CSVWriter {
	
	
	static boolean writeToCSV(String save_path, String output) {
		
		try {
			PrintWriter pw = new PrintWriter(new File(save_path + ".csv"));
			pw.write(output);
			pw.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	
}
