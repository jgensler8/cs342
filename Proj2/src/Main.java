import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//initialize components
		JWindow window = new JWindow();
		JPanel panel = new JPanel(new BorderLayout());
		JFrame frame = new JFrame();
		Container contentPane = frame.getContentPane();
		contentPane.add( new JButton() );
		window.add( panel);
		window.add(contentPane);
		
		//open game window
		window.setVisible( true);
		//game loop
	}

}
