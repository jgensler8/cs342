import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GamePanelLevelStorage {
	
	private Hashtable<String,ArrayList<String>> _levels; //key is filename, value is the arrayList representation of the board
	
	/**
	 * constructor
	 */
	public GamePanelLevelStorage(){
		this._levels = new Hashtable<String,ArrayList<String>>();
		this.addFile("board1.txt");
	}
	
	/**
	 * add an unknown file to list of playable boards
	 * useful for prompting input
	 */
	public void addFile(){
		String fileName = JOptionPane.showInputDialog("Enter the file name: ");
		//open the file
		if(fileName != null){
			try {
				new FileReader( "boards/" + fileName );
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}
			this.addFile(fileName);
		}
	}
	

	/**
	 * add a known file to the list of playable boards
	 */
	public void addFile(String fileName){
		ArrayList<String> toAdd = new ArrayList<String>();
		BufferedReader reader = null;
		//open the file
		try {
			reader = new BufferedReader( new FileReader( "boards/" + fileName ));
		} catch (FileNotFoundException e2) {
			JOptionPane.showMessageDialog( new JLabel(), "That file doesn't exist!");
			//e2.printStackTrace();
		}
		//read the file
		try {
			while( reader.ready()){
				toAdd.add(reader.readLine());
			}
		} catch (IOException e1) {
			System.out.println("ERROR READING FILE LINE");
			e1.printStackTrace();
		}
		//close the file
        try {
			reader.close();
		} catch (IOException e) {
			System.out.println("ERROR CLOSING FILE");
			e.printStackTrace();
		}
        //test the file and see if any piece intersect any of the other peices
        GamePanel g = new GamePanel(toAdd);
        if( g.validateBoard() )
        	this._levels.put(fileName, toAdd);
        else
    		JOptionPane.showMessageDialog( new JLabel(), "You've entered a board that contains errors!");
	}
	
	/**
	 * get a board saved in storage
	 */
	public ArrayList<String> getLevel(){
		String levelName = JOptionPane.showInputDialog("Enter a Level to load:");
		if( levelName != null && this._levels.get(levelName) != null){
			return this._levels.get(levelName);
		}
		else{
			JOptionPane.showMessageDialog( new JLabel(), "Sorry, you haven't loaded this level. Use the 'Load Level' option.");
			return null;
		}
	}
	
}
