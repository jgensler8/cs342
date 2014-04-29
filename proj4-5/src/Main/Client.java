package Main;

/*
@(#)File:           Client.java
@(#)Purpose:        CS342 Project 4
@(#)Author:         Jeff Gensler, Azita Moghadaszadeh and Jenny Sum
@(#)Copyright:      (C) Spring 2014
*/

import Agents.ClientAgent;
import Agents.ServerAgent;
import Game.Card;
import Game.Hand;
import Game.PhasePanel;
import Game.Pile;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.Scrollable;

public class Client implements Runnable, ActionListener, WindowListener,
		KeyListener {
	// This hostname should match the IP address of the Chat Server (use
	// "localhost" if Chat Server is running from the same machine as client)
	public static final String SERVER_HOSTNAME = "192.168.1.74";
	// This port number should match that of the Chat Server
	public static final int SERVER_PORT = 9001;
	//
	static ClientAgent _agent;
	int phaseNumber = 1;
	
	/**
	 * GUI Components
	 */
	private JFrame _gameFrame; // overall frame
	private JTextArea _chatDisplay; // displays chat messages
	private JTextField _chatBox; // user text box
	private JPanel _userPanel;
	private ArrayList<JCheckBox> _userCheckboxes;
	private JButton _sendButton; // submit user chat message

	/**
	 * User + Game Settings
	 */
	private String _buddy;
	private String _userName;
	private Hand _usersHand;
	private Card _topDiscard;
	private Card _topDraw;
	private Pile _pile;
	private Boolean _isDrawPhase;
	private Boolean _isReturnPhase;
	private String phaseButtonOne;
	private String phaseButtonTwo;

	/**
	 * Socket Settings
	 */
	private DataOutputStream _outStream;
	private DataInputStream _inStream;
	private final JPanel tablePanel = new JPanel();
	private JPanel phasePanel;
	private JPanel handPanel;
	private PhasePanel p;
	
	private JPanel discardPart;
	private JPanel drawPart;
	private JPanel tablePart;
	private JPanel handPart;
	private JPanel phasePart;
	private JButton button;
	private JButton button_1;
	private JButton btnSubmitPhase;
	private JButton btnReturnToHand;
	
	
	/**
	 * Main entry point of the program
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// instantiate new client with everyone as its recipients
					Client client = new Client("$PUBLIC$");
					client.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create instance of client
	 * 
	 * @param buddy
	 * @throws Exception
	 */
	public Client(String buddy) throws Exception {
		// initialize GUI Components
		initializeGUI();
		// initialize User and Socket Settings
		//initializeSettings("$REPLACE_TOKEN$", buddy);
		
		_isDrawPhase = false;
		_isReturnPhase = true;
		
		_usersHand = new Hand();
		_usersHand.addCard( new Card(1,1));
		_usersHand.addCard( new Card(3,2));
		_usersHand.addCard( new Card(7,3));
		_usersHand.addCard( new Card(2,2));
		_usersHand.addCard( new Card(5,1));
		_usersHand.addCard( new Card(5,2));
		_usersHand.addCard( new Card(5,3));
		_usersHand.addCard( new Card(5,4));
		this.renderHand();
		
		_pile = new Pile();
		_pile.returnCard( new Card(1,2) );
		this.renderDiscard();
		this.renderDraw();
		
		//System.out.println( temp.cardSelected() ); //used to get the users card selected
		
		p = new PhasePanel(phaseNumber, _usersHand);
		
		//show what table will look like

		Hand temp2 = new Hand();
		temp2.addCard( new Card(6,1));
		temp2.addCard( new Card(6,2));
		temp2.addCard( new Card(6,3));
		temp2.addCard( new Card(6,4));
		Hand temp3 = new Hand();
		temp3.addCard( new Card(1,1));
		temp3.addCard( new Card(2,2));
		temp3.addCard( new Card(3,3));
		temp3.addCard( new Card(4,2));
		ArrayList<Hand> playerOnes = new ArrayList<Hand>();
		playerOnes.add(temp2);
		playerOnes.add(temp3);
		
		Hand temp4 = new Hand();
		temp4.addCard( new Card(8,1));
		temp4.addCard( new Card(8,2));
		temp4.addCard( new Card(8,3));
		temp4.addCard( new Card(8,2));
		ArrayList<Hand> playerTwos = new ArrayList<Hand>();
		playerTwos.add(temp4);
		
		ArrayList<ArrayList<Hand>> table = new ArrayList<ArrayList<Hand>>();
		table.add( playerOnes);
		table.add( playerTwos);
	
		this.renderTable(table);
	}

	/**
	 * Create instance of client
	 * 
	 * @param userName
	 * @param buddy
	 * @throws Exception
	 */
	public Client(String userName, String buddy) throws Exception {
		// initialize GUI Components
		initializeGUI();
		// initialize User and Socket Settings
		//initializeSettings(userName, buddy);
	}

	public JFrame getFrame() {
		return _gameFrame;
	}

	/**
	 * Initializes contents of frame
	 */
	private void initializeGUI() {
		_gameFrame = new JFrame();
		_gameFrame.setTitle("PHASE 10");
		_gameFrame.getContentPane().setBackground(Color.BLACK);
		_gameFrame.setBounds(1000, 1000, 1002, 708);
		_gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		_gameFrame.addWindowListener(this);
		_gameFrame.getContentPane().setLayout(null);

		_userCheckboxes = new ArrayList<JCheckBox>();
		
		JPanel chatPanel = new JPanel();
		chatPanel.setBackground(Color.BLUE);
		chatPanel.setBounds(6, 6, 280, 396);
		_gameFrame.getContentPane().add(chatPanel);
		chatPanel.setLayout(null);
		
				_chatBox = new JTextField();
				_chatBox.setBounds(16, 18, 157, 40);
				chatPanel.add(_chatBox);
				
						_sendButton = new JButton("SUBMIT");
						_sendButton.setBounds(185, 19, 58, 40);
						chatPanel.add(_sendButton);
						//_sendButton.setIcon(new ImageIcon("monkey.png"));
						_sendButton.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 13));
						
								_chatDisplay = new JTextArea();
								_chatDisplay.setBounds(16, 70, 154, 301);
								chatPanel.add(_chatDisplay);
								_chatDisplay.setEditable(false);
								
										_userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
										_userPanel.setBackground(Color.WHITE);
										_userPanel.setBounds(182, 97, 84, 274);
										chatPanel.add(_userPanel);
										
												JLabel lblOnlineUsers = new JLabel("ONLINE USERS");
												lblOnlineUsers.setBounds(182, 81, 80, 16);
												chatPanel.add(lblOnlineUsers);
												lblOnlineUsers.setForeground(Color.WHITE);
												lblOnlineUsers.setFont(new Font("Franklin Gothic Medium", Font.PLAIN,
														13));
								tablePanel.setBackground(Color.RED);
								tablePanel.setBounds(294, 6, 700, 396);
								_gameFrame.getContentPane().add(tablePanel);
								tablePanel.setLayout(null);
								
								JLabel lblTable = new JLabel("Table");
								lblTable.setForeground(Color.WHITE);
								lblTable.setBounds(660, 6, 34, 16);
								tablePanel.add(lblTable);
								
								tablePart = new JPanel();
								tablePart.setBackground(new Color(255, 0, 0));
								tablePart.setBounds(6, 23, 688, 294);
								tablePanel.add(tablePart);
								
								drawPart = new JPanel();
								drawPart.setBackground(new Color(255, 0, 0));
								drawPart.setBounds(654, 320, 40, 59);
								tablePanel.add(drawPart);
								
								discardPart = new JPanel();
								discardPart.setBackground(new Color(255, 0, 0));
								discardPart.setBounds(602, 320, 40, 59);
								tablePanel.add(discardPart);
								
								JLabel lblDiscard = new JLabel("Discard");
								lblDiscard.setForeground(new Color(255, 255, 255));
								lblDiscard.setBounds(594, 380, 48, 16);
								tablePanel.add(lblDiscard);
								
								JLabel lblDraw = new JLabel("Draw");
								lblDraw.setForeground(new Color(255, 255, 255));
								lblDraw.setBackground(new Color(255, 0, 0));
								lblDraw.setBounds(660, 380, 34, 16);
								tablePanel.add(lblDraw);
								
								handPanel = new JPanel();
								handPanel.setBackground(new Color(0, 128, 0));
								handPanel.setBounds(474, 409, 520, 269);
								_gameFrame.getContentPane().add(handPanel);
								handPanel.setLayout(null);
								
								JLabel lblHand = new JLabel("Hand");
								lblHand.setForeground(new Color(255, 255, 255));
								lblHand.setBounds(481, 6, 33, 16);
								handPanel.add(lblHand);
								
								handPart = new JPanel();
								handPart.setBackground(new Color(0, 128, 0));
								handPart.setBounds(6, 24, 508, 205);
								handPanel.add(handPart);
								handPart.setLayout(null);
								
								switch(phaseNumber){
								case 1: phaseButtonOne = "1st Set of 3"; phaseButtonTwo = "2nd Set of 3"; break;
								case 2: phaseButtonOne = "Set of 3"; phaseButtonTwo = "Run of 4"; break;
								case 3: phaseButtonOne = "Set of 4"; phaseButtonTwo = "Run of 4"; break;
								case 4: phaseButtonOne = "Run of 7"; break;
								case 5: phaseButtonOne = "Run of 8"; break;
								case 6: phaseButtonOne = "Run of 9"; break;
								case 7: phaseButtonOne = "1st Set of 4"; phaseButtonTwo = "2nd Set of 4"; break;
								case 8: phaseButtonOne = "7 of 1 Color"; break;
								case 9: phaseButtonOne = "Set of 5"; phaseButtonTwo = "Run of 2"; break;
								case 10: phaseButtonOne = "Set of 5"; phaseButtonTwo = "Run of 3"; break;
								}
								
								if (phaseNumber == 1 || phaseNumber == 2 || phaseNumber == 3 || 
										phaseNumber == 7 || phaseNumber == 9 || phaseNumber == 10) {
									button_1 = new JButton(phaseButtonOne);
									button_1.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											phasePart.removeAll();
											phasePart.add( p.addTo(1) );
										}
									});
									button_1.setBounds(6, 234, 117, 29);
									handPanel.add(button_1);
									
									button = new JButton(phaseButtonTwo);
									button.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											phasePart.removeAll();							//make sure we "refresh" the view
											phasePart.add( p.addTo(2) );
										}
										
									});
									button.setBounds(397, 234, 117, 29);
									handPanel.add(button);
								}
								else {
									button_1 = new JButton(phaseButtonOne);
									button_1.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											phasePart.removeAll();
											phasePart.add( p.addTo(1) );
										}
									});
									button_1.setBounds(6, 234, 117, 29);
									handPanel.add(button_1);
								}
								

								
								phasePanel = new JPanel();
								phasePanel.setBackground(Color.YELLOW);
								phasePanel.setBounds(6, 409, 460, 269);
								_gameFrame.getContentPane().add(phasePanel);
								phasePanel.setLayout(null);
								
								phasePart = new JPanel();
								phasePart.setBackground(new Color(255, 255, 0));
								phasePart.setBounds(6, 21, 448, 211);
								phasePanel.add(phasePart);
								phasePart.setLayout(null);
								
								JLabel lblNewLabel_1 = new JLabel("Phase Submission");
								lblNewLabel_1.setForeground(Color.RED);
								lblNewLabel_1.setBounds(341, 6, 113, 16);
								phasePanel.add(lblNewLabel_1);
								
								btnSubmitPhase = new JButton("Submit Phase");
								btnSubmitPhase.setBounds(341, 234, 117, 29);
								phasePanel.add(btnSubmitPhase);
								
								btnReturnToHand = new JButton("Return to Hand");
								btnReturnToHand.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										p.returnAllCards(_usersHand);
										renderHand();
									}
								});
								btnReturnToHand.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
								btnReturnToHand.setBounds(6, 234, 117, 29);
								phasePanel.add(btnReturnToHand);
						_sendButton.addActionListener(this);
				_chatBox.addKeyListener(this);
	}

	/**
	 * Background thread communicating with chat server socket
	 */
	public void run() {
		while (true) {
			try {
				String[] msgArray = _inStream.readUTF().split("\\|\\|");
				if (msgArray.length > 1) {
					if (msgArray[0].equals("$YOUR_NAME$")) {
						// client assigned a new username from chat server
						// append username to title of window
						_userName = msgArray[1];
						_gameFrame.setTitle("CHATROOM ZOO - " + _userName);

						//_gameFrame.revalidate();
						_gameFrame.repaint();

					} else if (msgArray[0].equals("$DATA$")) {
						// another user sent a message broadcasted, display here
						_chatDisplay.insert("\n" + msgArray[1], 0);
					} else if (msgArray[0].equals("$ERROR$")) {
						// duplicate username supplied when creating this client
						_chatDisplay.insert("\n" + msgArray[1], 0);
					} else if (msgArray[0].equals("$USERS$")) {
						// Relay update list of online users to everyone
						_outStream.writeUTF(formatMsgToSrv("$UPDATE$",
								msgArray[1].trim()));
					} else if (msgArray[0].equals("$UPDATE$")) {
						// update list of online users received from server
						// remove what we have displayed as online users and
						// refresh our list with the update list from the server
						_userPanel.removeAll();
						_userCheckboxes.clear();

						// adds new check box for every user
						String[] userArray = msgArray[1].trim().split("\\~");
						for (String user : userArray) {

							JCheckBox newCheckBox = new JCheckBox(user);
							newCheckBox.setSelected(isBuddy(user));
							if (user.equals(_userName))
								newCheckBox.setEnabled(false);
							newCheckBox.addActionListener(this);

							_userPanel.add(newCheckBox);
							_userCheckboxes.add(newCheckBox);
						}

						_userPanel.revalidate();
						_userPanel.repaint();
						//_gameFrame.revalidate();
						_gameFrame.repaint();

					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Build a tokenize string that the server knows how to parse
	 * 
	 * @param msg
	 *            Text from input box
	 * @return Tokenize message to server
	 */
	private String formatMsgToSrv(String action, String msg) {
		if (action.equals("$UPDATE$"))
			return String.format("%s %s %s %s", action, _buddy, action, msg);
		else
			return String.format("%s %s %s %s", _userName, _buddy, action, msg);
	}

	/**
	 * Reroute to above method but with no message
	 * 
	 * @param action
	 * @return Tokenize message to server
	 */
	private String formatMsgToSrv(String action) {
		return formatMsgToSrv(action, "");
	}

	/**
	 * Action triggered to broadcast a message from this client
	 */
	private void sendMessage() {
		try {
			String msg = _chatBox.getText().toString();
			_outStream.writeUTF(formatMsgToSrv("$DATA$", msg));
			_chatBox.setText("");
		} catch (Exception ex) {
		}
	}

	/**
	 * Determines if given username is a recipient of this client
	 * 
	 * @param user
	 * @return true if user or everyone is a recipient, otherwise false
	 */
	private boolean isBuddy(String user) {
		return _buddy.equals("$PUBLIC$") || _buddy.indexOf(user) != -1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(_sendButton)) {
			// trigger to send message
			sendMessage();
		} else if (_userCheckboxes.contains(e.getSource())) {
			// select a limited audience (semicolon-delimited) to send message
			// to. If all users were selected, then the client's recipients is
			// set to everyone
			StringBuilder buddies = new StringBuilder();
			String delim = "";
			int iCount = 0;
			for (JCheckBox user : _userCheckboxes) {
				if (user.equals(e.getSource())) {
					user.setSelected(((JCheckBox) e.getSource()).isSelected());
				}
				if (user.isSelected()) {
					buddies.append(delim + user.getText());
					delim = ";";
					iCount++;
				}
			}
			if (iCount == _userCheckboxes.size()) {
				_buddy = "$PUBLIC$";
			} else {
				_buddy = buddies.toString();
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// send message to server that client disconnected, so server can
		// notify other clients with the updated list of online users
		if( this._agent != null) this._agent.quit();
		_gameFrame.setVisible(false);
		_gameFrame.dispose();
		System.exit(1);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource().equals(_chatBox)) {
			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
				// trigger to send message
				//sendMessage();
			}
		}
		
		//System.out.println( this._hand.getSelection() );
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	/*
	 * XXX older code, may be of use...
	 */
	private static void startServerClient(String IP, int port){
		System.out.println("CLIENT: STARTING SERVER AT " + IP + " on port " + port);
		
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
	 * XXX older code, may be of use...
	 */
	private static void startClient(String IP, int port){
		System.out.println("CLIENT: STARTING CLIENT AT " + IP + " on port " + port);
		InetAddress addr = null;
		try {
			addr = InetAddress.getByName(IP);
			_agent = new ClientAgent(addr, port);
			int options[] = {100,101,1,2,3};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private void renderHand(){
		this.handPart.removeAll();
		this.handPart.add( _usersHand.render( this.handPanel.getBackground() ));
		this.handPart.repaint();
		this.handPart.revalidate();
	}
	
	/**
	 * 
	 */
	private void renderDiscard(){
		this.discardPart.removeAll();
		this._topDiscard = _pile.renderDiscard();
		MouseListener m = new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if( _isDrawPhase){
					if( _topDiscard.getRank() != Card.BLANK){
						_isDrawPhase = false;
						_topDiscard.removeMouseListener(this);
						_usersHand.addCard( _pile.popDiscard());
						renderHand();
						renderDiscard();
					}
				}
				else if( _isReturnPhase){
					Card c =  _usersHand.cardSelected();
					_usersHand.removeCard(c);
					_pile.returnCard( c );

					renderHand();
					renderDiscard();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		};
		this._topDiscard.addMouseListener(m);
		this.discardPart.add( this._topDiscard );
		this.discardPart.repaint();
		this.discardPart.revalidate();
	}
	
	/**
	 * 
	 */
	private void renderDraw(){
		this.drawPart.removeAll();
		this._topDraw  = _pile.renderDraw();
		MouseListener m = new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if( _isDrawPhase){
					_isDrawPhase = false;
					_topDraw.removeMouseListener(this);
					_usersHand.addCard( _pile.popDraw());
					renderHand();
					renderDraw();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		};
		this._topDraw.addMouseListener(m);
		this.drawPart.add( _topDraw);
		this.drawPart.repaint();
		this.drawPart.revalidate();
	}
	
	/**
	 * render an arraylist of arraylist of cards
	 * Everyplayer has multiple plays which are contined in the hands
	 */
	private void renderTable(ArrayList<ArrayList<Hand>> allPlayersHands){
		tablePart.removeAll();
		Color background = Color.RED;
		JPanel outerWrapperPane = new JPanel();
		outerWrapperPane.setLayout( new GridLayout(10,1) );
		outerWrapperPane.setBackground(background);
		for(ArrayList<Hand> playerPlays : allPlayersHands){
			
			JTabbedPane playerWrapper = new JTabbedPane();
			for( Hand h : playerPlays){
				playerWrapper.add(h.render(background) );
			}
			playerWrapper.setBackground(background);
			playerWrapper.setSize( playerWrapper.getPreferredSize());
			outerWrapperPane.add(playerWrapper);
		}
		
		outerWrapperPane.setSize( outerWrapperPane.getPreferredSize() );
		tablePart.add(outerWrapperPane);
		tablePart.setSize(tablePart.getPreferredSize());
		tablePart.repaint();
		tablePart.revalidate();
	}
	
	
	public static class NetworkInput extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JTextField _ipAddress;
		JTextField _port;
		
		public NetworkInput(){
			this._ipAddress = new JTextField(20);
			this._ipAddress.setText("127.0.0.1");
		    this._port = new JTextField(10);
		    this._port.setText("15000");

		    JPanel inputPanel = new JPanel();
		    inputPanel.add(new JLabel("Ip Address (ex:'123.123.123.123'):"));
		    inputPanel.add(this._ipAddress);
		    inputPanel.add(Box.createHorizontalStrut(15)); // a spacer
		    inputPanel.add(new JLabel("Port:"));
		    inputPanel.add(this._port);
		    this.add( inputPanel);
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
