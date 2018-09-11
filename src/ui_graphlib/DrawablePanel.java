package ui_graphlib;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import system_utils.DataStore;
import ui_framework.Refreshable;
import ui_framework.SystemPanel;

@SuppressWarnings("serial")
public class DrawablePanel extends SystemPanel implements MouseListener {
	private Graphics2D g;
	private BufferedImage image;
	private int height;
	private int width;
	private DrawableManager manager;
	
	public DrawablePanel(DrawableManager manager) {
		super();
		init();
		this.manager = manager;
	}
	private void init() {
		this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.g = (Graphics2D) image.getGraphics();
	}
	@Override
	public void refresh() {
		manager.draw_components(g);
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, width, height, null);
		g2.dispose();
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
}
