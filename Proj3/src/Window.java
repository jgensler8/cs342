import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;




public class Window extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// ADD RESET BUTTON
		//##################

		JPanel btnPanel = new JPanel();
		contentPane.add(btnPanel, BorderLayout.NORTH);
		btnPanel.setBackground(new Color(128, 207, 255));

		JButton resetButton = new JButton("Reset");
		btnPanel.add(resetButton);

		// ADD PANEL TO HOLD MAZE
		//########################
		
		// Create the panel where maze will be displayed
		JPanel maze = new JPanel();
		maze.setLayout(null);
		contentPane.add(maze);
		
		Border mazeBorder = BorderFactory.createLineBorder(new Color(255, 176, 128), 3);
		maze.setBackground(new Color(255, 200, 170));
		maze.setBorder(mazeBorder);

		Car c = new Car("Z", 100, 10, 100, 100);
		maze.add(c);
	}
}
