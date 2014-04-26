package Agents;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import Chat.ChatPanel;
import Game.GamePanel;

public class ClientAgent {

	//LOTS TO BE DONE
	private String _userName;
	private String _buddy;
	private Socket _soc;
	private DataInputStream _inStream;
	private DataOutputStream _outStream;
	private Thread _daemon;
	private int _serverPort;
	private String _serverHostname;
	private ChatPanel _chatPanel;
	private GamePanel _gamePanel;

	public ClientAgent(InetAddress addr, int port, ChatPanel chatPanel, GamePanel gamePanel) throws IOException{
		//init the connection to the panels
		this._chatPanel = chatPanel;
		this._gamePanel = gamePanel;
		
		//TODO
		_userName = "SET ME UP WHEN I CONENCT TO SERVER";
		
		//init the connection
		_soc = new Socket(addr, port);
		_inStream = new DataInputStream(_soc.getInputStream());
		_outStream = new DataOutputStream(_soc.getOutputStream());
		_outStream.writeUTF(_userName);
		//_daemon = new AcceptClient();
		//_daemon.start();
	}

	private class AcceptClient extends Thread{
		public void run() {
			while (true) {
				try {
					String[] msgArray = _inStream.readUTF().split("\\|\\|");
					if (msgArray.length > 1) {
						if (msgArray[0].equals("$YOUR_NAME$")) {
							//_chatPanel.setStuff();
							_userName = msgArray[1];
						} else if (msgArray[0].equals("$DATA$")) {
							_chatPanel.appendToDisplay("\n" + msgArray[1]);
						} else if (msgArray[0].equals("$ERROR$")) {
							_chatPanel.appendToDisplay("\n" + msgArray[1]);
						} else if (msgArray[0].equals("$USERS$")) {
							// Relay update list of online users to everyone
							_outStream.writeUTF(formatMsgToSrv("$UPDATE$",
									msgArray[1].trim()));
						} else if (msgArray[0].equals("$UPDATE$")) {
							String[] userArray = msgArray[1].trim().split("\\~");
							ArrayList<String> users = new ArrayList<String>();
							for( String user : userArray)
								users.add(user);
							_chatPanel.updateConnectedList(users);
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
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

	/*
	 * TODO 
	 */
	private String formatMsgToSrv(String action) {
		return formatMsgToSrv(action, "");
	}
	
	/**
	 * send a message to the server
	 * @param message, the message to be send
	 */
	public void sendMessage(String message) {
		//XXX
		System.out.println(message);
		try {
			_outStream.writeUTF(formatMsgToSrv("$DATA$", message));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * TODO
	 */
	public boolean isBuddy(String user) {
		return _buddy.equals("$PUBLIC$") || _buddy.indexOf(user) != -1;
	}
}
