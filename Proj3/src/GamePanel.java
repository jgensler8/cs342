import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.event.MouseInputListener;

/*
 * (0,0) (1,0)
 * (1,0)
 */

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	public static final int BUTTON_SIZE = 80;
	private int _winWidth, _winHeight;
	private JViewport _field;
	private ArrayList<Piece> _pieces;
	/*
	 * the board that will get reset when the a reset button is clicked
	 */
	private ArrayList<String> _resetBoard;
	/*
	 * a stack that will contain the boards that will lead to the solution
	 */
	private Stack<ArrayList<String>> _solutionBoards;
	
	/*
	 * initialize the attributes of this GamePanel
	 */
	private GamePanel(){
		super();
		this._field = new JViewport();
		this.setBackground( new Color(255, 255, 240));
		this._pieces = new ArrayList<Piece>();
		this.setLayout(null);
	}
	
	/*
	 * construct a board panel from a file
	 */
	public GamePanel( String fileName){
		this();
		ArrayList<String> initList = readFileList( fileName);
		this.initBoardFromList( initList);
		this._resetBoard = new ArrayList<String>(this.getBoardList());
		this._solutionBoards = new Stack<ArrayList<String>>();
	}
	
	/*
	 * construct a board panel from another board panel
	 * (copying the passed panel)
	 */
	public GamePanel( ArrayList<String> initList){
		this();
		this.initBoardFromList( initList);
	}
	
	/*
	 * convert this board to a string that can uniquely construct this board
	 */
	public ArrayList<String> getBoardList(){
		ArrayList<String> toReturn = new ArrayList<String>();
		toReturn.add( this._winHeight + "  " + this._winWidth);
		for( Piece p : _pieces){
			toReturn.add(p.toString());
		}
		return toReturn;
	}
	
	/*
	 * reset the board to the initial state
	 */
	public void resetBoard(){
		//XXX
		this.removeAll();
		this._pieces.clear();
		this.initBoardFromList(_resetBoard);
	}
	
	/*
	 * called to disable movement of the pieces in the panel
	 */
	public void disableButtonMovement() {
		for(Piece p : this._pieces){
			p.setImmovable();
		}
	}
	
	/*
	 * show the solution path and to the final state
	 */
	public void showSolution(){
		this.solvePuzzle();
		
		//because we need to show where the player started from
		this._solutionBoards.add( this.getBoardList());
		
		GamePanelSolutionBoard fullSolutionPopUp = new GamePanelSolutionBoard( this._solutionBoards );
	}

	/*
	 * show the solution path and to the final state
	 */
	public void showHint(){
		//creates a hint solution board using a specific constructor
		GamePanelSolutionBoard hintPopUp = new GamePanelSolutionBoard( this.solvePuzzle() );
	}
	
	/*
	 * @param file name
	 * read the file and return its contents as a string
	 */
	private ArrayList<String> readFileList( String fileName){
		ArrayList<String> toReturn = new ArrayList<String>();
		BufferedReader reader = null;
		//open the file
		try {
			reader = new BufferedReader( new FileReader( "boards/" + fileName ));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		//read the file
		try {
			while( reader.ready()){
				toReturn.add(reader.readLine());
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
        return toReturn;
	}
	
	/*
	 * build the board from the string passed as an argument
	 */
	private void initBoardFromList( ArrayList<String> lines){
		String line = lines.get(0);
		String lineSplit[] = line.split("  "); //TWO SPACES
		_winHeight = Integer.parseInt( lineSplit[0]);
		_winWidth = Integer.parseInt( lineSplit[1] );
		_field.setBounds(0,0, (this._winHeight+1)*GamePanel.BUTTON_SIZE, (this._winWidth+1)*GamePanel.BUTTON_SIZE);
		
		//read in the cars
		int posX, posY;
		int height, width;
		char direction;
		String name;
		Boolean isWinningPiece = false;
		for(int lineIndex = 1; lineIndex < lines.size(); lineIndex++){
			name = "P" + lineIndex;
			if( lineIndex == 1) isWinningPiece = true;
			else isWinningPiece = false;
		    line = lines.get(lineIndex);
			lineSplit = line.split("  "); //TWO SPACES
			posX = Integer.parseInt( lineSplit[0]);
			posY = Integer.parseInt( lineSplit[1]);
			height = Integer.parseInt( lineSplit[2]);
			width = Integer.parseInt( lineSplit[3]);
			direction = lineSplit[4].charAt(0);
			//System.out.println( name +" "+ posX +" "+ posY +" "+ height +" "+ width +" "+ direction);
			switch(direction){
			case 'v':
			case 'V':
				Piece toAddV = new Piece( name, posX, posY, height, width, true, isWinningPiece);
				this.add( toAddV);
				this._pieces.add( toAddV);
				break;
			case 'h': 
			case 'H':
				Piece toAddH = new Piece( name, posX, posY, height, width, false, isWinningPiece);
				this.add( toAddH);
				this._pieces.add( toAddH);
				break;
			default:
				//this.add( new Piece( name, posX, posY, height, width, false)); 
			}
		}
		this.add(_field); //adding the field last moves it to the back
		this.validate();
	}

	/*
	 * BFS of the possible boards from the current state of the user's game panel
	 * *** returns the next move to make get the nearest solution *** (used by hint button)
	 */
	private ArrayList<String> solvePuzzle(){
		this._solutionBoards.clear();	//make sure there aren't any boards left over from last time 
		Queue<ArrayList<String>> 				queue 		= new LinkedList<ArrayList<String>>();
		Hashtable<String, ArrayList<String>> 	visited		= new Hashtable<String, ArrayList<String>>();
		queue.add( this.getBoardList() );
		visited.put( this.getBoardList().toString(), this.getBoardList() );
		int counter = 0;
		
		while( !queue.isEmpty() ){
			System.out.println(counter + " " + queue.size() + " " + visited.size());
			ArrayList<String> queueList = queue.poll();			//grab the first board list out of the queue
			GamePanel queueBoard = new GamePanel( queueList);	//instantiate a board the board list
			if( queueBoard.isSolved() ){
				ArrayList<String> backtrackList = queueList;
				ArrayList<String> currentList = queueList; 
				System.out.println("FOUND THE SOLUTION!");
				do{
					this._solutionBoards.push(currentList);					//record the board we have as a solution
					currentList = backtrackList; 							//move back through the solutions
					backtrackList = visited.get(currentList.toString()); 	//get the board that got me here
				}while( backtrackList != currentList); 						//because the start of the graph is delimited by the start hashed to itself
				this._solutionBoards.pop();									//the top board on our list is the current board, but we don't want this
				return this._solutionBoards.peek();
			}
			ArrayList<ArrayList<String>> possibleBoards = queueBoard.getPossibleMoveLists();	//possible moves ("adjacent edges" of the graph)
			for( ArrayList<String> possibleBoardList : possibleBoards){
				if( possibleBoardList != null && !visited.contains( possibleBoardList) ){
					visited.put( possibleBoardList.toString(), queueList);	//key is where a possible move path, value is the board that got me here
					queue.add( possibleBoardList);							//make sure we hit this board when its turn is up in the queue
				}
			}
			counter++;
		}
		return null;
	}
		
	/*
	 * determines if the board is solved
	 * the 'z' piece is in the final position
	 */
	private boolean isSolved() {
		for( Piece p : this._pieces)
		if( p.isInWinningPosition() )
			return true;
		return false;
	}
	
	/*
	 * get a list of 
	 */
	private ArrayList<ArrayList<String>> getPossibleMoveLists() {
		ArrayList<ArrayList<String>> toReturn = new ArrayList<ArrayList<String>>();
		for( int pieceIndex = 0; pieceIndex < this._pieces.size() ; pieceIndex++){
			toReturn.add( this.simulateMove( pieceIndex, LEFT ));
			toReturn.add( this.simulateMove( pieceIndex, RIGHT ));
			toReturn.add( this.simulateMove( pieceIndex, UP ));
			toReturn.add( this.simulateMove( pieceIndex, DOWN ));
		}
		return toReturn;
	}
	
	/*
	 * simulate the move of a specific piece in a specific direction.
	 * the directions are determined by the ones IN THIS CLASS.
	 */
	private ArrayList<String> simulateMove(int pieceIndex, int direction){
		GamePanel simulated = new GamePanel( this.getBoardList() );
		if(simulated._pieces.get(pieceIndex).canFit(direction)){
			simulated._pieces.get(pieceIndex).doMove(direction);
			return simulated.getBoardList();
		}
		else return null;
	}
	
	
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	private class Piece extends JViewport implements MouseInputListener{
		private static final long serialVersionUID = 1L;
		private String _name;
		private Boolean _vertical;
		private Boolean _horizontal;
		private Boolean _isWinningPiece;
		private Point _currentDragPos;
		
		/*
		 * constructor for the winning piece
		 */
		public Piece( String name, int x, int y, int height, int width, Boolean vertical, Boolean isFinalPiece){
			super();
			this._name = name;
			this._vertical = vertical;
			this._horizontal = !vertical;
			this._isWinningPiece = isFinalPiece;
			this._currentDragPos = new Point();
			//this.setText( this._name);
			this.setBackground( new Color(
					(int)((Math.random()*1000) % 255),
					(int)((Math.random()*1000) % 255),
					(int)((Math.random()*1000) % 255)) );
			this.setBounds( x*BUTTON_SIZE, y*BUTTON_SIZE, width*BUTTON_SIZE, height*BUTTON_SIZE);
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		
		/*
		 * get the name of this piece
		 */
		public String getName(){
			return this._name;
		}
		
		/*
		 * convert the button into a string
		 * this can be used to instantiate a board
		 */
		public String toString(){
			return (this.getX() / BUTTON_SIZE) + "  " + (this.getY() / BUTTON_SIZE) + "  "
				+ (this.getHeight() / BUTTON_SIZE) + "  " + (this.getWidth() / BUTTON_SIZE)
				+ "  " + (this._vertical ? "v" : "h");
		}
		
		/*
		 * check to see if this piece is in the winning position
		 */
		public Boolean isInWinningPosition(){
			return ( this._isWinningPiece && (this.getBounds().getMaxX() == _field.getBounds().getMaxX()));
		}

		/*
		 * disable any movement that can happen with the buttons
		 */
		public void setImmovable(){
			//not working for some reason? >.< TODO
			this._vertical = false;
			this._horizontal = false;
		}
		
		/*
		 * check to see if this piece can move in a particular direction
		 */
		public boolean canFit(int direction){
			Rectangle newPosition = new Rectangle( this.getBounds());
			switch(direction){
			case UP:
				if( this._horizontal || this.getY() - BUTTON_SIZE < 0 ) return false;
				if( this._vertical) newPosition.setLocation( this.getX(), this.getY() - BUTTON_SIZE);
				break;
			case DOWN: 
				if( this._horizontal || this.getY() + BUTTON_SIZE >= _field.getBounds().getMaxY() ) return false;
				if( this._vertical) newPosition.setLocation( this.getX(), this.getY() + BUTTON_SIZE);
				break;
			case LEFT:
				if( this._vertical || this.getX() - BUTTON_SIZE < 0 ) return false;
				if( this._horizontal) newPosition.setLocation( this.getX() - BUTTON_SIZE, this.getY() );
				break;
			case RIGHT:
				if( this._vertical || this.getX() + BUTTON_SIZE >= _field.getBounds().getMaxX() ) return false;
				if( this._horizontal) newPosition.setLocation( this.getX() + BUTTON_SIZE, this.getY() );
				break;
			}
			//verify that the space we are moving to reflects our start state
			//System.out.println( this.getBounds() + " MOVING TO " + newPosition.getBounds());
			for( Piece p : _pieces){
				if( p._name != this._name){
					if( p.getBounds().contains( newPosition.getLocation())){
						//TODO this doesn't work when bottom corners match
						//System.out.println( p._name + " contains where this item will move" + p.getLocation());
						return false;
					}
					if(newPosition.getBounds().contains( p.getLocation() )){
						//System.out.println("I will contain another item");
						return false;
					}
				}
			}
			return true;
		}
		
		/*
		 * move the piece in a given direction
		 */
		protected void doMove(int direction){
			switch(direction){
			case UP:
				this.setLocation( this.getX(), this.getY()-BUTTON_SIZE);
				break;
			case DOWN:
				this.setLocation(this.getX(), this.getY()+BUTTON_SIZE);
				break;
			case LEFT:
				this.setLocation( this.getX()-BUTTON_SIZE, this.getY());
				break;
			case RIGHT:
				this.setLocation(this.getX()+BUTTON_SIZE, this.getY());
				break;
			}
		}

		@Override public void mouseClicked(MouseEvent e) {}
		@Override public void mouseEntered(MouseEvent e) {}
		@Override public void mouseExited(MouseEvent e) {}
		@Override public void mousePressed(MouseEvent e) {}
		@Override public void mouseReleased(MouseEvent e) {}
		@Override public void mouseDragged(MouseEvent e) {
			this._currentDragPos.setLocation( this.getX()+e.getX(), this.getY()+e.getY());
			//System.out.println( this._currentDragPos);
			if( _field.contains( this._currentDragPos)){
				if(this._currentDragPos.y > this.getBounds().getMaxY() && canFit(DOWN))
					this.doMove(DOWN);
				else if(this._currentDragPos.y < this.getBounds().getMinY() && canFit(UP))
					this.doMove(UP);
				else if(this._currentDragPos.x > this.getBounds().getMaxX() && canFit(RIGHT))
					this.doMove(RIGHT);
				else if(this._currentDragPos.x < this.getBounds().getMinX() && canFit(LEFT))
					this.doMove(LEFT);
			}
		}
		@Override public void mouseMoved(MouseEvent e) {}
	}
}
