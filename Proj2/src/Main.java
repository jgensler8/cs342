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

	/*
	 * initializes the frame with the game contents
	 */
	private static void initFrame( JFrame frame){
		Container container = frame.getContentPane();
		
		//configure the menu bar
		JMenuBar menuBar = new JMenuBar();
		initMenuBar(menuBar);
		frame.setJMenuBar( menuBar);
		
		ScoreBoard scoreboard = new ScoreBoard();
		scoreboard.addScorer("Jeff", 20);
		scoreboard.addScorer("Jeff", 30);
		scoreboard.addScorer("Jeff", 40);
		
		//configure the bomb panel
		BombPanel bombpanel = new BombPanel();
		bombpanel.setScoreBoard(scoreboard);
		container.add( bombpanel);
		
		//configure the frame
		frame.setSize(400, 400);
	}
	
	/*
	 * initialize components in the passed menuBar
	 */
	private static void initMenuBar( JMenuBar menuBar){
		JMenu gameMenu = new JMenu("GAME");
		JMenuItem startGameMenuItem = new JMenuItem("Reset");
		//TODO maybe make a "resetEvent"
		gameMenu.add(startGameMenuItem);
		
		//see write up for what these are supposed to do/accomplish
		//TODO jmenuitem topten not sure how this will be done... don't quite have a timer working yet either
		//TODO jmenuitem exit
		
		//TODO jmenu help
		//TODO jmenuitem help
		//TODO jmenuitem about
		
		menuBar.add(gameMenu);
	}
	
	/*
	 * set the frame as visible, starting the game
	 */
	private static void playGame( JFrame frame){
		frame.setVisible( true);
	}
	
}
