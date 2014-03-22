import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class Main{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//init the window for the user
		JFrame window = new JFrame();
		window.setResizable(false);
		Container container = window.getContentPane();
		
		//add the game board
		final GamePanel game = new GamePanel("board1.txt");
		container.add( game);
		window.pack();
		
		//add the menu bars
		addMenuBars(window, game);
		
		//show the window to the user
		window.setBounds( 100,100,400,400);
		window.setVisible(true);
	}
	
	/*
	 * 
	 */
	public static void addMenuBars( JFrame window, final GamePanel game){
		JMenuBar menuBar = new JMenuBar();
		
		// Game Menu
		JMenu gameMenu = new JMenu("Game");
		JMenuItem restartItem = new JMenuItem("Restart");
		restartItem.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.resetBoard();
			}
		});
		JMenuItem hintItem = new JMenuItem("Hint");
		hintItem.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.showHint();
			}
		});
		JMenuItem solveItem = new JMenuItem("Solve");
		hintItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				game.showSolution();
			}
		});
		JMenuItem exitItem = new JMenuItem("Exit");
		gameMenu.add( restartItem);
		gameMenu.add( hintItem);
		gameMenu.add( solveItem);
		gameMenu.add( exitItem);
		
		//add the menus to the menu bar
		menuBar.add(gameMenu);
		//add the menu bar to the window
		window.setJMenuBar(menuBar);
	}
}
