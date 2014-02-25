import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ScoreBoard extends JFrame{
	private static final long serialVersionUID = 1L;
	private final int DEFAULT_TOP_NUM_PLAYERS = 10;
	private final int MAX_TOP_NUM_PLAYERS = 100;
	private int _topNumPlayers;
	private final String _fileName = "SCORE_FILE.txt";
	private File _scoreFile;
	private JButton _closeButton;
	
	private ArrayList<Player> TopList = new ArrayList<Player>();
	
	/**
	 * Construct a ScoreBoard keeping the default amount of scores (10)
	 */
	public ScoreBoard(){
		super();
		_topNumPlayers = DEFAULT_TOP_NUM_PLAYERS;
		_closeButton = new JButton();
		_closeButton.setText("CLOSE");
		_closeButton.setSize(10, 10); //TODO
		_closeButton.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			};
		});
		this.add( _closeButton);
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
		this.writeToFile();
	}
	
	/**
	 * show the scoreboard to the user
	 */
	public void showToUser(){
		this.setVisible(true);
		this.setAlwaysOnTop(true);
	}
	
	/**
	 * prompt for the input of a name with the score
	 * @param score
	 */
	public void promptScorer(int score) {
		this.showToUser();
		if( TopList.size() <= _topNumPlayers){
			InputBox inbox = new InputBox();
			inbox.promptName();
			this.addScorer( inbox.getName() , score);
		}
		else{
			for(int playerIndex = 0; playerIndex < TopList.size(); ++playerIndex){
				if( score > TopList.get(playerIndex).getScore() ){
					InputBox inbox = new InputBox();
					inbox.promptName();
					this.addScorer( inbox.getName() , score);
					break;
				}
			}
		}
		this.sort();
		this.writeToFile();
	}

	/*
	 * write the list of score to the file
	 * @throws FileNotFoundException e
	 */
	private void writeToFile(){
		String total = "";
		for( Player p : TopList){
			total += p.getName() + " " +  p.getScore() + "\n";
		}
		PrintWriter overWriter = null;
		try {
			overWriter = new PrintWriter(_scoreFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		overWriter.print(total);
		overWriter.close();
	}
	
	/*
	 * load a previously existing score file
	 */
	private void loadScoreFile() {
		// use a .split at " "
		// strings[0] = name
		// strings[1].toInt = score
		// call sort
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
			return A.getScore() - B.getScore();
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
		private String name;
		private Boolean _validName;
		private JLabel _label;
		private JTextField _input;
		private JButton _commit;
		
		/*
		 * contains 3 elements
		 * label to show what user has to do
		 * text field to enter the text
		 * button to commit the text
		 */
		public InputBox(){
			super();
			this.setLayout( new FlowLayout() );
			this.setSize(100, 200);
			this.setAlwaysOnTop(true);
			_validName = false;
			_label = new JLabel();
			_label.setText("Enter your name below:");
			this.add( _label);
			_input = new JTextField();
			_input.setSize(100, 200);
			this.add(_input);
			_commit = new JButton();
			_commit.addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if( _input.getText() != ""){
						name = _input.getText();
						_validName = true;
					}
				}
			});
			_commit.setSize(200, 200);
			_commit.setText("SUBMIT");
			this.add( _commit);
		}
		
		/*
		 * open the box and don't close until you get a name
		 */
		public void promptName(){
			this.setVisible(true);
			while( !_validName);
			this.setVisible(false);
		}
		
		/*
		 * return the name that has been read
		 */
		public String getName(){
			return name;
		}
	}

}
