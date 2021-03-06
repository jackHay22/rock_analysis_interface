package ui_stdlib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CrashReporter {
	private static String OS = null;
	
	// These are the final directories that the crash info will be saved inside
	private static final String SAVE_DIR = "/HALite";
	private static final String WINDOWS_SAVE_DIR = "\\HALite";
	
	// OSX app support
	private static final String APP_SUPPORT = System.getProperty("user.home") + "/Library/Application Support";
	
	// This is the file path for windows crash reporting
	private static final String WINDOWS_APP_DATA = System.getProperty("user.home") + "\\AppData\\Local\\";
	
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
		
		//joption pane prompts user to ignore or save log to native "app support" dir
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
				//attempt to write trace as stream
				write_trace(get_log_filename() , e);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private static String get_os_path() {
		if (unix()) {
			return APP_SUPPORT + SAVE_DIR;
		} else if (windows()) {
			return WINDOWS_APP_DATA + WINDOWS_SAVE_DIR;
		} else {
			//by default
			return System.getProperty("user.home");
		}
	}
	
	private static File mk_file(String os_path, String application_support_file) {
		//make application support directory
		new File(os_path).mkdirs();
		
		//create file in AS
		return new File(os_path + "/" + application_support_file);
	}
	
	private static void write_trace(String file_name, Exception ex) throws FileNotFoundException {
		try (PrintWriter out = new PrintWriter(mk_file(get_os_path(), file_name))) {
		    ex.printStackTrace(out);
		}
	}

}
