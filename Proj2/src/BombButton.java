import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class BombButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	/*
	 *
	 */
	public BombButton(){
		super();
		this.setSize(5, 5);
		addActionListener( this);
	}
	public BombButton(int width, int height){
		this();
		this.setSize( width, height);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * 
	 */
	public void actionPerformed( ActionEvent event) {
		
	}
	
}
