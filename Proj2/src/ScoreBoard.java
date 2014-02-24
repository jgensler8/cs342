import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;

public class ScoreBoard extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private final int DEFAULT_TOP_NUM_PLAYERS = 10;
	private final int MAX_TOP_NUM_PLAYERS = 100;
	private int topNumPlayers;
	
	private ArrayList<Player> TopList = new ArrayList<Player>();
	
	/**
	 * Construct a ScoreBoard keeping the default amount of scores (10)
	 */
	public ScoreBoard(){
		topNumPlayers = DEFAULT_TOP_NUM_PLAYERS;
	}
	/**
	 * Construct a ScoreBoard keeping the userTopNum amount of scores
	 * @param userTopNum must be >0 and <100
	 */
	public ScoreBoard(int userTopNum){
		super();
		if( userTopNum < 0 || userTopNum > MAX_TOP_NUM_PLAYERS){
			topNumPlayers = DEFAULT_TOP_NUM_PLAYERS;
		}
		else topNumPlayers = userTopNum;
	}
	
	/**
	 * add a user to the scoreboard with the given score
	 * @param name name of the player
	 * @param score value of the score
	 * 
	 */
	public void addScorer(String name, int score){
		if( TopList.size() <= topNumPlayers){
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
	}
	
	/**
	 * show the scoreboard to the user
	 */
	public void show(){
		this.setVisible(true);
		this.setAlwaysOnTop(true);
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
			return A.score - B.score;
		}	
	};
		
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		//TODO
	}
	
	private class Player{
		String name;
		int score;
		
		public Player(){
			name = "EMPTY";
			score = 0;
		}
		
		public int getScore() {
			return score;
		}
		public Player(String userName, int userTime){
			this();
			name = userName;
			score = userTime;
		}
		
	}

}
