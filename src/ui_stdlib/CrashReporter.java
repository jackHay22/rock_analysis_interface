package ui_stdlib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CrashReporter {
	private static String OS = null;
	private static final String SAVE_DIR = "/rock_analysis";
	private static final String APP_SUPPORT = System.getProperty("user.home") + "/Library/Application Support";
	
	private static String get_os_name() {
	   if(OS == null) { OS = System.getProperty("os.name"); }
	      return OS;
	}
	
	private static boolean windows() {
	   return get_os_name().startsWith("Windows");
	
	}
	private static boolean unix() {
		return get_os_name().startsWith("Mac");
	}
	
	private static String get_log_filename() {
		String time_stamp = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(new java.util.Date());
		
		return "crashreport_" + time_stamp + ".txt";
	}
	
	public static <T extends JFrame>  void report_crash(T frame, Exception e) {
		
		Object[] options = {"Save Log", "Ignore"};
		
		int action = JOptionPane.showOptionDialog(frame,
			    e.getMessage(),
			    "Error",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.ERROR_MESSAGE,
			    null,
			    options,
			    options[1]);
		
		if(action == 0){
			try {
				if (unix()) {
					write_trace(get_log_filename() , e);
				} else {
					//TODO windows:
				}
				
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private static File mk_file(String application_support_file) {
		//make application support directory
		new File(APP_SUPPORT + SAVE_DIR).mkdirs();
		
		//create file in AS
		return new File(APP_SUPPORT + SAVE_DIR + "/" + application_support_file);
	}
	
	private static void write_trace(String file_name, Exception ex) throws FileNotFoundException {
		try (PrintWriter out = new PrintWriter(mk_file(file_name))) {
		    ex.printStackTrace(out);
		}
	}

}