import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ScoreBoard extends JFrame{
	private static final long serialVersionUID = 1L;
	private final int DEFAULT_TOP_NUM_PLAYERS = 10;
	private final int MAX_TOP_NUM_PLAYERS = 100;
	private final String _fileName = "SCORE_FILE.txt";
	private int _topNumPlayers;
	private File _scoreFile;
	private JPanel _scorePanel;
	private JButton _closeButton;
	private JTextPane _scoreOutput;
	
	private ArrayList<Player> TopList = new ArrayList<Player>();
	
	/**
	 * Construct a ScoreBoard keeping the default amount of scores (10)
	 */
	public ScoreBoard(){
		super();
		_topNumPlayers = DEFAULT_TOP_NUM_PLAYERS;
		//init the panel
		_scorePanel = new JPanel();
		_scorePanel.setBackground( Color.WHITE);
		_scorePanel.setLayout( new BoxLayout(_scorePanel, 1) );
		//initialize the close button
		_closeButton = new JButton();
		_closeButton.setText("CLOSE");
		_closeButton.setSize(10, 10);
		_closeButton.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			};
		});
		_scorePanel.add( _closeButton);
		//init the text view to display top scores
		_scoreOutput = new JTextPane();
		_scoreOutput.setSize(100,250);
		_scorePanel.add(_scoreOutput);
		//initialize the scoreboard file
		_scoreFile = new File( _fileName);
		if( !_scoreFile.exists() ){
			System.out.println("CREATING A NEW SCOREBOARD FILE");
			try {
				_scoreFile.createNewFile();
			} catch (IOException e) {
				System.out.println("ERROR CREATING FILE: CHECK USER FILE PERMISSIONS");
				e.printStackTrace();
			}
		}
		else{
			System.out.println( _scoreFile.getAbsolutePath());
			System.out.println("LOADING OLD SCOREBOARD FILE");
			this.loadScoreFile();
		}
		this.setSize(100, 200);
		this.add( _scorePanel);
	}
	

	/**
	 * Construct a ScoreBoard keeping the userTopNum amount of scores
	 * @param userTopNum must be >0 and <100
	 */
	public ScoreBoard(int userTopNum){
		this();
		if( userTopNum < 0 || userTopNum > MAX_TOP_NUM_PLAYERS){
			_topNumPlayers = DEFAULT_TOP_NUM_PLAYERS;
		}
		else _topNumPlayers = userTopNum;
	}
	
	/**
	 * add a user to the scoreboard with the given score
	 * @param name name of the player
	 * @param score value of the score
	 * 
	 */
	public void addScorer(String name, int score){
		if( TopList.size() <= _topNumPlayers){
			TopList.add( new Player( name, score));
		}
		else{
			for(int playerIndex = 0; playerIndex < TopList.size(); ++playerIndex){
				if( score > TopList.get(playerIndex).getScore() ){
					TopList.remove(playerIndex);
					TopList.add( new Player( name, score));
					break;
				}
			}
		}
		this.sort();
		this.writeToScoreFile();
	}
	
	/**
	 * show the scoreboard to the user
	 */
	public void showToUser(){
		this._scoreOutput.setText( this.getScoreString() );
		//this.setBounds(x, y, width, height) //TODO this makes it appear in the center of the screen
		this.setVisible(true);
	}
	
	/**
	 * prompt for the input of a name with the score
	 * @param score
	 */
	public void promptScorer(int score) {
		this.showToUser();
		if( TopList.size() <= _topNumPlayers){
			InputBox inbox = new InputBox(score);
			inbox.promptBox();
		}
		else{
			for(int playerIndex = 0; playerIndex < TopList.size(); ++playerIndex){
				if( score > TopList.get(playerIndex).getScore() ){
					InputBox inbox = new InputBox(score);
					inbox.promptBox();
					break;
				}
			}
		}
		this.sort();
		this.writeToScoreFile();
	}

	/*
	 * @return the concatenation of the high scorers on the list;
	 */
	private String getScoreString(){
		String total = "USERNAME: SCORE\n";
		for( Player p : TopList){
			total += p.getName() + ": " +  p.getScore() + "\n";
		}
		return total;
	}
	
	/*
	 * write the list of score to the file
	 * @throws FileNotFoundException e
	 */
	private void writeToScoreFile(){
		PrintWriter overWriter = null;
		try {
			overWriter = new PrintWriter(_scoreFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		overWriter.print( this.getScoreString()	);
		overWriter.close();
	}
	
	/*
	 * load a previously existing score file
	 */
	private void loadScoreFile() {
		//TODO
		// use a .split at " "
		// strings[0] = name
		// strings[1].toInt = score
		// call an addScorer for each name read in
	}
	
	/*
	 * sort the list from hightest to lowest 
	 */
	private void sort() {
		Collections.sort(this.TopList, ScoreBoard.playerComparatorDesc);
	}
	
	/*
	 * a comparator to sort the list in descending order
	 */
	public static final Comparator<Player> playerComparatorDesc = new Comparator<Player>(){
		public int compare(Player A, Player B){
			return A.getScore() - B.getScore(); //TODO
		}	
	};
		
	/*
	 * the unit that makes up the top scorers
	 */
	private class Player{
		String _name;
		int _score;
		
		public Player(){
			_name = "EMPTY";
			_score = 0;
		}
		public Player(String userName, int userTime){
			this();
			_name = userName;
			_score = userTime;
		}
		public int getScore() {
			return _score;
		}
		public String getName(){
			return _name;
		}
	}
	
	/*
	 * the box that the scoreboard uses to add new scores
	 */
	private class InputBox extends JFrame{
		private static final long serialVersionUID = 1L;
		private String name = "EMPTY";
		private int _userScore;
		private JPanel _mainPanel;
		private JLabel _label;
		private JTextField _input;
		private JButton _commit;
		
		/**
		 * contains 3 elements
		 * label to show what user has to do
		 * text field to enter the text
		 * button to commit the text
		 * @param score, the score that is associated with the user name we are prompting for
		 */
		public InputBox(int score){
			super();
			this.setSize(100, 100);
			this.setAlwaysOnTop(true);
			_userScore = score;
			//init the panel
			_mainPanel = new JPanel();
			_mainPanel.setLayout( new BoxLayout(_mainPanel, 1) );
			//_mainPanel.setBackground( Color.White);
			//init the label
			_label = new JLabel();
			_label.setText("Enter your name below:");
			_mainPanel.add( _label);
			//init the text field
			_input = new JTextField();
			_input.setSize(100, 200);
			_mainPanel.add(_input);
			//init the input button
			_commit = new JButton();
			_commit.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if( !_input.getText().isEmpty() ){
						name = _input.getText();
						addScorer( name, _userScore);
						closeBox();
					}
				}
			});
			_commit.setSize(200, 200);
			_commit.setText("SUBMIT");
			_mainPanel.add( _commit);
			this.add(_mainPanel);
		}
		
		/**
		 * open the box and don't close until you get a name
		 */
		public void promptBox(){
			this.setVisible(true);
		}
		
		/*
		 * close the input box
		 */
		private void closeBox(){
			this.setVisible(false);
		}
	}

}
