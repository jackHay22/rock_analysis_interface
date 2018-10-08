package system_utils.io_tools;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import system_utils.FileChooser;
import ui_framework.SetupCoordinator;
import ui_stdlib.SystemThemes;

@SuppressWarnings("serial")
public class MultiFileSelector extends JFrame implements ui_framework.ScheduledState {
	private int width = 600;
	private int height = 400;
	private JButton continue_button;
	private FileChooser file_chooser;
	private boolean xrf_chosen = false;
	private boolean means_chosen = false;
	private boolean standards_chosen = false;
	private int path_display_length = 40;
	
	public MultiFileSelector(String title) {
		super(title);	
		
		this.setLayout(new GridLayout(4,0));
		continue_button = new JButton("Continue");
		continue_button.setEnabled(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(SystemThemes.BACKGROUND);
		
		file_chooser = new FileChooser(this);

	}
	
	private void can_continue() {
		continue_button.setEnabled(xrf_chosen && standards_chosen && means_chosen);
	}
	
	private String get_file_display(String label, String path) {
		if (path.length() <= path_display_length) {
			  return label + ": " + path;
			} else {
			  return label + ": ..." + path.substring(path.length() - path_display_length);
			}
	}

	@Override
	public void on_scheduled(SetupCoordinator callback, ui_framework.StateResult previous) {
		JButton xrf_chooser = new JButton("Xrf");
		xrf_chooser.setBackground(SystemThemes.BACKGROUND);
		xrf_chooser.setOpaque(true);
		this.add(xrf_chooser);
		JButton means_chooser = new JButton("Means");
		means_chooser.setBackground(SystemThemes.BACKGROUND);
		means_chooser.setOpaque(true);
		this.add(means_chooser);
		JButton stds_chooser = new JButton("Standards");
		stds_chooser.setBackground(SystemThemes.BACKGROUND);
		stds_chooser.setOpaque(true);
		this.add(stds_chooser);
		
		xrf_chooser.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	xrf_chooser.setText(
		    			get_file_display("Xrf", file_chooser.import_files(file_chooser.xrf, "XRF_DATA_RUN_229")));
		    	xrf_chosen = true;
		    	can_continue();
		    }
		});
		
		means_chooser.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	means_chooser.setText(
		    			get_file_display("Means", file_chooser.import_files(file_chooser.means, "means")));
		    	means_chosen = true;
		    	can_continue();
		    }
		});
		
		stds_chooser.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	stds_chooser.setText(
		    			get_file_display("Standards",file_chooser.import_files(file_chooser.standards, "standards")));
		    	standards_chosen = true;
		    	can_continue();
		    }
		});
		
		continue_button.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				callback.release(file_chooser);
				setVisible(false);
				dispose();
			}
        }); 
		
		this.setMinimumSize(new Dimension(width, height));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		this.setResizable(false);
		this.add(continue_button);
		continue_button.setBackground(SystemThemes.MAIN);
		continue_button.setOpaque(true);
		
		
		
		setVisible(true);
		//continue_button.setVisible(true);
	}
}
