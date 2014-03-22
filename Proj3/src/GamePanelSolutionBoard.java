import java.awt.Container;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class GamePanelSolutionBoard extends JFrame{
private static final long serialVersionUID = 1L;
	
	/*
	 * private constructor to set up defaults
	 */
	private GamePanelSolutionBoard(){
		super();
		this.setBounds(50, 50, 400, 400);
		this.setVisible(true);
	}
	
	/*
	 * constructor for a hint
	 */
	public GamePanelSolutionBoard(ArrayList<String> hintList){
		this();
		Container contentPane = this.getContentPane();
		
		GamePanel hintBoard = new GamePanel( hintList );	//instantiate a board panel
		hintBoard.disableButtonMovement();				//disable its item's movement
		
		contentPane.add( hintBoard );
		this.setBounds( hintBoard.getBounds() );
	}
	
	/*
	 * constructor for a full solution
	 */
	public GamePanelSolutionBoard(Stack<ArrayList<String>> solutionStack){
		this();
		Container contentPane = this.getContentPane();
		
		JTabbedPane tabView = new JTabbedPane();
		tabView.addTab("Current", null, new GamePanel( solutionStack.pop()));
		for( int boardIndex = 1; !solutionStack.isEmpty(); boardIndex++){
			tabView.addTab("Step " + boardIndex, null, new GamePanel( solutionStack.pop()));
		}
		
		contentPane.add( tabView);
	}
}
