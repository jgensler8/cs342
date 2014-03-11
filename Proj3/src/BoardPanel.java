import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;


public class BoardPanel extends JPanel{
	final private int SIZE = 800;
	private int _width, _height;
	private ArrayList<String> _pieceNames;
	
	/*
	 * construct a board panel from a file
	 */
	public BoardPanel( String fileName){
		/*
		String initString = readFile( fileName);
		this.initBoardFromString( initString);
		*/
		_width = 7;
		_height = 7;
		_pieceNames = new ArrayList<String>();
		
		this.setSize(SIZE,SIZE);
		this.setLayout( new GridLayout( _height, _width ));
		for( int numSpaces = 0; numSpaces < _width*_height ; numSpaces++){
			this.add( new Piece( Integer.toString(numSpaces), false, false) );
			_pieceNames.add( Integer.toString( numSpaces) );
		}
	}
	
	/*
	 * construct a board panel from another board panel
	 * (copying the passed panel)
	 */
	public BoardPanel( BoardPanel BP){
		String initString = BP.getBoardString();
		this.initBoardFromString( initString);
	}
	
	/*
	 * convert this board to a string that can uniquely construct this board
	 */
	public String getBoardString(){
		return "TODO"; 
	}
	
	/*
	 * @param file name
	 * read the file and return its contents as a string
	 */
	private String readFile( String s){
		return "TODO";
	}
	
	/*
	 * build the board from the string passed as an argument
	 */
	private void initBoardFromString(String s){
		//TODO
	}
	
	private class Piece extends JPanel{
		private String _name;
		private Boolean _leftNeighbor, _rightNeighbor;
		private Boolean _canMoveVert, _canMoveHoriz;
		
		/*
		 * construct a piece
		 */
		public Piece(String name, Boolean leftNeighbor, Boolean rightNeighbor){
			this.setLayout( new FlowLayout());
			_name = name;
			_leftNeighbor = leftNeighbor; //change to up neighbor as well
			_rightNeighbor = rightNeighbor;
			if(!leftNeighbor) this.add( new ArrowKey( javax.swing.SwingConstants.LEFT) );
			this.add( new ArrowKey( javax.swing.SwingConstants.NORTH) );
			this.add( new ArrowKey( javax.swing.SwingConstants.SOUTH) );
			if(!rightNeighbor) this.add( new ArrowKey( javax.swing.SwingConstants.RIGHT) );
		}
		
		/*
		 * used to move the piece left
		 */
		void moveLeft(Piece p){
			
		}
		
		/*
		 * 
		 */
		void moveRight(Piece p){
			
		}
		
		/*
		 * 
		 */
		void moveUp( Piece p){
			
		}
		
		/*
		 * 
		 */
		void moveDown( Piece p){
			
		}
		
		private class ArrowKey extends JButton{
			int _direction;
			
			/*
			 * construct an arrow key to make the piece move in a certain direction
			 */
			public ArrowKey( int direction){
				_direction = direction;
				this.setActionCommand( _name + " " + _direction);
				this.addActionListener( new ActionListener(){
					public void actionPerformed(ActionEvent e)
		            {
						System.out.println( e.getActionCommand());
		            }
				});
				
				switch(direction){
				case LEFT: this.setText("<");
					break;
				case RIGHT: this.setText(">");
					break;
				case NORTH: this.setText("^");
					break;
				case SOUTH: this.setText("V");
				}
			}
		}
	}
}
