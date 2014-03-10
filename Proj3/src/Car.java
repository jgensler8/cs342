import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;


public class Car extends JButton implements MouseInputListener {
	int width, height;
	int xPos, yPos;
	String name;
	
	Timer timer = new Timer();
	TimerTask task = new MyTimerTask();
	
	private class MyTimerTask extends TimerTask {
		public void run() {	
			setSize(++width, ++height);
		}
	}
	
	public Car(String nm, int wid, int hgt, int x, int y) {
		name = nm;
		width = wid;
		height = hgt;
		xPos = x;
		yPos = y;
		
		setSize(wid, hgt);
		setLocation(x, y);
		
		addMouseListener(this);
	}


	
	@Override public void mouseClicked(MouseEvent e) {

	}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {
		timer.scheduleAtFixedRate(task, 0, 1000);
	}
	@Override public void mouseReleased(MouseEvent e) {
		task.cancel();
	}
	@Override public void mouseDragged(MouseEvent arg0) {
	}
	@Override public void mouseMoved(MouseEvent arg0) {}
}
