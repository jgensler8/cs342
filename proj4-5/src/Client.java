
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.util.*;

import javax.swing.*;

import Game.GamePanel;

public class Client{
	
	
	/**
	 * 
	 */
	public static void main(String args[]) throws Exception {
		JFrame window = new JFrame("Phase 10");
		buildGui( window);
		
		//show the window to the user
		window.pack();
		window.setBounds( 100,100,1300,400);
		window.setVisible(true);
	}
	
	/*
	 * Add game menus and game to the Container 
	 */
	private static void buildGui(JFrame window){
		JMenuBar menuBar = new JMenuBar();
		
		//// *** Menus
		// *** Main Menu
		JMenu mainMenu = new JMenu("Main");
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem clientItem = new JMenuItem("Run Client");
		clientItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				NetworkInput input = new NetworkInput();
				int result = JOptionPane.showConfirmDialog(null, input, 
			               "Please Enter IP address and Port to connect to", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					startClient( input.getIP(), input.getPort() );
				}
				
			}
		});
		JMenuItem serverItem = new JMenuItem("Run Server Client");
		serverItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				NetworkInput input = new NetworkInput();
				int result = JOptionPane.showConfirmDialog(null, input, 
			               "Please Enter IP address and Port to connect to", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					startServerClient( input.getIP(), input.getPort() );
				}
				
			}
		});
		mainMenu.add( clientItem);
		mainMenu.add( serverItem);
		mainMenu.add( exitItem);
		
		// *** Help Menu
		final JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog( helpMenu, "Created by Azita, Jenny, and JEff");
			}
		});
		JMenuItem helpItem = new JMenuItem("Help");
		helpItem.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog( helpMenu, "See rules online at http://www.natlassoc.org/fun/games/phase10/phase10r.htm");
			}
		});
		helpMenu.add(aboutItem);
		helpMenu.add(helpItem);
		// Add the menus to the menubar
		menuBar.add(mainMenu);
		menuBar.add(helpMenu);
		window.setJMenuBar(menuBar);
		

		window.getContentPane().setLayout( new GridLayout());
		
		//// *** Chat
		
		try {
			//we should add a blank panel here and when the user TODO
			//clicks a menu button, THEN we add the chatPanel to that blank panel
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//// *** Game
		ArrayList<String> addresses = new ArrayList<String>();
		addresses.add("192.168.0.1");
		addresses.add("192.168.0.2");
		addresses.add("192.168.0.3");
		window.getContentPane().add( new GamePanel(addresses) );
	}
	
	/*
	 * 
	 */
	private static void startServerClient(String IP, int port){
		System.out.println("IP addr: " + IP);
		System.out.println("Port: " + port);
		
		InetAddress addr = null;
		try {
			addr = InetAddress.getByName(IP);
			ServerAgent server = new ServerAgent(addr, port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startClient( IP, port);
	}
	
	/*
	 * 
	 */
	private static void startClient(String IP, int port){
		System.out.println("IP addr: " + IP);
		System.out.println("Port: " + port);
		
		/*
		 asdf
		 
		 ClientAgent = new ClientAgent
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				InetAddress addr = null;
				try {
					addr = InetAddress.getByName(IP);
					ChatPanel p = new ChatPanel("$PUBLIC$");
					p.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		*/
	}
	
	public static class NetworkInput extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextField _ipAddress;
		JTextField _port;
		
		public NetworkInput(){
			this._ipAddress = new JTextField(5);
			this._ipAddress.setText("123.123.123.123");
		    this._port = new JTextField(5);
		    this._port.setText("3000");

		    JPanel inputPanel = new JPanel();
		    inputPanel.add(new JLabel("Ip Address (ex:'123.123.123.123'):"));
		    inputPanel.add(this._ipAddress);
		    inputPanel.add(Box.createHorizontalStrut(15)); // a spacer
		    inputPanel.add(new JLabel("Port:"));
		    inputPanel.add(this._port);
		}
		
		/**
		 * get the ipaddress located in the text field
		 */
		public String getIP(){
			return this._ipAddress.getText();
		}
		
		/**
		 * get the port located in the text field
		 */
		public int getPort(){
			return Integer.parseInt( this._port.getText() );
		}
	}
}
