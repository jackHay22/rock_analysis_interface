package ui_graphlib;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import system_utils.DataStore;
import ui_framework.Refreshable;

@SuppressWarnings("serial")
public class DrawablePanel extends JPanel implements MouseListener, Refreshable{
	private DrawableManager manager;
	
	public DrawablePanel(DrawableManager manager, int width, int height) {
		super();
//		this.width = width;
//		this.height = height;
		this.manager = manager;
		setPreferredSize(new Dimension(width, height));
	}
	
	@Override
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);

	    //g.setColor(Color.blue);
	    g.drawRect(0, 0, 200, 200);
		Graphics2D g2 =(Graphics2D)g;
		manager.draw_components(g2);
        
    }

	@Override
	public void set_datastore(DataStore datastore) {
	}

	@Override
	public void add_refreshable(Refreshable refreshable_component) { }
	
	@Override
	public void mouseClicked(MouseEvent e) {
		manager.handle_mouse_event(e);	
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		manager.handle_mouse_event(e);	
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void refresh() {
		this.repaint();
	}

	@Override
	public void on_start() {
		
	}
}
