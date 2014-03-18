import java.awt.Point;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;


public class Car extends JButton implements MouseInputListener {
	boolean vertical;	// 0 for horizontal, 1 for vertical
	int width, height;
	int xPos, yPos;
	String name;

	public Car(String nm, int x, int y, int wid, int hgt, boolean vert) {
		name = nm;
		xPos = x;
		yPos = y;
		width = wid;
		height = hgt;
		vertical = vert;

		setText(name);
		setBounds(xPos, yPos, width, height);	// Location and size of button in panel	
		addMouseListener(this);	// Add mouse listeners specified below
		addMouseMotionListener(this);	// Allow for dragging/move event
	}


	Point clickOrigin, currentPos;

	@Override public void mouseClicked(MouseEvent e) {
		/*clickOrigin = getMousePosition();
		
		if(clickOrigin.x < width/2)
			setBounds(xPos-=50, yPos, width, height);
		else if(clickOrigin.x > width/2)
			setBounds(xPos+=50, yPos, width, height);
		*/
	}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {
		clickOrigin = e.getPoint();	// Reference point for the mouseDragged event
	}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseDragged(MouseEvent e) {/*
		currentPos = e.getPoint();	// Get coordinate of event (relative to panel)

		// x/y +/- 15 sets a threshold. This fixed an error
		// Where the button would keep bouncing back and forth
		//
		// Dragging mouse vertically/horizontally. Shift button by 25
		if(vertical) {
			if(currentPos.y < clickOrigin.y-15 && yPos > 0)
				setBounds(xPos, yPos-=25, width, height);
			else if(currentPos.y > clickOrigin.y+15 && yPos < 500) // change to width of panel - width of button
				setBounds(xPos, yPos+=25, width, height);
		}
		else {
			if(currentPos.x < clickOrigin.x-15 && xPos > 0)
				setBounds(xPos-=25, yPos, width, height);
			else if(currentPos.x > clickOrigin.x+15 && xPos < 500) // change to width of panel - width of button
				setBounds(xPos+=25, yPos, width, height);	
		}*/
	}
	@Override public void mouseMoved(MouseEvent arg0) {}

}
