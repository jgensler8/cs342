import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;


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
		container.add(new GamePanel("board1.txt"));
		window.setContentPane(container);
		window.pack();
		
		//show the window to the user
		window.setBounds(100, 100, 800, 800);
		window.setVisible(true);
	}

	
	/*
	 * classes: board ( piece)
	 * 
	 * board{
	 *   int ySize, xSize
	 * 
	 *  public board( filename ){
	 *  	get size from file
	 *  	while( get pieces from file)
	 *  }
	 *  
	 *  public board( board){
	 *  	use this as a clone
	 *  }
	 *  vs WHAT DO WE DO HERE
	 *  clone(){
	 *  	return copy of board
	 *  }
	 *  im leaning toward the constructor
	 *
	 *	should we do some sort of threading here? problem is finding if we have gotten a certain board before
	 *	also what happens if we have found a solution in each thread?
	 *	we have to stop all other threads and report the solution to main!
	 *	(probs not the easiest to do)
	 *  dfs( board){
	 *  	create a queue
	 *  	find ALL possible moves (should we separate move up by 1 and move up by 2?)
	 *  	^^ that answer is yes because bfs will "expand" naturally
	 *  
	 *  	check that we haven't gotten this board before 
	 *  	remember that this is a graph problem and we dont want
	 *  	to be hopping between two solution moving a peice up and down and up and down
	 * 
	 * 		while( queue not empty)
	 * 			copy current board and make move
	 * 			pass to dfs
	 *  }
	 *  
	 *  
	 * 
	 */
}
