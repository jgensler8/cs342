import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.event.*;

public class GamePanel extends JPanel {
	// TODO squaresize should depend on the dimension of the panel
	//final private int squareSize;	// Square = 50x50 ... rectangle = 50x100 or 100x50
	final private int winWidth, winHeight; //,nButtons;
	private ArrayList<Car> gamePieces;
	
	// TODO add input file as param
	GamePanel(int xDim, int yDim) {
		winWidth  = xDim;
		winHeight = yDim;
		//nButtons  = ; // TODO need to parse in file and determine what's up
		
		setBackground(new Color(255, 255, 240));	// Add background color
		setLayout(null);

		gamePieces = new ArrayList<Car>();	// Initialize ArrayList to hold all buttons

		
	}

	private class Car extends JButton implements MouseInputListener {
		private boolean vertical;	// false for horizontal, true for vertical
		private int width, height, xPos, yPos;
		private String name;

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


		Point clickOrigin, currentPos;	// Used for telling direction of mouse movement

		@Override public void mouseClicked(MouseEvent e) {}
		@Override public void mouseEntered(MouseEvent e) {}
		@Override public void mouseExited(MouseEvent e) {}
		@Override public void mousePressed(MouseEvent e) {
			clickOrigin = e.getPoint();	// Reference point for the mouseDragged event
		}
		@Override public void mouseReleased(MouseEvent e) {
			revalidateGame();
		}
		@Override public void mouseDragged(MouseEvent e) {
			currentPos = e.getPoint();	// Get coordinate of event (relative to panel)

			// x/y +/- 15 sets a threshold. This fixed an error
			// Where the button would keep bouncing back and forth
			//
			// Dragging mouse vertically/horizontally. Shift button by 25
			if(vertical) {
				if(currentPos.y < clickOrigin.y-25 && yPos > 0) {
					if(findComponentAt(xPos, yPos-50) == null)
						setBounds(xPos, yPos-=50, width, height);
				}
				else if(currentPos.y > clickOrigin.y+25 && yPos+height < winHeight) { // change to width of panel - width of button
					if(findComponentAt(xPos, yPos+50) == null)
						setBounds(xPos, yPos+=50, width, height);
				}
			}
			else {
				if(currentPos.x < clickOrigin.x-25 && xPos > 0) {
					//if(findComponentAt(xPos-50, yPos) == null)
						setBounds(xPos-=50, yPos, width, height);
				}
				else if(currentPos.x > clickOrigin.x+25 && xPos+width < winWidth) { // change to width of panel - width of button
					//if(findComponentAt(xPos+50, yPos) == null)
						setBounds(xPos+=50, yPos, width, height);
				}
			}
		}
		@Override public void mouseMoved(MouseEvent arg0) {}

	}
	
	public void revalidateGame() {
		this.revalidate();
	}
	public void repaintGame() {
		this.repaintGame();
	}
}