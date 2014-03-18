import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;



public class Window extends JFrame {
	private final int width = 700, height = 700, offset = 100;	// Offset used for sizing components
	private int time, moveCount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Window() {

		time = 0;
		moveCount = 0;
		
		  //////////////////////////////
		 //      WINDOW SETTINGS     //
		//////////////////////////////
		
		this.setTitle("Rush Hour");
		this.setBounds(0, 0, width, height);		// Set size of window (3rd and 4th args)
		this.setLocationRelativeTo(null);	// Center the window on the screen
		this.setResizable(false);					// Keep size of the window fixed
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		  ////////////////////////
		 //      MENU BAR      //
		////////////////////////
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		 // Game Menu
		/////////////
		
		JMenu gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		
		JMenuItem restart = new JMenuItem("Restart");
		JMenuItem hint = new JMenuItem("Hint");
		JMenuItem exit = new JMenuItem("Exit");
		gameMenu.add(restart);
		gameMenu.add(hint);
		gameMenu.add(exit);

		 // Help Menu
		/////////////
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);

		JMenuItem about = new JMenuItem("About");
		JMenuItem mntmHelp = new JMenuItem("Help");
		helpMenu.add(about);
		helpMenu.add(mntmHelp);

		  ////////////////////////////
		 //      CONTENT PANE      //
		////////////////////////////
		
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(143, 188, 143));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		this.setContentPane(contentPane);

		 // Game Controls (Restart, Hint, Level Select, time, #moves
		////////////////////////////////////////////////////////////
		
		JPanel btnPanel = new JPanel();
		btnPanel.setBounds(width-offset, 0, offset, height-offset);	// dock 100 pixels left of the right border
		btnPanel.setLayout(new GridLayout(9, 1, 0, 0));
		btnPanel.setBackground(new Color(240, 255, 240));
		contentPane.add(btnPanel, BorderLayout.EAST);

		JButton restartBtn = new JButton("Restart");
		btnPanel.add(restartBtn);

		JButton hintBtn = new JButton("Hint");
		btnPanel.add(hintBtn);

		 // Levels Menu
		///////////////

		LevelButton levelBtn = new LevelButton(10);	// Will bring up a menu with 0-9
		btnPanel.add(levelBtn);
		
		 // Game Information Display
		////////////////////////////
		
		// Add empty space between levels button and time label
		Component separator1 = Box.createRigidArea(new Dimension(20, 20));
		btnPanel.add(separator1);
		
		// Time information
		JLabel timeLbl = new JLabel("Time");
		timeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		btnPanel.add(timeLbl);
		
		JLabel currTimeLbl = new JLabel("START TIME");
		currTimeLbl.setForeground(SystemColor.controlHighlight);
		currTimeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		btnPanel.add(currTimeLbl);
		
		// Add empty space between time display and moves label
		Component separator2 = Box.createRigidArea(new Dimension(20, 20));
		btnPanel.add(separator2);
		
		// Information about number of moves
		JLabel movesLbl = new JLabel("Moves");
		movesLbl.setHorizontalAlignment(SwingConstants.CENTER);
		btnPanel.add(movesLbl);
		
		JLabel nMovesLbl = new JLabel("NMOVES");
		nMovesLbl.setForeground(SystemColor.controlHighlight);
		nMovesLbl.setHorizontalAlignment(SwingConstants.CENTER);
		btnPanel.add(nMovesLbl);

		 // Gameplay Window
		///////////////////
		
		// TODO Add input file as parameter
		GamePanel gamePanel = new GamePanel(width-offset, width-offset);	// Change this to have the dimensions as specified in input file
		
		// TODO rectangular grid would require some multiple of offset (width-offset, width-2*offset) for 4x3
		gamePanel.setBounds(0, 0, width-offset, width-offset);	// Create square grid
		contentPane.add(gamePanel, BorderLayout.CENTER);
	}
}