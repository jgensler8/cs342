import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
		
		ScoreBoard scoreboard = new ScoreBoard();
		scoreboard.addScorer("BEAT ME", 20);
		scoreboard.addScorer("BEAT ME", 30);
		scoreboard.addScorer("BEAT ME", 40);
		scoreboard.addScorer("BEAT ME", 40);
		scoreboard.addScorer("BEAT ME", 40);
		scoreboard.addScorer("BEAT ME", 40);
		scoreboard.addScorer("BEAT ME", 1);
		scoreboard.addScorer("BEAT ME", 1);
		scoreboard.addScorer("BEAT ME", 1);
		scoreboard.addScorer("BEAT ME", 1);
		scoreboard.addScorer("BEAT ME", 1);
		scoreboard.addScorer("BEAT ME", 1);
		
		//configure the menu bar
		JMenuBar menuBar = new JMenuBar();
		initMenuBar(menuBar, frame, scoreboard);
		frame.setJMenuBar( menuBar);
		
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
	private static void initMenuBar( JMenuBar menuBar, final JFrame frame, final ScoreBoard scoreboard){
        JMenu game = new JMenu("Game");
        JMenu help = new JMenu("Help");

        JMenuItem resetMenuItem = new JMenuItem("Reset");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        JMenuItem helpMenuItem = new JMenuItem("Help");
        JMenuItem toptenMenuItem = new JMenuItem("Top Ten");
        JMenuItem aboutMenuItem = new JMenuItem("About");

        //TODO maybe make a "resetEvent"
        //add drop down menu items
        game.add(resetMenuItem);
        game.add(toptenMenuItem);
        game.add(exitMenuItem);

        help.add(helpMenuItem);
        help.add(aboutMenuItem);

        //action/event listeners for drop down menu item standard format.
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Authors: Jeff Gensler and Karl Consing", "About", JOptionPane.PLAIN_MESSAGE);
            }
        });

        helpMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Guess the squares where the mines are! Use left click to guess\n" +
                		"and right click to mark the spaces", "Help", JOptionPane.PLAIN_MESSAGE);
            }
        });

        resetMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				BombPanel bombpanel = new BombPanel();
				bombpanel.setScoreBoard(scoreboard);
				frame.getContentPane().add( bombpanel);
				frame.getContentPane().validate();
				//frame.getContentPane().revalidate();
				frame.getContentPane().repaint();
			}
        });

        toptenMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				scoreboard.showToUser();
			}
        });

		menuBar.add(game);
		menuBar.add(help);
	}
	
	/*
	 * set the frame as visible, starting the game
	 */
	private static void playGame( JFrame frame){
		frame.setVisible( true);
	}
	
}
