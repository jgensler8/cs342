import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class Main{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//init the window for the user
		JFrame window = new JFrame();
		Container container = window.getContentPane();
		
		//initialize the level storage
		GamePanelLevelStorage storage = new GamePanelLevelStorage();
		
		//add the game board
		final GamePanel game = new GamePanel("board1.txt");
		container.add( game);
		window.pack();
		
		//add the menu bars
		addMenuBars(window, game, storage);
		
		//show the window to the user
		window.setBounds( 100,100,400,400);
		window.setVisible(true);
	}
	
	/**
	 * initialze the menu bars in the main window
	 */
	public static void addMenuBars( JFrame window, final GamePanel game, final GamePanelLevelStorage storage){
		JMenuBar menuBar = new JMenuBar();
		
		// *********************************** Game Menu
		JMenu gameMenu = new JMenu("Game");
		JMenuItem restartItem = new JMenuItem("Restart");
		restartItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				game.resetBoard();
			}
		});
		JMenuItem hintItem = new JMenuItem("Hint");
		hintItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				game.showHint();
			}
		});
		JMenuItem solveItem = new JMenuItem("Solve");
		solveItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				game.showSolution(); 
			}
		});
		JMenuItem exitItem = new JMenuItem("Exit");
		gameMenu.add( restartItem);
		gameMenu.add( hintItem);
		gameMenu.add( solveItem);
		gameMenu.add( exitItem);
		
		// ************************************Help Menu
		final JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog( helpMenu, "Created by Adam Perez and Jeff Gensler");
			}
		});
		JMenuItem helpItem = new JMenuItem("Help");
		helpItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog( helpMenu, "TRY AND GET THE RED BOX TO THE OTHER SIDE THAT IT STARTS OUT IN");
			}
		});
		helpMenu.add(aboutItem);
		helpMenu.add(helpItem);
		
		// **************************************** Level Select Menu
		final JMenu levelSelectMenu = new JMenu("Level Select");
		JMenuItem addLevelItem = new JMenuItem("Add Level");
		addLevelItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				storage.addFile();
			}
		});
		JMenuItem loadLevelItem = new JMenuItem("Load Level");
		loadLevelItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> levelList = storage.getLevel();
				if( levelList != null) game.resetBoard( levelList);
			}
		});
		levelSelectMenu.add( addLevelItem);
		levelSelectMenu.add( loadLevelItem);
		
		
		//add the menus to the menu bar
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		menuBar.add(levelSelectMenu);
		//add the menu bar to the window
		window.setJMenuBar(menuBar);
	}
}
