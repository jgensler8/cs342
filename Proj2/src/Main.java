import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//initialize components
		JFrame frame = new JFrame(); //functions as a frame
		initFrame( frame);
		//game loop
		playGame( frame);
	}

	/**
	 * @param frame
	 * initializes the frame with the game contents
	 */
	private static void initFrame( JFrame frame){
		Container container = frame.getContentPane();
		
		//configure the menu bar
		JMenuBar menuBar = new JMenuBar();
		initMenuBar(menuBar);
		frame.setJMenuBar( menuBar);
		
		//configure the bomb panel
		BombPanel bombPanel = new BombPanel();
		container.add( bombPanel);
		
		//configure the frame
		frame.setSize(400, 400);
	}
	
	/*
	 * 
	 */
	private static void initMenuBar( JMenuBar menuBar){
		JMenu gameMenu = new JMenu("GAME");
		JMenuItem startGameMenuItem = new JMenuItem("Reset");
		//startGameMenuItem.addActionListener( actionListener e){
			
		//}
		gameMenu.add(startGameMenuItem);
		
		//see write up for what these are supposed to do/accomplish
		//TODO jmenuitem topten
		//TODO jmenuitem exit
		
		//TODO jmenu help
		//TODO jmenuitem help
		//TODO jmenuitem about
		
		menuBar.add(gameMenu);
	}
	
	/*
	 * start the game loop, handle replaying of the game
	 */
	private static void playGame( JFrame frame){
		frame.setVisible( true);
	}
	
}
